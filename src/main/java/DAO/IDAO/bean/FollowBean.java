package DAO.IDAO.bean;

import DAO.AWSdao.DynamoDBFollowDAO;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class FollowBean {
    private String follower_handle;
    private String follower_first_name;
    private String follower_last_name;
    private String follower_profile_pic_link;

    private String followee_handle;
    private String followee_first_name;
    private String followee_last_name;
    private String followee_profile_pic_link;

    public FollowBean(){}

    public FollowBean(String follower_handle, String follower_first_name,
                      String follower_last_name, String follower_profile_pic_link,
                      String followee_handle, String followee_first_name,
                      String followee_last_name, String followee_profile_pic_link) {
        this.follower_handle = follower_handle;
        this.follower_first_name = follower_first_name;
        this.follower_last_name = follower_last_name;
        this.follower_profile_pic_link = follower_profile_pic_link;
        this.followee_handle = followee_handle;
        this.followee_first_name = followee_first_name;
        this.followee_last_name = followee_last_name;
        this.followee_profile_pic_link = followee_profile_pic_link;
    }

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = DynamoDBFollowDAO.indexName)
    public String getFollower_handle() {
        return follower_handle;
    }

    public void setFollower_handle(String follower_handle) {
        this.follower_handle = follower_handle;
    }

    public String getFollower_first_name() {
        return follower_first_name;
    }

    public void setFollower_first_name(String follower_first_name) {
        this.follower_first_name = follower_first_name;
    }

    public String getFollower_last_name() {
        return follower_last_name;
    }

    public void setFollower_last_name(String follower_last_name) {
        this.follower_last_name = follower_last_name;
    }

    public String getFollower_profile_pic_link() {
        return follower_profile_pic_link;
    }

    public void setFollower_profile_pic_link(String follower_profile_pic_link) {
        this.follower_profile_pic_link = follower_profile_pic_link;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = DynamoDBFollowDAO.indexName)
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

    public String getFollowee_profile_pic_link() {
        return followee_profile_pic_link;
    }

    public void setFollowee_profile_pic_link(String followee_profile_pic_link) {
        this.followee_profile_pic_link = followee_profile_pic_link;
    }
}
