package gui.client.main.bookmarks;

import domain.bookmark.Bookmark;
import domain.bookmark.BookmarkService;
import domain.realty_obj.RealtyObject;
import gui.client.main.ClientForm;
import gui.client.main.search.RealtorPanel;
import gui.client.main.search.SearchPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/***
 * Bookmarks
 */
public class BookmarkPanel extends JPanel {

    private ClientForm clientForm;

    private BookmarkService bookmarkService;

    private DefaultListModel objectsModel;

    public BookmarkPanel(ClientForm clientForm) {
        if (clientForm == null) {
            throw new IllegalArgumentException("BookmarkPanel: clientForm is null!");
        }
        this.clientForm = clientForm;
        bookmarkService = new BookmarkService();
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("", "center", "center"));

        objectsModel = new DefaultListModel();
        final JList objList = new JList(objectsModel);
        objList.setCellRenderer(new BookmarkRenderer());
        JScrollPane pane = new JScrollPane(objList);
        pane.setPreferredSize(new Dimension(500, 300));
        add(pane);

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new MigLayout("", "center", "center"));

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadObjects();
            }
        });
        bPanel.add(refresh, "wrap");

        JButton showInfo = new JButton("Show realtor info");
        showInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                RealtyObject realtyObject = ((BMWrapper) objList.getSelectedValue()).getBookmark()
                .getRealtyObject();
                if (realtyObject == null) {
                    JOptionPane.showMessageDialog(BookmarkPanel.this, "No information about " +
                            "realty object!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    RealtorPanel.getInstance().showInfo(BookmarkPanel.this,
                            realtyObject.getRealtor());
                }
            }
        });
        bPanel.add(showInfo, "wrap");

        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Bookmark bookmark = new Bookmark(((BMWrapper) objList.getSelectedValue()).getBookmark());
                String name = JOptionPane.showInputDialog(BookmarkPanel.this,
                        "Enter new bookmark name:");
                if (name == null) {
                    return;
                }
                if (name.equals("") || name.length() > 15) {
                    JOptionPane.showMessageDialog(BookmarkPanel.this, "Bookmark name " +
                                    "must be not longer than to 15 symbols!",
                            "Input error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                bookmark.setName(name);
                try {
                    bookmarkService.update(bookmark);
                    loadObjects();
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(BookmarkPanel.this, "Error: " + e1.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bPanel.add(update, "wrap");

        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    bookmarkService.delete(((BMWrapper) objList.getSelectedValue()).getBookmark());
                    loadObjects();
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(BookmarkPanel.this, "Error: " + e1.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bPanel.add(delete, "wrap");

        JButton deleteAll = new JButton("Delete all");
        deleteAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    bookmarkService.deleteAll(clientForm.getClient());
                    loadObjects();
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(BookmarkPanel.this, "Error: " + e1.getMessage(),
                            "DB error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        bPanel.add(deleteAll, "wrap");

        add(bPanel);
    }

    public void loadObjects() {
        objectsModel.removeAllElements();
        try {
            List<Bookmark> list =
                    bookmarkService.findByClient(clientForm.getClient().getId());
            for (Bookmark bm : list) {
                if (bm != null) {
                    objectsModel.addElement(new BMWrapper(bm));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
