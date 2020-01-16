package gui;

import domain.User;
import domain.account.Account;

/***
 * Represents a main controller
 */
public class MainController {

    /** Login form */
    private LoginForm loginForm;

    /** Registration form */
    private RegistrationForm registrationForm;

    /** Main form */
    private MainForm mainForm;

    /***
     * Constructor
     */
    public MainController() {

    }

    public void start() {
        loginForm.setVisible(true);
    }

    public void loggedIn(User user) {
        loginForm.dispose();
        mainForm.setUser(user);
        mainForm.setVisible(true);
    }

    public void registration() {
        loginForm.dispose();
        registrationForm.setVisible(true);
    }


    public void outReg() {
        registrationForm.dispose();
        loginForm.setVisible(true);
    }

    public void logOut() {
        mainForm.dispose();
        loginForm.setVisible(true);
    }

    public void showMainForm() {
        mainForm.setVisible(true);
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public void setRegistrationForm(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }
}
