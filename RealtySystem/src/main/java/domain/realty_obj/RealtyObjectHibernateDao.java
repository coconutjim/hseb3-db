package domain.realty_obj;

import domain.bookmark.Bookmark;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;
import org.hibernate.criterion.Restrictions;

/***
 * Realize
 */
public class RealtyObjectHibernateDao implements RealtyObjectDao<RealtyObject, Bookmark, Integer> {

    private Session currentSession;

    private Transaction currentTransaction;

    public RealtyObjectHibernateDao() {
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

    public void persist(RealtyObject entity) {
        if (entity.getId() != null ) {
            throw new IllegalArgumentException();
        }
        getCurrentSession().save(entity);
    }

    public void update(RealtyObject entity) {
        getCurrentSession().update(entity);
    }

    public List<RealtyObject> findByRealtor(Integer reId) {
        Query query = getCurrentSession().createSQLQuery(
                "select * from realty_objects t where t.realtor_id = :reID")
                .addEntity(RealtyObject.class)
                .setParameter("reID", reId);
        return query.list();
    }

    public List<RealtyObject> findByCriteria(String city,
                                             Float minSquare,
                                             Float maxSquare,
                                             Float minPrice,
                                             Float maxPrice) {
        Criteria criteria = getCurrentSession().createCriteria(RealtyObject.class);
        if (city != null) {
            criteria.add(Restrictions.eq("city", city));
        }
        if (minSquare != null) {
            criteria.add(Restrictions.ge("square", minSquare));
        }
        if (maxSquare != null) {
            criteria.add(Restrictions.le("square", maxSquare));
        }
        if (minPrice != null) {
            criteria.add(Restrictions.ge("price", minPrice));
        }
        if (maxPrice != null) {
            criteria.add(Restrictions.le("price", maxPrice));
        }
        return criteria.list();
    }

    public void delete(RealtyObject entity, List<Bookmark> bookmarks) {
        if (bookmarks != null) {
            for (Bookmark bookmark : bookmarks) {
                bookmark.setRealtyObject(null);
                getCurrentSession().update(bookmark);
            }
        }
        getCurrentSession().delete(entity);
    }

    public void deleteAll(List<RealtyObject> objs, List<List<Bookmark>> bookmarks) {

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
    }
}
