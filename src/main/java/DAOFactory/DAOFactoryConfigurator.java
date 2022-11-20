package DAOFactory;

public class DAOFactoryConfigurator {
    private static boolean factoryAlreadyConfigured = false;

    public static void configure(){
        if(factoryAlreadyConfigured) return;
        AbstractDAOFactory dao = new AWSDAOFactory();
        AbstractDAOFactory.setInstance(new AWSDAOFactory());
        factoryAlreadyConfigured = false;
    }
}
