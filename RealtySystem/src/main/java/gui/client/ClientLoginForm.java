package gui.client;

import domain.User;
import domain.account.Account;
import domain.account.AccountService;
import domain.client.ClientService;
import gui.LoginForm;
import gui.MainController;

/***
 * Represents a client login from
 */
public class ClientLoginForm extends LoginForm {

    private AccountService accountService;
    private ClientService clientService;

    public ClientLoginForm(MainController controller) {
        super(controller);
        accountService = new AccountService();
        clientService = new ClientService();
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
        return clientService.findByAccount(account.getId());
    }
}
