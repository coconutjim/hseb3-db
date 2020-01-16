package domain.bookmark;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/***
 * Realize
 */
public class BookmarkHibernateDao implements BookmarkDao<Bookmark, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public BookmarkHibernateDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Bookmark entity) {
        if (entity.getId() != null ) {
            throw new IllegalArgumentException();
        }
        getCurrentSession().save(entity);
    }

    public void update(Bookmark entity) {
        getCurrentSession().update(entity);
    }

    public List<Bookmark> findByClient(Integer clId) {
        Query query = getCurrentSession().createSQLQuery(
                "select * from bookmarks t where t.client_id = :clID")
                .addEntity(Bookmark.class)
                .setParameter("clID", clId);
        return query.list();
    }

    public List<Bookmark> findByRealtyObject(Integer roId) {
        Query query = getCurrentSession().createSQLQuery(
                "select * from bookmarks t where t.realty_object_id = :roID")
                .addEntity(Bookmark.class)
                .setParameter("roID", roId);
        return query.list();
    }

    public void delete(Bookmark entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteAll(List<Bookmark> bookmarks) {
        if (bookmarks != null) {
            for (Bookmark bookmark : bookmarks) {
                getCurrentSession().delete(bookmark);
            }
        }
    }
}