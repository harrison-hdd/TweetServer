package DAO.AWSdao;

import DAO.AWSdao.util.CredentialsProvider;
import DAO.IDAO.IStoryDAO;
import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.StoryBean;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoDBStoryDAO implements IStoryDAO {
    private static final String tableName = "tweeter_story";

    private static final String timestampAttr = "timestamp";
    private static final String usernameAttr = "username";

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
    public void insert(StoryBean bean) {
        DynamoDbTable<StoryBean> table = enhancedClient.table(tableName, TableSchema.fromBean(StoryBean.class));
        table.putItem(bean);
    }

    @Override
    public Pair<List<StoryBean>, Boolean> get(String username, int pageSize, Long lastTimestamp) {
        DynamoDbTable<StoryBean> table = enhancedClient.table(tableName, TableSchema.fromBean(StoryBean.class));
        Key key = Key.builder()
                .partitionValue(username)
                .build();

        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize)
                .scanIndexForward(false);

        if(lastTimestamp != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(usernameAttr, AttributeValue.builder().s(username).build());
            startKey.put(timestampAttr, AttributeValue.builder().n(String.valueOf(lastTimestamp)).build());
            queryBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = queryBuilder.build();
        PageIterable<StoryBean> result = table.query(request);

        Page<StoryBean> page = result.stream().collect(Collectors.toList()).get(0);
        List<StoryBean> list = page.items();

        return new Pair<>(list, page.lastEvaluatedKey() != null);
    }


}
