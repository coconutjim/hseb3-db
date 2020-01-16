package domain.realtor;

import java.io.Serializable;
import java.util.List;

/***
 * Realtor dao
 */
public interface RealtorDao<T, X, Y, Id extends Serializable> {

    void persist(T entity);

    void update(T entity);

    T findById(Id id);

    T findByAccount(Id id);

    void delete(T entity, List<X> objs, List<List<Y>> bms);

}
