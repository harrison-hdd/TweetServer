package DAO.IDAO;

import DAO.IDAO.bean.AuthTokenBean;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface IAuthTokenDAO {
    AuthTokenBean find(String token);
    void insert(AuthTokenBean authToken);
    void delete(String token);
}
