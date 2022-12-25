package DAO.AWSdao;

import DAO.AWSdao.util.CredentialsProvider;
import DAO.IDAO.IFeedDAO;
import DAO.IDAO.bean.FeedBean;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.*;
import java.util.stream.Collectors;

public final class DynamoDBFeedDAO implements IFeedDAO {
    private static final String tableName = "tweeter_feed";

    private static final String followerHandleAttr = "follower_handle";
    private static final String timestampAttr = "timestamp";

    private static final String followeeHandleAttr = "followee_handle";
    private static final String followeeFirstNameAttr = "followee_first_name";
    private static final String followeeLastNameAttr = "followee_last_name";
    private static final String followeeProfilePicLinkAttr = "followee_profile_picture_link";
    private static final String postAttr = "post";

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
    public Pair<List<FeedBean>, Boolean> getFeed(String followerHandle, int pageSize, Long lastTimestamp) {
        DynamoDbTable<FeedBean> table = enhancedClient.table(tableName, TableSchema.fromBean(FeedBean.class));
        Key key = Key.builder()
                .partitionValue(followerHandle)
                .build();

        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .limit(pageSize)
                .scanIndexForward(false);

        if(lastTimestamp != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followerHandleAttr, AttributeValue.builder().s(followerHandle).build());
            startKey.put(timestampAttr, AttributeValue.builder().n(String.valueOf(lastTimestamp)).build());
            queryBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = queryBuilder.build();
        PageIterable<FeedBean> result = table.query(request);

        Page<FeedBean> page = result.stream().collect(Collectors.toList()).get(0);
        List<FeedBean> list = page.items();

        return new Pair<>(list, page.lastEvaluatedKey() != null);
    }

    @Override
    public void insert(List<User> followers, User poster, String post) {

        TableWriteItems request = new TableWriteItems(tableName);
        Collection<Item> itemsToPut = new ArrayList<>();

        long timestamp = new Date().getTime();


        for(User follower: followers){
            Item item = new Item().with(followerHandleAttr, follower.getAlias())
                    .with(followeeHandleAttr, poster.getAlias())
                    .with(followeeFirstNameAttr, poster.getFirstName())
                    .with(followeeLastNameAttr, poster.getLastName())
                    .with(followeeProfilePicLinkAttr, poster.getImageUrl())
                    .with(postAttr, post)
                    .with(timestampAttr, timestamp);
            itemsToPut.add(item);

            if(itemsToPut.size() == 25){
                request.withItemsToPut(itemsToPut);
                dynamoDB.batchWriteItem(request);
                itemsToPut.clear();
            }
        }

        request.withItemsToPut(itemsToPut);
        dynamoDB.batchWriteItem(request);
    }

    @Override
    public void insert(List<FeedBean> beans) {
        System.out.println("num feeds being inserted:" + beans.size());

        if(beans.isEmpty()) return;
        TableWriteItems request = new TableWriteItems(tableName);
        Collection<Item> itemsToPut = new ArrayList<>();

        for(FeedBean bean: beans){
            Item item = new Item().with(followerHandleAttr, bean.getFollower_handle())
                    .with(followeeHandleAttr, bean.getFollowee_handle())
                    .with(followeeFirstNameAttr, bean.getFollowee_first_name())
                    .with(followeeLastNameAttr, bean.getFollowee_last_name())
                    .with(followeeProfilePicLinkAttr, bean.getFollowee_profile_picture_link())
                    .with(postAttr, bean.getPost())
                    .with(timestampAttr, bean.getTimestamp());
            itemsToPut.add(item);

            if(itemsToPut.size() == 25){
                request.withItemsToPut(itemsToPut);
                BatchWriteItemOutcome result = dynamoDB.batchWriteItem(request);

                while(!result.getUnprocessedItems().isEmpty()){
                    result = dynamoDB.batchWriteItemUnprocessed(result.getUnprocessedItems());
                }
                itemsToPut.clear();
            }
        }

        if(itemsToPut.size() == 0) return;

        request.withItemsToPut(itemsToPut);
        BatchWriteItemOutcome result = dynamoDB.batchWriteItem(request);
        while(!result.getUnprocessedItems().isEmpty()){
            result = dynamoDB.batchWriteItemUnprocessed(result.getUnprocessedItems());
        }
    }
}
