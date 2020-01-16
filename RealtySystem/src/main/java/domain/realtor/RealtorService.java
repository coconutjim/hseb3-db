package domain.realtor;

import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkHibernateDao;
import domain.realty_obj.RealtyObject;
import domain.realty_obj.RealtyObjectHibernateDao;

import java.util.ArrayList;
import java.util.List;

/***
 * Represents a realtor service
 */
public class RealtorService {

    private static RealtorHibernateDao realtorDao;
    private static RealtyObjectHibernateDao roDao;
    private static BookmarkHibernateDao bmDao;

    public RealtorService() {
        realtorDao = new RealtorHibernateDao();
        roDao = new RealtyObjectHibernateDao();
        bmDao = new BookmarkHibernateDao();
    }

    public void persist(Realtor entity) {
        realtorDao.openCurrentSessionWithTransaction();
        realtorDao.persist(entity);
        realtorDao.closeCurrentSessionWithTransaction();
    }

    public void update(Realtor entity) {
        realtorDao.openCurrentSessionWithTransaction();
        realtorDao.update(entity);
        realtorDao.closeCurrentSessionWithTransaction();
    }

    public Realtor findById(Integer id) {
        realtorDao.openCurrentSession();
        Realtor realtor = realtorDao.findById(id);
        realtorDao.closeCurrentSession();
        return realtor;
    }

    public Realtor findByAccount(Integer accId) {
        realtorDao.openCurrentSession();
        Realtor realtor = realtorDao.findByAccount(accId);
        realtorDao.closeCurrentSession();
        return realtor;
    }

    public static RealtorHibernateDao getRealtorDao() {
        return realtorDao;
    }

    public void delete(Realtor realtor) {

        List<RealtyObject> objects;
        List<List<Bookmark>> bms = new ArrayList<List<Bookmark>>();

        roDao.openCurrentSession();
        objects = roDao.findByRealtor(realtor.getId());
        roDao.closeCurrentSession();

        bmDao.openCurrentSession();
        if (objects != null) {
            for (RealtyObject obj : objects) {
                List<Bookmark> bm = bmDao.findByRealtyObject(obj.getId());
                if (bm != null) {
                    bms.add(bm);
                }
            }
        }
        bmDao.closeCurrentSession();

        realtorDao.openCurrentSessionWithTransaction();
        realtorDao.delete(realtor, objects, bms);
        realtorDao.closeCurrentSessionWithTransaction();
    }
}
