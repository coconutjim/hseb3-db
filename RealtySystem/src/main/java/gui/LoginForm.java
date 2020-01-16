package gui;

import domain.User;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Represents a login form
 */
public abstract class LoginForm extends JFrame {

    /** Link to main controller */
    protected MainController controller;

    /** Login field */
    private JTextField login;

    /** Password field */
    private JPasswordField password;

    /***
     * Csontructor
     * @param controller link to main controller
     */
    public LoginForm(MainController controller)  {
        if (controller == null) {
            throw new IllegalArgumentException("LoginForm: controller is null!");
        }
        this.controller = controller;
        initComponents();
    }

    /***
     * Initializes GUI components
     */
    private void initComponents() {
        setTitle("Login");
        setLayout(new MigLayout("", "center", "center"));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        add(new JLabel("Login:"));
        login = new JTextField();
        add(login, new CC().wrap().width("150:150:150"));
        add(new JLabel("Password:"));
        password = new JPasswordField();
        password.setEchoChar('*');
        add(password, new CC().wrap().width("150:150:150"));

        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user;
                try {
                    user = login(login.getText(), new String(password.getPassword()));
                }
                catch (NullPointerException e1) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login not found!");
                    return;
                }
                catch (IllegalArgumentException e2) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Wrong password!");
                    return;
                }
                catch (Exception e3) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Error: " + e3.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (user != null) {
                    clearFields();
                    controller.loggedIn(user);
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Profile not found!");
                }
            }
        });
        add(loginButton, "span");

        JButton regButton = new JButton("Registration");
        regButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
                controller.registration();
            }
        });
        add(regButton, "span");

        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.height / 2 - getSize().height / 2);
    }

    /***
     * Clears login and password fields
     */
    private void clearFields() {
        login.setText("");
        password.setText("");
    }

    /***
     * Tries lo login
     * @param login login
     * @param password password
     * @return account if login was successful, null otherwise
     */
    public abstract User login(String login, String password) throws Exception;
}
