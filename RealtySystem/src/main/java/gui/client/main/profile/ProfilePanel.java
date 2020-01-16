package gui.client.main.profile;

import domain.client.Client;
import domain.client.ClientService;
import gui.RegistrationForm;
import gui.client.main.ClientForm;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Represents a profile panel
 */
public class ProfilePanel extends JPanel {

    private ClientForm clientForm;

    private JTextField firstName;

    private JTextField lastName;

    private JTextField email;

    private JTextField phoneNumber;

    private ClientService clientService;

    public ProfilePanel(ClientForm clientForm) {
        if (clientForm == null) {
            throw new IllegalArgumentException("ProfilePanel: clientForm is null!");
        }
        this.clientForm = clientForm;
        clientService = new ClientService();
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

        setFieldsEditable(false);

        final JToggleButton changeButton = new JToggleButton("Change");
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fn = firstName.getText();
                String ln = lastName.getText();
                String em = email.getText();
                String ph = phoneNumber.getText();
                if (RegistrationForm.checkClient(ProfilePanel.this,
                        fn, ln, em, ph)) {
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
                    Client client = new Client(clientForm.getClient());
                    client.setFirstName(fn);
                    client.setLastName(ln);
                    client.setEmail(em);
                    client.setPhoneNumber(ph);
                    try {
                        clientService.update(client);
                        clientForm.setClient(client);
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
                clientForm.logOut();
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
                        clientService.delete(clientForm.getClient());
                    }
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(ProfilePanel.this, "Error: " + e1.getMessage(),
                                "DB error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    clientForm.logOut();
                }
            }
        });
        add(deleteAcc, "span");

    }

    public void restoreFields() {
        Client client = clientForm.getClient();
        firstName.setText(client.getFirstName());
        lastName.setText(client.getLastName());
        email.setText(client.getEmail());
        phoneNumber.setText(client.getPhoneNumber());
    }

    private void setFieldsEditable(boolean flag) {
        firstName.setEditable(flag);
        lastName.setEditable(flag);
        email.setEditable(flag);
        phoneNumber.setEditable(flag);
    }

}
