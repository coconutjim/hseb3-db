package domain.client;


import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkHibernateDao;

import java.util.List;

/***
 * Represents a client service
 */
public class ClientService {

    private static ClientHibernateDao clientDao;
    private static BookmarkHibernateDao bmDao;

    public ClientService() {
        clientDao = new ClientHibernateDao();
        bmDao = new BookmarkHibernateDao();
    }

    public void persist(Client entity) {
        clientDao.openCurrentSessionWithTransaction();
        clientDao.persist(entity);
        clientDao.closeCurrentSessionWithTransaction();
    }

    public void update(Client entity) {
        clientDao.openCurrentSessionWithTransaction();
        clientDao.update(entity);
        clientDao.closeCurrentSessionWithTransaction();
    }

    public Client findById(Integer id) {
        clientDao.openCurrentSession();
        Client client = clientDao.findById(id);
        clientDao.closeCurrentSession();
        return client;
    }

    public Client findByAccount(Integer accId) {
        clientDao.openCurrentSession();
        Client client = clientDao.findByAccount(accId);
        clientDao.closeCurrentSession();
        return client;
    }

    public ClientHibernateDao getClientDao() {
        return clientDao;
    }

    public void delete(Client client) {

        List<Bookmark> bookmarks;

        bmDao.openCurrentSession();
        bookmarks = bmDao.findByClient(client.getId());
        bmDao.closeCurrentSession();

        clientDao.openCurrentSessionWithTransaction();
        clientDao.delete(client, bookmarks);
        clientDao.closeCurrentSessionWithTransaction();
    }
}
