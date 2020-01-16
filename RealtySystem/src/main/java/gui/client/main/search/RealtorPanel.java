package gui.client.main.search;

import domain.realtor.Realtor;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/***
 * RealtorPanel
 */
public class RealtorPanel extends JPanel {

    private JLabel fName;
    private JLabel lName;
    private JLabel email;
    private JLabel phone;
    private JLabel cName;
    private JLabel cWebsite;

    private static RealtorPanel instance;

    public static RealtorPanel getInstance() {
        if (instance == null) {
            instance = new RealtorPanel();
        }
        return instance;
    }

    private RealtorPanel() {
        setLayout(new MigLayout("", "left", "center"));

        add(new JLabel("First name: "));
        fName = new JLabel();
        add(fName, "wrap");

        add(new JLabel("Last name: "));
        lName = new JLabel();
        add(lName, "wrap");

        add(new JLabel("E-mail: "));
        email = new JLabel();
        add(email, "wrap");

        add(new JLabel("Phone number: "));
        phone = new JLabel();
        add(phone, "wrap");

        add(new JLabel("Company name: "));
        cName = new JLabel();
        add(cName, "wrap");

        add(new JLabel("Company website: "));
        cWebsite = new JLabel();
        add(cWebsite, "wrap");
    }

    public void showInfo(Component component, Realtor realtor) {
        if (realtor == null) {
            return;
        }
        fName.setText(realtor.getFirstName());
        lName.setText(realtor.getLastName());
        email.setText(realtor.getEmail());
        phone.setText(realtor.getPhoneNumber());
        cName.setText(realtor.getCompanyName());
        cWebsite.setText(realtor.getCompanyWebsite());

        JOptionPane.showMessageDialog(component, this, "Realtor info",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
