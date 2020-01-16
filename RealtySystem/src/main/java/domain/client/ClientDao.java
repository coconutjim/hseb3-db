package domain.client;

import java.io.Serializable;
import java.util.List;

/***
 * Client DAO
 */
public interface ClientDao<T, X, Id extends Serializable> {

    void persist(T entity);

    void update(T entity);

    T findById(Id id);

    T findByAccount(Id id);

    void delete(T entity, List<X> bookmarks);

}
