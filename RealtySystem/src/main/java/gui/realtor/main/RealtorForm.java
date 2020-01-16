package gui.realtor.main;

import domain.User;
import domain.realtor.Realtor;
import gui.MainController;
import gui.MainForm;
import gui.realtor.main.profile.ProfilePanel;
import gui.realtor.main.realty_obj.RealtyObjectPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/***
 * Represents a realtor form
 */
public class RealtorForm extends MainForm {

    /** Realtor */
    private Realtor realtor;

    private ProfilePanel profilePanel;

    private RealtyObjectPanel realtyObjectPanel;

    public RealtorForm(MainController controller) {
        super(controller);
        profilePanel = new ProfilePanel(this);
        realtyObjectPanel = new RealtyObjectPanel(this);
        initComponents();
    }

    /***
     * Initializes GUI components
     */
    private void initComponents() {
        setTitle("Realtor");
        setLayout(new MigLayout("", "center", "center"));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        JPanel profileOutside = new JPanel();
        profileOutside.add(profilePanel);
        tabs.add(profileOutside, "Profile");
        tabs.add(realtyObjectPanel, "Realty objects");

        add(tabs);

        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.height / 2 - getSize().height / 2);

        profilePanel.setLocation(profileOutside.getWidth() / 2
                        - profilePanel.getWidth() / 2,
                profileOutside.getHeight() / 2
                        - profilePanel.getHeight() / 2);
    }

    @Override
    public void setUser(User user) {
        realtor = (Realtor) user;
        profilePanel.restoreFields();
        realtyObjectPanel.loadObjects();
    }

    public void logOut() {
        controller.logOut();
    }

    public Realtor getRealtor() {
        return realtor;
    }

    public void setRealtor(Realtor realtor) {
        this.realtor = realtor;
    }
}
