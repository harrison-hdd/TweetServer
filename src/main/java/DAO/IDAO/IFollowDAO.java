package DAO.IDAO;

import DAO.IDAO.bean.FollowBean;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

import java.util.List;

public interface IFollowDAO {
    FollowBean find(String followerHandle, String followeeHandle);

    /**return false if follow bean is already in the table, true if inserted successfully*/
    boolean insert(FollowBean followBean);

    /**return false if follow bean is not in table, true if removed successfully*/
    boolean remove(String followerHandle, String followeeHandle);
    Pair<List<FollowBean>, Boolean> findFollowingPagedList(String followerHandle, int pageSize, String lastFolloweeHandle);
    Pair<List<FollowBean>, Boolean> findFollowersPagedList(String followeeHandle, int pageSize, String lastFollowerHandle);
}
