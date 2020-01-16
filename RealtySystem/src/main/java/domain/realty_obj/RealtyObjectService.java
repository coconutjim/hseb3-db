package domain.realty_obj;

import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkHibernateDao;
import domain.realtor.Realtor;
import gui.ImageResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/***
 * RO service
 */
public class RealtyObjectService {

    private static RealtyObjectHibernateDao roDao;
    private static BookmarkHibernateDao bmDao;

    public RealtyObjectService() {
        roDao = new RealtyObjectHibernateDao();
        bmDao = new BookmarkHibernateDao();
    }

    public void persist(RealtyObject entity, File file) {
        if (file != null) {
            String url = ImageResource.generateId();
            entity.setImageUrl(url);
            ImageResource.copyFile(file, url);
        }
        roDao.openCurrentSessionWithTransaction();
        roDao.persist(entity);
        roDao.closeCurrentSessionWithTransaction();
    }

    public void update(RealtyObject entity, String url, File file) {
        if (file != null) {
            String newUrl = ImageResource.generateId();
            entity.setImageUrl(newUrl);
            ImageResource.copyFile(file, newUrl);
        }
        roDao.openCurrentSessionWithTransaction();
        roDao.update(entity);
        roDao.closeCurrentSessionWithTransaction();

        if (url != null) {
            ImageResource.deleteFile(url);
        }
    }

    public List<RealtyObject> findByRealtor(Integer id) {
        roDao.openCurrentSession();
        List<RealtyObject> realtor = roDao.findByRealtor(id);
        roDao.closeCurrentSession();
        return realtor;
    }

    public static RealtyObjectHibernateDao getRoDao() {
        return roDao;
    }

    public void delete(RealtyObject realtyObject) {

        String url = realtyObject.getImageUrl();

        List<Bookmark> bookmarks;

        bmDao.openCurrentSession();
        bookmarks = bmDao.findByRealtyObject(realtyObject.getId());
        bmDao.closeCurrentSession();

        roDao.openCurrentSessionWithTransaction();
        roDao.delete(realtyObject, bookmarks);
        roDao.closeCurrentSessionWithTransaction();

        if (url != null) {
            ImageResource.deleteFile(url);
        }
    }

    public void deleteAll(Realtor realtor) {

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

        roDao.openCurrentSessionWithTransaction();
        roDao.deleteAll(objects, bms);
        roDao.closeCurrentSessionWithTransaction();

        if (objects != null) {
            for (RealtyObject object : objects) {
                String url = object.getImageUrl();
                if (url != null) {
                    ImageResource.deleteFile(url);
                }
            }
        }
    }
}
