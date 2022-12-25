package DAOFactory;

import DAO.IDAO.*;

public abstract class AbstractDAOFactory {
    private static AbstractDAOFactory instance;
    private static boolean instanceAlreadySet = false;

    public static AbstractDAOFactory factory(){
        if(instance == null) DAOFactoryConfigurator.configure();
        return instance;
    }

    public static void setInstance(AbstractDAOFactory instance){
        if(instanceAlreadySet) return;
        AbstractDAOFactory.instance = instance;
        instanceAlreadySet = true;
    }

    public abstract IAuthTokenDAO authTokenDAO();
    public abstract IFeedDAO feedDAO();
    public abstract IFollowDAO followDAO();
    public abstract IProfilePictureDAO profilePictureDAO();
    public abstract IStoryDAO storyDAO();
    public abstract IUserDAO userDAO();
}
