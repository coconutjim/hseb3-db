package domain.account;

import java.io.Serializable;

/***
 * Account DAO
 */
public interface AccountDao<T, Id extends Serializable> {

    T findById(Id id);

    T findAccount(String login);
}
