package gui.client.main;

import domain.User;
import domain.client.Client;
import gui.MainController;
import gui.MainForm;
import gui.client.main.bookmarks.BookmarkPanel;
import gui.client.main.profile.ProfilePanel;
import gui.client.main.search.SearchPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/***
 * Represents a client form
 */
public class ClientForm extends MainForm {

    /** Client */
    private Client client;

    private ProfilePanel profilePanel;
    private SearchPanel searchPanel;
    private BookmarkPanel bookmarkPanel;

    public ClientForm(MainController controller) {
        super(controller);
        profilePanel = new ProfilePanel(this);
        searchPanel = new SearchPanel(this);
        bookmarkPanel = new BookmarkPanel(this);
        initComponents();
    }

    /***
     * Initializes GUI components
     */
    private void initComponents() {
        setTitle("Client");
        setLayout(new MigLayout("", "center", "center"));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        JPanel profileOutside = new JPanel();
        profileOutside.add(profilePanel);
        tabs.add(profileOutside, "Profile");

        tabs.add(searchPanel, "Search");

        JPanel bookmarkOutside = new JPanel();
        bookmarkOutside.add(bookmarkPanel);
        tabs.add(bookmarkOutside, "Bookmarks");

        add(tabs);

        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.height / 2 - getSize().height / 2);

        profilePanel.setLocation(profileOutside.getWidth() / 2
                        - profilePanel.getWidth() / 2,
                profileOutside.getHeight() / 2
                        - profilePanel.getHeight() / 2);

        bookmarkPanel.setLocation(bookmarkOutside.getWidth() / 2
                        - bookmarkPanel.getWidth() / 2,
                bookmarkOutside.getHeight() / 2
                        - bookmarkPanel.getHeight() / 2);
    }

    @Override
    public void setUser(User user) {
        client = (Client) user;
        profilePanel.restoreFields();
        searchPanel.clear();
        bookmarkPanel.loadObjects();
    }

    public void updateBookmarks() {
        bookmarkPanel.loadObjects();
    }

    public void logOut() {
        controller.logOut();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
