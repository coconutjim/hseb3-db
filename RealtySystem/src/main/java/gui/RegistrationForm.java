package gui;

import domain.account.Account;
import domain.account.AccountService;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

/***
 * Represents a registration form
 */
public class RegistrationForm extends JFrame {

    protected MainController controller;
    private static AccountService accountService;

    private static Pattern patternLogin;
    private static Pattern patternPassword;
    private static Pattern patternName;
    private static Pattern patternEmail;
    private static Pattern patternPhone;
    private static Pattern patternWebsite;

    public RegistrationForm(MainController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("RegistrationForm: controller is null!");
        }
        this.controller = controller;
        accountService = new AccountService();
        patternLogin = Pattern.compile("^[a-zA-Z0-9]{6,12}$");
        patternPassword = patternLogin;
        patternName = Pattern.compile("^[a-zA-Z]{1,15}$");
        patternEmail = Pattern.compile("^[[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})]{1,100}$");
        patternPhone = Pattern.compile("[0-9]{11}$");
        patternWebsite = Pattern.compile("^[((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)]{1,100}$");
    }

    public static boolean checkClient(Component component,
                                      String fName,
                                      String lName, String email,
                                      String number) {
        if (fName != null && ! fName.equals("") && ! patternName.matcher(fName).matches()) {
            JOptionPane.showMessageDialog(component, "First name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lName != null && ! lName.equals("") && ! patternName.matcher(lName).matches()) {
            JOptionPane.showMessageDialog(component, "Last name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email != null && ! email.equals("") && ! patternEmail.matcher(email).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid e-mail!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (number != null && ! number.equals("") && ! patternPhone.matcher(number).matches()) {
            JOptionPane.showMessageDialog(component, "Phone number must " +
                            "contain 11 numbers!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    public static boolean checkClient(Component component,
                                      String login, String password,
                                      String conf, String fName,
                                      String lName, String email,
                                      String number) {
        if (login == null || login.equals("") ||
                password == null || password.equals("") ||
                conf == null || conf.equals("")) {
            JOptionPane.showMessageDialog(component, "Please, fill all necessary fields!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! patternLogin.matcher(login).matches()) {
            JOptionPane.showMessageDialog(component, "Login can contain only " +
                    "English letters or numbers and must be from 6 to 12 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! patternPassword.matcher(password).matches()) {
            JOptionPane.showMessageDialog(component, "Password can contain only " +
                    "English letters or numbers and must be from 6 to 12 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! password.equals(conf)) {
            JOptionPane.showMessageDialog(component, "Passwords are not equal!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (fName != null && ! fName.equals("") && ! patternName.matcher(fName).matches()) {
            JOptionPane.showMessageDialog(component, "First name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lName != null && ! lName.equals("") && ! patternName.matcher(lName).matches()) {
            JOptionPane.showMessageDialog(component, "Last name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email != null && ! email.equals("") && ! patternEmail.matcher(email).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid e-mail!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (number != null && ! number.equals("") && ! patternPhone.matcher(number).matches()) {
            JOptionPane.showMessageDialog(component, "Phone number must " +
                            "contain 11 numbers!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Account account = accountService.findAccount(login);
            if (account != null) {
                JOptionPane.showMessageDialog(component,
                        "This login is occupied already!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(component, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkRealtor(Component component,
                                       String fName,
                                       String lName, String email,
                                       String number, String cName,
                                       String cWebsite) {
        if (fName != null && ! fName.equals("") && ! patternName.matcher(fName).matches()) {
            JOptionPane.showMessageDialog(component, "First name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lName != null && ! lName.equals("") && ! patternName.matcher(lName).matches()) {
            JOptionPane.showMessageDialog(component, "Last name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email != null && ! email.equals("") && ! patternEmail.matcher(email).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid e-mail!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (number != null && ! number.equals("") && ! patternPhone.matcher(number).matches()) {
            JOptionPane.showMessageDialog(component, "Phone number must " +
                            "contain 11 numbers!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cName != null && ! cName.equals("") && ! patternName.matcher(cName).matches()) {
            JOptionPane.showMessageDialog(component, "Company name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cWebsite != null && ! cWebsite.equals("") && ! patternWebsite.matcher(cWebsite).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid website!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean checkRealtor(Component component,
                                       String login, String password,
                                       String conf, String fName,
                                       String lName, String email,
                                       String number, String cName,
                                       String cWebsite) {
        if (login == null || login.equals("") ||
                password == null || password.equals("") ||
                conf == null || conf.equals("")) {
            JOptionPane.showMessageDialog(component, "Please, fill all necessary fields!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! patternLogin.matcher(login).matches()) {
            JOptionPane.showMessageDialog(component, "Login can contain only " +
                            "English letters or numbers and must be from 6 to 12 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! patternPassword.matcher(password).matches()) {
            JOptionPane.showMessageDialog(component, "Password can contain only " +
                            "English letters or numbers and must be from 6 to 12 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (! password.equals(conf)) {
            JOptionPane.showMessageDialog(component, "Passwords are not equal!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (fName != null && ! fName.equals("") && ! patternName.matcher(fName).matches()) {
            JOptionPane.showMessageDialog(component, "First name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lName != null && ! lName.equals("") && ! patternName.matcher(lName).matches()) {
            JOptionPane.showMessageDialog(component, "Last name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email != null && ! email.equals("") && ! patternEmail.matcher(email).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid e-mail!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (number != null && ! number.equals("") && ! patternPhone.matcher(number).matches()) {
            JOptionPane.showMessageDialog(component, "Phone number must " +
                            "contain 11 numbers!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cName != null && ! cName.equals("") && ! patternName.matcher(cName).matches()) {
            JOptionPane.showMessageDialog(component, "Company name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cWebsite != null && ! cWebsite.equals("") && ! patternWebsite.matcher(cWebsite).matches()) {
            JOptionPane.showMessageDialog(component, "Invalid website!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Account account = accountService.findAccount(login);
            if (account != null) {
                JOptionPane.showMessageDialog(component,
                        "This login is occupied already!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(component, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
