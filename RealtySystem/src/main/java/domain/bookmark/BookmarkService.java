package domain.bookmark;

import domain.client.Client;

import java.util.List;

/***
 * BM service
 */
public class BookmarkService {

    private static BookmarkHibernateDao bmDao;

    public BookmarkService() {
        bmDao = new BookmarkHibernateDao();
    }

    public void persist(Bookmark entity) {
        bmDao.openCurrentSessionWithTransaction();
        bmDao.persist(entity);
        bmDao.closeCurrentSessionWithTransaction();
    }

    public void update(Bookmark entity) {
        bmDao.openCurrentSessionWithTransaction();
        bmDao.update(entity);
        bmDao.closeCurrentSessionWithTransaction();
    }

    public List<Bookmark> findByClient(Integer id) {
        bmDao.openCurrentSession();
        List<Bookmark> realtor = bmDao.findByClient(id);
        bmDao.closeCurrentSession();
        return realtor;
    }

    public static BookmarkHibernateDao getBmDao() {
        return bmDao;
    }

    public void delete(Bookmark realtyObject) {
        bmDao.openCurrentSessionWithTransaction();
        bmDao.delete(realtyObject);
        bmDao.closeCurrentSessionWithTransaction();
    }

    public void deleteAll(Client client) {
        List<Bookmark> bookmarks;

        bmDao.openCurrentSession();
        bookmarks = bmDao.findByClient(client.getId());
        bmDao.closeCurrentSession();

        if (bookmarks != null) {
            bmDao.openCurrentSessionWithTransaction();
            bmDao.deleteAll(bookmarks);
            bmDao.closeCurrentSessionWithTransaction();
        }
    }
}
