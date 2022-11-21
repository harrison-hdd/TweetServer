package DAOFactory;

import DAO.AWSdao.*;
import DAO.IDAO.*;

public class AWSDAOFactory extends AbstractDAOFactory {


    @Override
    public IAuthTokenDAO authTokenDAO() {
        return new DynamoDBAuthTokenDAO();
    }

    @Override
    public IFeedDAO feedDAO() {
        return new DynamoDBFeedDAO();
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
        return new DynamoDBStoryDAO();
    }

    @Override
    public IUserDAO userDAO() {
        return new DynamoDBUserDao();
    }
}
