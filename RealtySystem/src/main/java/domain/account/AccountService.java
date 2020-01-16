package domain.account;

import domain.client.Client;

/***
 * Account service
 */
public class AccountService {

    private static AccountHibernateDao accountDao;

    public AccountService() {
        accountDao = new AccountHibernateDao();
    }

    public Account findAccount(String login) {
        accountDao.openCurrentSession();
        Account account = accountDao.findAccount(login);
        accountDao.closeCurrentSession();
        return account;
    }

    public Account findById(Integer id) {
        accountDao.openCurrentSession();
        Account account = accountDao.findById(id);
        accountDao.closeCurrentSession();
        return account;
    }
}
