package gui.client.main.search;

import domain.SearchService;
import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkService;
import domain.realty_obj.RealtyObject;
import gui.ROWrapper;
import gui.RealtyObjectRenderer;
import gui.client.main.ClientForm;
import net.miginfocom.swing.MigLayout;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Search
 */
public class SearchPanel extends JPanel {

    private ClientForm clientForm;

    private JComboBox<String> city;
    private JComboBox<String> priceFrom;
    private JComboBox<String> priceTo;
    private JComboBox<String> squareFrom;
    private JComboBox<String> squareTo;

    private DefaultListModel objectsModel;

    private SearchService searchService;
    private BookmarkService bookmarkService;


    public SearchPanel(ClientForm clientForm) {
        if (clientForm == null) {
            throw new IllegalArgumentException("SearchPanel: clientForm is null!");
        }
        this.clientForm = clientForm;
        searchService = new SearchService();
        bookmarkService = new BookmarkService();
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("", "center", "center"));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new MigLayout("", "center", "center"));
        searchPanel.add(new JLabel("City"));
        searchPanel.add(new JLabel("Square (meters)"));
        searchPanel.add(new JLabel("Price (rubles)"), "wrap");

        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new MigLayout("", "center", "center"));
        String[] cities = new String[] {"---", "Moscow", "Perm", "Novosibirsk"};
        city = new JComboBox<String>(cities);
        cityPanel.add(new JLabel("City:"), "wrap");
        cityPanel.add(city);

        searchPanel.add(cityPanel);

        JPanel squarePanel = new JPanel();
        squarePanel.setLayout(new MigLayout("", "center", "center"));
        squarePanel.add(new JLabel("From:"));
        squarePanel.add(new JLabel("To:"), "wrap");
        String[] squareFroms = new String[] {"---", "100", "200"
                , "300", "400", "500", "600", "700", "800", "900", "1000"};
        squareFrom = new JComboBox<String>(squareFroms);
        squarePanel.add(squareFrom);
        String[] squareTos = new String[] {"---", "100", "200"
                , "300", "400", "500", "600", "700", "800", "900", "1000"};
        squareTo = new JComboBox<String>(squareTos);
        squarePanel.add(squareTo);

        searchPanel.add(squarePanel);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new MigLayout("", "center", "center"));
        pricePanel.add(new JLabel("From:"));
        pricePanel.add(new JLabel("To:"), "wrap");
        String[] priceFroms = new String[] {"---", "100000", "1000000"
                , "50000000", "100000000", "500000000", "700000000", "1000000000"};
        priceFrom = new JComboBox<String>(priceFroms);
        pricePanel.add(priceFrom);
        String[] priceTos = new String[] {"---", "100000", "1000000"
                , "50000000", "100000000", "500000000", "700000000", "1000000000"};
        priceTo = new JComboBox<String>(priceTos);
        pricePanel.add(priceTo);

        searchPanel.add(pricePanel);

        add(searchPanel);

        final JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        add(search, "wrap");

        objectsModel = new DefaultListModel();
        final JList objList = new JList<RealtyObject>(objectsModel);
        objList.setCellRenderer(new RealtyObjectRenderer());
        JScrollPane pane = new JScrollPane(objList);
        pane.setPreferredSize(new Dimension(500, 300));
        add(pane);

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new MigLayout("", "center", "center"));

        JButton showInfo = new JButton("Show realtor info");
        showInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                RealtorPanel.getInstance().showInfo(SearchPanel.this,
                        ((ROWrapper) objList.getSelectedValue()).getObject().getRealtor());
            }
        });
        bPanel.add(showInfo, "wrap");

        JButton addBookmark = new JButton("Add to bookmarks");
        addBookmark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String name = JOptionPane.showInputDialog(SearchPanel.this,
                        "Enter bookmark name:");
                if (name == null) {
                    return;
                }
                if (name.equals("") || name.length() > 15) {
                    JOptionPane.showMessageDialog(SearchPanel.this, "Bookmark name " +
                                    "must be not longer than to 15 symbols!",
                            "Input error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Bookmark bookmark = new Bookmark(name, clientForm.getClient(),
                        ((ROWrapper) objList.getSelectedValue()).getObject());
                try {
                    bookmarkService.persist(bookmark);
                    clientForm.updateBookmarks();
                    JOptionPane.showMessageDialog(SearchPanel.this,
                            "Bookmark was created!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(SearchPanel.this, "Error: " + e1.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bPanel.add(addBookmark);

        add(bPanel);
    }

    public void search() {
        if (squareFrom.getSelectedIndex() != 0 &&
                squareTo.getSelectedIndex() != 0 &&
                Float.parseFloat((String) squareFrom.getSelectedItem()) >
                        Float.parseFloat((String) squareTo.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Minimum square can not be greater than maximum square!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (priceFrom.getSelectedIndex() != 0 &&
                priceTo.getSelectedIndex() != 0 &&
                Float.parseFloat((String) priceFrom.getSelectedItem()) >
                        Float.parseFloat((String) priceTo.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Minimum price can not be greater than maximum price!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String c = city.getSelectedIndex() == 0? null : (String) city.getSelectedItem();
        Float sf = squareFrom.getSelectedIndex() == 0? null :
                Float.parseFloat((String) squareFrom.getSelectedItem());
        Float st = squareTo.getSelectedIndex() == 0? null :
                Float.parseFloat((String) squareTo.getSelectedItem());
        Float pf = priceFrom.getSelectedIndex() == 0? null :
                Float.parseFloat((String) priceFrom.getSelectedItem());
        Float pt = priceTo.getSelectedIndex() == 0? null :
                Float.parseFloat((String) priceTo.getSelectedItem());
        List<RealtyObject> list;
        try {

            list = searchService.search(c, sf, st, pf, pt);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (list != null) {
            objectsModel.removeAllElements();
            for (RealtyObject object : list) {
                if (object != null) {
                    objectsModel.addElement(new ROWrapper(object));
                }
            }
        }
    }

    public void clear() {
        city.setSelectedIndex(0);
        priceFrom.setSelectedIndex(0);
        priceTo.setSelectedIndex(0);
        squareFrom.setSelectedIndex(0);
        squareTo.setSelectedIndex(0);
        objectsModel.removeAllElements();
    }
}
