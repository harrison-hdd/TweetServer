package service.utils;

import DAO.IDAO.IAuthTokenDAO;
import DAO.IDAO.bean.AuthTokenBean;
import DAOFactory.AbstractDAOFactory;
import edu.byu.cs.tweeter.model.domain.AuthToken;

import java.util.Date;

public class AuthTokenValidator {
    public static void validate(AuthToken authToken){
        if(authToken == null){
            throw new RuntimeException("[Unauthorized]");
        }
        IAuthTokenDAO authTokenDAO = AbstractDAOFactory.factory().authTokenDAO();
        AuthTokenBean authTokenBean = authTokenDAO.find(authToken.getToken());
        if(authTokenBean == null){
            throw new RuntimeException("[Bad Request] invalid authorization");
        }
        if(authTokenBean.getDatetime() < new Date().getTime()){
            throw new RuntimeException("[Bad Request] expired authorization");
        }
    }
}
