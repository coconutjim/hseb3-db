package domain.realty_obj;

import java.io.Serializable;
import java.util.List;

/***
 * Realty Obj dao
 */
public interface RealtyObjectDao<T, X, Id extends Serializable> {

    void persist(T entity);

    void update(T entity);

    List<T> findByRealtor(Id id);

    void delete(T entity, List<X> bms);

    void deleteAll(List<T> objs, List<List<X>> bms);

}
