package service;

import DAO.IDAO.IAuthTokenDAO;
import DAO.IDAO.bean.AuthTokenBean;
import DAO.IDAO.bean.UserBean;
import DAO.IDAO.IProfilePictureDAO;
import DAO.IDAO.IUserDAO;
import DAOFactory.AbstractDAOFactory;
import com.amazonaws.AmazonServiceException;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import request.LoginRequest;
import request.authenticated_request.AuthenticatedRequest;
import request.authenticated_request.GetUserRequest;
import request.authenticated_request.LogoutRequest;
import request.RegisterRequest;
import response.GetUserResponse;
import response.LoginResponse;
import response.LogoutResponse;
import response.RegisterResponse;
import service.utils.AuthTokenGenerator;
import service.utils.AuthTokenValidator;
import service.utils.PasswordHash;

import java.util.Base64;
import java.util.UUID;

public class UserService {
    private static final String BUCKET_NAME = "cs340tweeter-profile-pictures";
    private static final String IMAGE_FORMAT = ".jpg";

    public LoginResponse login(LoginRequest loginRequest){
        if(loginRequest.getUsername() == null || loginRequest.getPassword() == null ||
                loginRequest.getPassword().equals("") || loginRequest.getUsername().equals("")){
            throw new RuntimeException("[Bad Request] username and password cannot be empty");
        }

        IUserDAO userDAO = AbstractDAOFactory.factory().userDAO();
        UserBean bean = userDAO.find(loginRequest.getUsername());

        if(bean == null){
            throw new RuntimeException("[Bad Request] check username or password");
        }
        String passwordHash = PasswordHash.hash(bean.getSalt(), loginRequest.getPassword());

        if(!bean.getPassword_hash().equals(passwordHash)){
            throw new RuntimeException("[Bad Request] check username or password");
        }

        // at this point, it means password is correct
        User user = getUserFromBean(bean);

        AuthToken authtoken = AuthTokenGenerator.newAuthToken();

        AuthTokenBean authTokenBean = new AuthTokenBean(authtoken.getToken(), Long.parseLong(authtoken.getDatetime()));

        //save authtoken to DB
        AbstractDAOFactory.factory().authTokenDAO().insert(authTokenBean);

        return new LoginResponse(true, null, user , authtoken);
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();
        String imageBase64 = registerRequest.getImageBase64();
        if(firstName == null || firstName.equals("") ||
           lastName == null || lastName.equals("") ||
           username == null || username.equals("") ||
           password == null || password.equals("") ||
           imageBase64 == null || imageBase64.equals("")){
            throw new RuntimeException("[Bad Request] no field can be empty");
        }

        if(!username.startsWith("@")){
            throw new RuntimeException("[Bad Request] Username must start with @");
        }

        //check if user already exist
        IUserDAO dao = AbstractDAOFactory.factory().userDAO();
        UserBean bean = dao.find(registerRequest.getUsername());
        if(bean != null){ //user exists
            throw new RuntimeException("[Bad Request] Username already used");
        }

        String salt = UUID.randomUUID().toString();
        String passwordHash = PasswordHash.hash(salt, registerRequest.getPassword());

        //upload
        String pictureURL = null;
        try{
            IProfilePictureDAO pictureDAO = AbstractDAOFactory.factory().profilePictureDAO();
            byte[] imgByteArr = Base64.getDecoder().decode(imageBase64);
            pictureURL = pictureDAO.uploadPicture(username, imgByteArr);
        }catch (AmazonServiceException e){
            throw new RuntimeException("[Internal Server Error] Failed to upload image");
        }

        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        userBean.setSalt(salt);
        userBean.setPassword_hash(passwordHash);
        userBean.setFirst_name(firstName);
        userBean.setLast_name(lastName);
        userBean.setProfile_pic_link(pictureURL);
        userBean.setFollowers_count(0);
        userBean.setFollowing_count(0);

        try{
            dao.insert(userBean);
        }catch (Exception e){
            throw new RuntimeException("[Internal Server Error] Failed to register");
        }


        User user = new User(firstName, lastName, username, pictureURL);

        AuthToken authToken = AuthTokenGenerator.newAuthToken();

        AuthTokenBean authTokenBean = new AuthTokenBean(authToken.getToken(), Long.parseLong(authToken.getDatetime()));

        //save authtoken to DB
        AbstractDAOFactory.factory().authTokenDAO().insert(authTokenBean);

        return new RegisterResponse(true, null, user, authToken);
    }

    public LogoutResponse logout(LogoutRequest logoutRequest){
//        AuthTokenValidator.validate(logoutRequest.getAuthToken());
        AbstractDAOFactory.factory().authTokenDAO().delete(logoutRequest.getAuthToken().getToken());
        return new LogoutResponse(true, null);
    }


    public GetUserResponse getUser(GetUserRequest getUserRequest){
        AuthTokenValidator.validate(getUserRequest.getAuthToken());
        UserBean userBean = AbstractDAOFactory.factory().userDAO().find(getUserRequest.getUsername());
        if(userBean == null){
            throw new RuntimeException("[Bad Request] user does not exist");
        }

        User user = getUserFromBean(userBean);
        return new GetUserResponse(true, null, user);
    }

    private User getUserFromBean(UserBean bean){
        return new User(bean.getFirst_name(), bean.getLast_name(), bean.getUsername(), bean.getProfile_pic_link());
    }

    private void checkAuthToken(AuthenticatedRequest request){
        if(request.getAuthToken() == null){
            throw new RuntimeException("[Unauthorized]");
        }
        IAuthTokenDAO authTokenDAO = AbstractDAOFactory.factory().authTokenDAO();
        AuthTokenBean authTokenBean = authTokenDAO.find(request.getAuthToken().getToken());
        if(authTokenBean == null){
            throw new RuntimeException("[Bad Request] invalid authorization");
        }
        if(authTokenBean.getDatetime() < Long.parseLong(request.getAuthToken().getDatetime())){
            throw new RuntimeException("[Bad Request] expired authorization");
        }
    }
}
