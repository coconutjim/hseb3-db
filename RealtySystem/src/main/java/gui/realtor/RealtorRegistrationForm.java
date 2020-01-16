package gui.realtor;

import domain.account.Account;
import domain.realtor.Realtor;
import domain.realtor.RealtorService;
import gui.MainController;
import gui.RegistrationForm;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Realtor registration form
 */
public class RealtorRegistrationForm extends RegistrationForm {

    private RealtorService realtorService;

    private JTextField login;
    private JPasswordField password;
    private JPasswordField confPassword;
    private JTextField fName;
    private JTextField lName;
    private JTextField email;
    private JTextField phone;
    private JTextField compName;
    private JTextField compWebsite;

    public RealtorRegistrationForm(MainController controller) {
        super(controller);
        realtorService = new RealtorService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Registration");
        setLayout(new MigLayout("", "center", "center"));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        add(new JLabel("* - must be filled"), "span");

        add(new JLabel("*Login:"));
        login = new JTextField();
        add(login, new CC().wrap().width("150:150:150"));

        add(new JLabel("*Password:"));
        password = new JPasswordField();
        password.setEchoChar('*');
        add(password, new CC().wrap().width("150:150:150"));

        add(new JLabel("*Confirm password:"));
        confPassword = new JPasswordField();
        confPassword.setEchoChar('*');
        add(confPassword, new CC().wrap().width("150:150:150"));

        add(new JLabel("First name:"));
        fName = new JTextField();
        add(fName, new CC().wrap().width("150:150:150"));

        add(new JLabel("Last name:"));
        lName = new JTextField();
        add(lName, new CC().wrap().width("150:150:150"));

        add(new JLabel("E-mail:"));
        email = new JTextField();
        add(email, new CC().wrap().width("150:150:150"));

        add(new JLabel("Phone number:"));
        phone = new JTextField();
        add(phone, new CC().wrap().width("150:150:150"));

        add(new JLabel("Company name"));
        compName = new JTextField();
        add(compName, new CC().wrap().width("150:150:150"));

        add(new JLabel("Company website"));
        compWebsite = new JTextField();
        add(compWebsite, new CC().wrap().width("150:150:150"));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "center", "center"));

        JButton reg = new JButton("Register");
        reg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String l = login.getText();
                String pass = new String(password.getPassword());
                String conf = new String(confPassword.getPassword());
                String fn = fName.getText();
                String ln = lName.getText();
                String em = email.getText();
                String ph = phone.getText();
                String cmpName = compName.getText();
                String cmpWebsite = compWebsite.getText();
                if (checkRealtor(RealtorRegistrationForm.this,
                        l, pass, conf, fn, ln, em, ph, cmpName, cmpWebsite)) {
                    if (fn != null && fn.equals("")) {
                        fn = null;
                    }
                    if (ln != null && ln.equals("")) {
                        ln = null;
                    }
                    if (em != null && em.equals("")) {
                        em = null;
                    }
                    if (ph != null && ! ph.equals("")) {
                        ph = null;
                    }
                    if (cmpName != null && cmpName.equals("")) {
                        cmpName = null;
                    }
                    if (cmpWebsite != null && cmpWebsite.equals("")) {
                        cmpWebsite = null;
                    }
                    try {
                        realtorService.persist(new Realtor(new Account(l, pass),
                                fn, ln, em, ph, cmpName, cmpWebsite));
                        clearFields();
                        controller.outReg();
                    }
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(RealtorRegistrationForm.this, "Error: " + e1.getMessage(),
                                "DB error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(reg);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
                controller.outReg();
            }
        });
        panel.add(cancel);

        add(panel, "span");

        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.height / 2 - getSize().height / 2);
    }

    private void clearFields() {
        login.setText("");
        password.setText("");
        confPassword.setText("");
        fName.setText("");
        lName.setText("");
        email.setText("");
        phone.setText("");
        compName.setText("");
        compWebsite.setText("");
    }
}
