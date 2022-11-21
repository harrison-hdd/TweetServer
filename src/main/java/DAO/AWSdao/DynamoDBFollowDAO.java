package DAO.AWSdao;

import DAO.AWSdao.util.CredentialsProvider;
import DAO.IDAO.IFollowDAO;
import DAO.IDAO.IUserDAO;
import DAO.IDAO.bean.FollowBean;
import DAO.IDAO.bean.UserBean;
import DAOFactory.AbstractDAOFactory;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.xspec.B;
import edu.byu.cs.tweeter.util.Pair;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DynamoDBFollowDAO implements IFollowDAO {
    public static final String tableName = "tweeter_follow";
    public static final String indexName = "index_by_followee";

    private static final String followerHandleAttr = "follower_handle";
    private static final String followeeHandleAttr = "followee_handle";

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

    public FollowBean find(String follower, String followee){
        DynamoDbTable<FollowBean> table = enhancedClient.table(tableName, TableSchema.fromBean(FollowBean.class));
        Key key = Key.builder()
                .partitionValue(follower)
                .sortValue(followee)
                .build();
        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key));

        QueryEnhancedRequest request = queryBuilder.build();

        List<FollowBean> result = table.query(request)
                .items()
                .stream()
                .collect(Collectors.toList());

        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public boolean insert(FollowBean followBean) {
        FollowBean existingBean = find(followBean.getFollower_handle(), followBean.getFollowee_handle());
        if(existingBean != null) return false; //follow relationship already exists

        DynamoDbTable<FollowBean> table = enhancedClient.table(tableName, TableSchema.fromBean(FollowBean.class));
        table.putItem(followBean);
        return true;
    }

    @Override
    public boolean remove(String followerHandle, String followeeHandle) {
        FollowBean existingBean = find(followerHandle, followeeHandle);
        if(existingBean == null) return false; //follow relationship doesn't exist, so can't remove

        DynamoDbTable<FollowBean> table = enhancedClient.table(tableName, TableSchema.fromBean(FollowBean.class));
        Key key = Key.builder()
                .partitionValue(followerHandle)
                .sortValue(followeeHandle)
                .build();
        table.deleteItem(key);
        return true;
    }

    @Override
    public Pair<List<FollowBean>, Boolean> findFollowingPagedList(String followerHandle, int pageSize, String lastFolloweeHandle) {
        DynamoDbTable<FollowBean> table = enhancedClient.table(tableName, TableSchema.fromBean(FollowBean.class));

        QueryEnhancedRequest.Builder queryBuilder = paginationQueryBuilder(followerHandle, pageSize);

        if(lastFolloweeHandle != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followerHandleAttr, AttributeValue.builder().s(followerHandle).build());
            startKey.put(followeeHandleAttr, AttributeValue.builder().s(lastFolloweeHandle).build());
            queryBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = queryBuilder.build();

        PageIterable<FollowBean> result = table.query(request);

        //number of items in page is independent of the item in items stream.
        //QueryEnhancedRequest.Builder::limit() set the number of items per page.
        Page<FollowBean> page = result.stream().collect(Collectors.toList()).get(0); //get the first page returned

        //We can also do list = result.items().stream().limit(pageSize).collect(Collectors.toList())
        List<FollowBean> list = page.items();

        return new Pair<>(list, page.lastEvaluatedKey() != null);
    }

    @Override
    public Pair<List<FollowBean>, Boolean> findFollowersPagedList(String followeeHandle, int pageSize, String lastFollowerHandle) {
        DynamoDbIndex<FollowBean> index = enhancedClient.table(tableName, TableSchema.fromBean(FollowBean.class)).index(indexName);

        QueryEnhancedRequest.Builder queryBuilder = paginationQueryBuilder(followeeHandle, pageSize);

        if(lastFollowerHandle != null){
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followeeHandleAttr, AttributeValue.builder().s(followeeHandle).build());
            startKey.put(followerHandleAttr, AttributeValue.builder().s(lastFollowerHandle).build());
            queryBuilder.exclusiveStartKey(startKey);
        }

        QueryEnhancedRequest request = queryBuilder.build();
        SdkIterable<Page<FollowBean>> result = index.query(request);
        PageIterable<FollowBean> pageIterable = PageIterable.create(result);

        Page<FollowBean> page = result.stream().collect(Collectors.toList()).get(0); //get current page


        List<FollowBean> list = page.items();
        return new Pair<>(list, page.lastEvaluatedKey() != null);
    }

    //pass pageSize = -1 to skip pagination, (i.e. get all items)
    private QueryEnhancedRequest.Builder paginationQueryBuilder(String partitionKeyValue, int pageSize){
        Key key = Key.builder()
                .partitionValue(partitionKeyValue)
                .build();

        QueryEnhancedRequest.Builder queryBuilder = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(key))
                .scanIndexForward(true);

        if(pageSize > 0){
            queryBuilder.limit(pageSize);
        }

        return queryBuilder;
    }
}
