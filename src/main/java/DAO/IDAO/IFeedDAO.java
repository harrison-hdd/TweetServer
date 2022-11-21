package DAO.IDAO;

import DAO.IDAO.bean.FeedBean;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

import java.util.List;

public interface IFeedDAO {
    Pair<List<FeedBean>, Boolean> getFeed(String username, int pageSize, Long lastTimestamp);
    void insert(List<User> followers, User poster, String post);

    void insert(List<FeedBean> beans);

}
