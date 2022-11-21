package DAO.IDAO;

import DAO.IDAO.bean.StoryBean;
import edu.byu.cs.tweeter.util.Pair;

import java.util.List;

public interface IStoryDAO {
    void insert(StoryBean bean);
    Pair<List<StoryBean>, Boolean> get(String username, int pageSize, Long lastTimestamp);
}
