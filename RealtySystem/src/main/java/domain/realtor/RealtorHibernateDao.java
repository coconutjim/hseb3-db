package domain.realtor;

import domain.account.Account;
import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkHibernateDao;
import domain.realty_obj.RealtyObject;
import domain.realty_obj.RealtyObjectHibernateDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/***
 * Realtor hibernate DAO
 */
public class RealtorHibernateDao implements RealtorDao<Realtor, RealtyObject, Bookmark, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public RealtorHibernateDao() {
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

    public void persist(Realtor entity) {
        Account account = entity.getAccount();
        if (entity.getId() != null || account.getId() != null) {
            throw new IllegalArgumentException();
        }
        getCurrentSession().save(account);
        getCurrentSession().save(entity);
    }

    public void update(Realtor entity) {
        getCurrentSession().update(entity);
    }

    public Realtor findById(Integer id) {
        return (Realtor) getCurrentSession().get(Realtor.class, id);
    }

    public Realtor findByAccount(Integer accId) {
        Object o = getCurrentSession().createSQLQuery(
                "select * from realtors t where t.account_id = :accID")
                .addEntity(Realtor.class)
                .setParameter("accID", accId)
                .uniqueResult();
        return (Realtor) o;
    }

    public void delete(Realtor entity, List<RealtyObject> objs, List<List<Bookmark>> bookmarks) {

        if (bookmarks != null) {
            for (List<Bookmark> bs : bookmarks) {
                if (bs != null) {
                    for (Bookmark b : bs) {
                        b.setRealtyObject(null);
                        getCurrentSession().update(b);
                    }
                }
            }
        }

        for (RealtyObject ro : objs) {
            getCurrentSession().delete(ro);
        }

        getCurrentSession().delete(entity);
        getCurrentSession().delete(entity.getAccount());
    }

}
