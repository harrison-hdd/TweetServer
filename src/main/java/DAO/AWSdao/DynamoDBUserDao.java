package DAO.AWSdao;


import DAO.AWSdao.util.CredentialsProvider;
import DAO.IDAO.IUserDAO;
import DAO.IDAO.bean.UserBean;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

public final class DynamoDBUserDao implements IUserDAO {
    private static final String tableName = "tweeter_user";

    private static final String usernameAttr = "username";
    private static final String saltAttr = "salt";
    private static final String passwordHashAttr = "password_hash";
    private static final String firstNameAttr = "first_name";
    private static final String lastNameAttr = "last_name";
    private static final String profilePicLinkAttr = "profile_pic_link";
    private static final String followingCountAttr = "following_count";
    private static final String followersCountAttr = "followers_count";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-east-1")
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    //before building jar for AWS lambda,
    // change ProfileCredentialsProvider to EnvironmentVariableCredentialsProvider

    private static DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .credentialsProvider(CredentialsProvider.getProvider())
            .region(Region.US_EAST_1)
            .build();

    private static DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

    @Override
    public UserBean find(String username) {
        DynamoDbTable<UserBean> userTable = enhancedClient.table(tableName, TableSchema.fromBean(UserBean.class));


        Key key = Key.builder()
                .partitionValue(username)
                .build();

        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key));

        QueryEnhancedRequest request = queryBuilder.build();

        List<UserBean> result = userTable.query(request)
                .items()
                .stream()
                .collect(Collectors.toList());

        if(result.isEmpty()) return null;

        return result.get(0);
    }

    @Override
    public void updateFollowersCount(String followeeHandle, int change) {
        UserBean followeeBean = find(followeeHandle);
        if(followeeBean == null){
            throw new RuntimeException("[Internal Server Error] Database Error");
        }
        followeeBean.setFollowers_count(followeeBean.getFollowers_count() + change);
        update(followeeBean);
    }

    @Override
    public void updateFollowingCount(String followerHandle, int change) {
        UserBean followerBean = find(followerHandle);
        if(followerBean == null){
            throw new RuntimeException("[Internal Server Error] Database Error");
        }
        followerBean.setFollowing_count(followerBean.getFollowing_count() + change);
        update(followerBean);
    }

    private void update(UserBean bean){
        DynamoDbTable<UserBean> userTable = enhancedClient.table(tableName, TableSchema.fromBean(UserBean.class));
        userTable.updateItem(bean);
    }
    @Override
    public void insert(UserBean bean) {
        DynamoDbTable<UserBean> userTable = enhancedClient.table(tableName, TableSchema.fromBean(UserBean.class));
        userTable.putItem(bean);
    }

}
