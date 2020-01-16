package domain.bookmark;

import java.io.Serializable;
import java.util.List;

/***
 * Bookmark Dao
 */
public interface BookmarkDao<T, Id extends Serializable> {

    void persist(T entity);

    void update(T entity);

    List<T> findByClient(Id id);

    List<T> findByRealtyObject(Id id);

    void delete(T entity);

    void deleteAll(List<T> bms);
}
