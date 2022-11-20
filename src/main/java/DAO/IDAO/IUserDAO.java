package DAO.IDAO;

import DAO.IDAO.bean.UserBean;

public interface IUserDAO {
    UserBean find(String username);
    void updateFollowersCount(String followee, int change);
    void updateFollowingCount(String follower, int change);
    void insert(UserBean bean);
}
