package DAO.IDAO.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public final class FeedBean {
    private String follower_handle;
    private String followee_handle;
    private String followee_first_name;
    private String followee_last_name;
    private String followee_profile_picture_link;
    private String post;
    private long timestamp;

    public FeedBean(){}

    public FeedBean(String follower_handle, String followee_handle,
                    String followee_first_name, String followee_last_name,
                    String followee_profile_picture_link, String post, long timestamp) {
        this.follower_handle = follower_handle;
        this.followee_handle = followee_handle;
        this.followee_first_name = followee_first_name;
        this.followee_last_name = followee_last_name;
        this.followee_profile_picture_link = followee_profile_picture_link;
        this.post = post;
        this.timestamp = timestamp;
    }

    @DynamoDbPartitionKey
    public String getFollower_handle() {
        return follower_handle;
    }

    public void setFollower_handle(String follower_handle) {
        this.follower_handle = follower_handle;
    }

    public String getFollowee_handle() {
        return followee_handle;
    }

    public void setFollowee_handle(String followee_handle) {
        this.followee_handle = followee_handle;
    }

    public String getFollowee_first_name() {
        return followee_first_name;
    }

    public void setFollowee_first_name(String followee_first_name) {
        this.followee_first_name = followee_first_name;
    }

    public String getFollowee_last_name() {
        return followee_last_name;
    }

    public void setFollowee_last_name(String followee_last_name) {
        this.followee_last_name = followee_last_name;
    }

    public String getFollowee_profile_picture_link() {
        return followee_profile_picture_link;
    }

    public void setFollowee_profile_picture_link(String followee_profile_picture_link) {
        this.followee_profile_picture_link = followee_profile_picture_link;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @DynamoDbSortKey
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
