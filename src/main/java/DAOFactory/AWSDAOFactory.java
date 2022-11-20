package DAOFactory;

import DAO.AWSdao.DynamoDBAuthTokenDAO;
import DAO.AWSdao.DynamoDBFollowDAO;
import DAO.AWSdao.DynamoDBUserDao;
import DAO.IDAO.*;
import DAO.AWSdao.S3DAO;

public class AWSDAOFactory extends AbstractDAOFactory {


    @Override
    public IAuthTokenDAO authTokenDAO() {
        return new DynamoDBAuthTokenDAO();
    }

    @Override
    public IFeedDAO feedDAO() {
        return null;
    }

    @Override
    public IFollowDAO followDAO() {
        return new DynamoDBFollowDAO();
    }

    @Override
    public IProfilePictureDAO profilePictureDAO() {
        return new S3DAO();
    }

    @Override
    public IStoryDAO storyDAO() {
        return null;
    }

    @Override
    public IUserDAO userDAO() {
        return new DynamoDBUserDao();
    }
}
