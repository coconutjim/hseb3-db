package gui.realtor.main.profile;

import domain.realtor.Realtor;
import domain.realtor.RealtorService;
import gui.RegistrationForm;
import gui.realtor.main.RealtorForm;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Profile panel
 */
public class ProfilePanel extends JPanel {

    private RealtorForm realtorForm;

    private JTextField firstName;

    private JTextField lastName;

    private JTextField email;

    private JTextField phoneNumber;
    private JTextField compName;
    private JTextField compWebsite;

    private RealtorService realtorService;

    public ProfilePanel(RealtorForm realtorForm) {
        if (realtorForm == null) {
            throw new IllegalArgumentException("ProfilePanel: realtorForm is null!");
        }
        this.realtorForm = realtorForm;
        realtorService = new RealtorService();
        initComponents();
    }

    private void initComponents() {

        setLayout(new MigLayout("", "center", "center"));

        add(new JLabel("First name: "));
        firstName = new JTextField();
        add(firstName, new CC().wrap().width("150:150:150"));

        add(new JLabel("Last name: "));
        lastName = new JTextField();
        add(lastName, new CC().wrap().width("150:150:150"));

        add(new JLabel("E-mail: "));
        email = new JTextField();
        add(email, new CC().wrap().width("150:150:150"));

        add(new JLabel("Phone number: "));
        phoneNumber = new JTextField();
        add(phoneNumber, new CC().wrap().width("150:150:150"));

        add(new JLabel("Company name: "));
        compName = new JTextField();
        add(compName, new CC().wrap().width("150:150:150"));

        add(new JLabel("Company website: "));
        compWebsite = new JTextField();
        add(compWebsite, new CC().wrap().width("150:150:150"));

        setFieldsEditable(false);

        final JToggleButton changeButton = new JToggleButton("Change");
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fn = firstName.getText();
                String ln = lastName.getText();
                String em = email.getText();
                String ph = phoneNumber.getText();
                String cn = compName.getText();
                String cw = compWebsite.getText();
                if (RegistrationForm.checkRealtor(ProfilePanel.this,
                        fn, ln, em, ph, cn, cw)) {
                    if (fn != null && fn.equals("")) {
                        fn = null;
                    }
                    if (ln != null && ln.equals("")) {
                        ln = null;
                    }
                    if (em != null && em.equals("")) {
                        em = null;
                    }
                    if (ph != null && ph.equals("")) {
                        ph = null;
                    }
                    if (cn != null && cn.equals("")) {
                        cn = null;
                    }
                    if (cw != null && cw.equals("")) {
                        cw = null;
                    }
                    Realtor realtor = new Realtor(realtorForm.getRealtor());
                    realtor.setFirstName(fn);
                    realtor.setLastName(ln);
                    realtor.setEmail(em);
                    realtor.setPhoneNumber(ph);
                    realtor.setCompanyName(cn);
                    realtor.setCompanyWebsite(cw);
                    try {
                        realtorService.update(realtor);
                        realtorForm.setRealtor(realtor);
                    }
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(ProfilePanel.this, "Error: " + e1.getMessage(),
                                "DB error", JOptionPane.ERROR_MESSAGE);
                    }
                    restoreFields();
                    changeButton.setSelected(false);
                    setFieldsEditable(false);
                    saveButton.setEnabled(false);
                }
            }
        });
        saveButton.setEnabled(false);

        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (changeButton.isSelected()) {
                    setFieldsEditable(true);
                    saveButton.setEnabled(true);
                }
                else {
                    restoreFields();
                    setFieldsEditable(false);
                    saveButton.setEnabled(false);
                }
            }
        });

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new MigLayout("", "center", "center"));
        savePanel.add(changeButton);
        savePanel.add(saveButton);

        add(savePanel, "span");

        JButton logout = new JButton("Log out");
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                realtorForm.logOut();
            }
        });
        add(logout, "span");

        JButton deleteAcc = new JButton("Delete account");
        deleteAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(ProfilePanel.this, "Are you sure?", "Deleting account",
                        JOptionPane.OK_CANCEL_OPTION);
                if (res == JOptionPane.OK_OPTION) {
                    try {
                        realtorService.delete(realtorForm.getRealtor());
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(ProfilePanel.this, "Error: " + e1.getMessage(),
                                "DB error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    realtorForm.logOut();
                }
            }
        });
        add(deleteAcc, "span");

    }

    public void restoreFields() {
        Realtor realtor = realtorForm.getRealtor();
        firstName.setText(realtor.getFirstName());
        lastName.setText(realtor.getLastName());
        email.setText(realtor.getEmail());
        phoneNumber.setText(realtor.getPhoneNumber());
        compName.setText(realtor.getCompanyName());
        compWebsite.setText(realtor.getCompanyWebsite());
    }

    private void setFieldsEditable(boolean flag) {
        firstName.setEditable(flag);
        lastName.setEditable(flag);
        email.setEditable(flag);
        phoneNumber.setEditable(flag);
        compName.setEditable(flag);
        compWebsite.setEditable(flag);
    }

}
