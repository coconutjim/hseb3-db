package domain.client;

import domain.account.Account;
import domain.bookmark.Bookmark;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


/***
 * Client Hibernate DAO
 */
public class ClientHibernateDao implements ClientDao<Client, Bookmark, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public ClientHibernateDao() {
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

    public void persist(Client entity) {
        Account account = entity.getAccount();
        if (entity.getId() != null || account.getId() != null) {
            throw new IllegalArgumentException();
        }
        getCurrentSession().save(account);
        getCurrentSession().save(entity);
    }

    public void update(Client entity) {
        getCurrentSession().update(entity);
    }

    public Client findById(Integer id) {
        return (Client) getCurrentSession().get(Client.class, id);
    }

    public Client findByAccount(Integer accId) {
        Object o = getCurrentSession().createSQLQuery(
                "select * from clients t where t.account_id = :accID")
                .addEntity(Client.class)
                .setParameter("accID", accId)
                .uniqueResult();
        return (Client) o;
    }

    public void delete(Client entity, List<Bookmark> bookmarks) {
        if (bookmarks != null) {
            for (Bookmark bookmark : bookmarks) {
                getCurrentSession().delete(bookmark);
            }
        }

        getCurrentSession().delete(entity);
        getCurrentSession().delete(entity.getAccount());
    }

}
