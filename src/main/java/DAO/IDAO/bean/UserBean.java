package DAO.IDAO.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public final class UserBean {
    private String username;
    private String salt;
    private String password_hash;
    private String first_name;
    private String last_name;
    private String profile_pic_link;
    private int followers_count;
    private int following_count;

    public UserBean(){}

    public UserBean(String username, String salt, String password_hash,
                    String first_name, String last_name, String profile_pic_link,
                    int followers_count, int following_count) {
        this.username = username;
        this.salt = salt;
        this.password_hash = password_hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_pic_link = profile_pic_link;
        this.followers_count = followers_count;
        this.following_count = following_count;
    }

    @DynamoDbPartitionKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_pic_link() {
        return profile_pic_link;
    }

    public void setProfile_pic_link(String profile_pic_link) {
        this.profile_pic_link = profile_pic_link;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }
}
