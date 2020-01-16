package gui.realtor;

import domain.User;
import domain.account.Account;
import domain.account.AccountService;
import domain.realtor.RealtorService;
import gui.LoginForm;
import gui.MainController;

/***
 * Represents a realtor login from
 */
public class RealtorLoginForm extends LoginForm {

    private AccountService accountService;
    private RealtorService realtorService;

    public RealtorLoginForm(MainController controller) {
        super(controller);
        accountService = new AccountService();
        realtorService = new RealtorService();
    }

    @Override
    public User login(String login, String password) throws Exception {
        Account account = accountService.findAccount(login);
        if (account == null) {
            throw new NullPointerException();
        }
        if (! account.checkPassword(password)) {
            throw new IllegalArgumentException();
        }
        return realtorService.findByAccount(account.getId());
    }
}
