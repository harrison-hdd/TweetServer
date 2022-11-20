package DAO.AWSdao;

import DAO.IDAO.bean.AuthTokenBean;
import DAO.AWSdao.util.CredentialsProvider;
import DAO.IDAO.IAuthTokenDAO;
import DAO.IDAO.bean.UserBean;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

public final class DynamoDBAuthTokenDAO implements IAuthTokenDAO {
    private static final String tableName = "tweeter_authtoken";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-east-1")
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private static DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .credentialsProvider(CredentialsProvider.getProvider())
            .region(Region.US_EAST_1)
            .build();

    private static DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

    @Override
    public AuthTokenBean find(String token) {
        DynamoDbTable<AuthTokenBean> table = enhancedClient.table(tableName, TableSchema.fromBean(AuthTokenBean.class));
        Key key = Key.builder()
                .partitionValue(token)
                .build();
        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key));

        QueryEnhancedRequest request = queryBuilder.build();
        List<AuthTokenBean> result = table.query(request)
                .items()
                .stream()
                .collect(Collectors.toList());

        if(result.isEmpty()) return null;

        return result.get(0);
    }

    @Override
    public void insert(AuthTokenBean bean) {
        DynamoDbTable<AuthTokenBean> table = enhancedClient.table(tableName, TableSchema.fromBean(AuthTokenBean.class));
        table.putItem(bean);
    }

    @Override
    public void delete(String token) {
        DynamoDbTable<AuthTokenBean> table = enhancedClient.table(tableName, TableSchema.fromBean(AuthTokenBean.class));
        Key key = Key.builder()
                .partitionValue(token)
                .build();
        table.deleteItem(key);
    }
}
