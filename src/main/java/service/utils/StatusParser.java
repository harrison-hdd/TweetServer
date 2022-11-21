package service.utils;

import DAO.IDAO.bean.FeedBean;
import DAO.IDAO.bean.FollowBean;
import DAO.IDAO.bean.StoryBean;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatusParser {
    public static List<Status> parse(List<StoryBean> beans, User user){
        List<Status> statuses = new ArrayList<>();
        for(StoryBean bean: beans){
            Status status = new Status();
            status.datetime = new Date(bean.getTimestamp()).toString();
            status.post = bean.getPost();
            status.user = user;
            status.mentions = parseMentions(bean.getPost());
            status.urls = parseURLs(bean.getPost());
            statuses.add(status);
        }
        return statuses;
    }

    public static List<Status> parse(List<FeedBean> beans){
        List<Status> statuses = new ArrayList<>();
        for(FeedBean bean: beans){
            statuses.add(parse(bean));
        }
        return statuses;
    }

    public static Status parse(FeedBean bean){
        Status status = new Status();
        status.datetime = new Date(bean.getTimestamp()).toString();

        status.post = bean.getPost();


        User user = new User(bean.getFollowee_first_name(), bean.getFollowee_last_name(),
                bean.getFollowee_handle(), bean.getFollowee_profile_picture_link());
        status.setUser(user);

        status.mentions = parseMentions(bean.getPost());
        status.urls = parseURLs(bean.getPost());

        return status;
    }

    public static Status parse(String post, User user){
        Status status = new Status();
        status.datetime = new Date().toString();
        status.user = user;
        status.post = post;

        status.mentions = parseMentions(post);
        status.urls = parseURLs(post);
        return status;
    }
    private static List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://") ||
                    word.contains(".com") || word.contains(".org") ||
                    word.contains(".edu") || word.contains(".net") ||
                    word.contains(".mil")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private static List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private static int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public static void main(String[] args) {
//        parse("");
    }
}
