package gui;

import domain.User;
import domain.account.Account;

import javax.swing.*;

/***
 * Represents a main form
 */
public abstract class MainForm extends JFrame {

    /** Link to controller */
    protected MainController controller;

    /***
     * Constructor
     * @param controller link to controller
     */
    public MainForm(MainController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("MainForm: controller is null!");
        }
        this.controller = controller;
    }

    public abstract void setUser(User user);

}
