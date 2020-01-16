package gui.realtor.main.realty_obj;

import domain.realty_obj.RealtyObject;
import domain.realty_obj.RealtyObjectService;
import gui.ROWrapper;
import gui.RealtyObjectRenderer;
import gui.realtor.main.RealtorForm;
import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.*;
import java.awt.*;

/***
 * Maintain RO
 */
public class RealtyObjectPanel extends JPanel {

    private RealtorForm realtorForm;

    private RealtyObjectService realtyObjectService;

    private DefaultListModel objectsModel;

    private ROUpdatePanel roUpdatePanel;

    private ROCreatePanel roCreatePanel;

    public RealtyObjectPanel(RealtorForm realtorForm) {
        if (realtorForm == null) {
            throw new IllegalArgumentException("RealtyObjectPanel: realtorForm is null!");
        }
        this.realtorForm = realtorForm;
        realtyObjectService = new RealtyObjectService();
        roCreatePanel = new ROCreatePanel(realtorForm,  this);
        roUpdatePanel = new ROUpdatePanel(realtorForm, this);
        initComponents();
    }

    private void initComponents() {
        setLayout(new MigLayout("", "center", "center"));

        objectsModel = new DefaultListModel();
        final JList objList = new JList(objectsModel);
        objList.setCellRenderer(new RealtyObjectRenderer());
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

        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roCreatePanel.showDialog(realtorForm.getRealtor(), null);
            }
        });
        bPanel.add(create, "wrap");

        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (objList.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(null, "No object selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                RealtyObject realtyObject = new RealtyObject(((ROWrapper) objList.getSelectedValue()).getObject());
                roUpdatePanel.showDialog(realtorForm.getRealtor(),
                        realtyObject);
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
                delete(((ROWrapper) objList.getSelectedValue()).getObject());
            }
        });
        bPanel.add(delete, "wrap");

        JButton deleteAll = new JButton("Delete all");
        deleteAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    realtyObjectService.deleteAll(realtorForm.getRealtor());
                    loadObjects();
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(RealtyObjectPanel.this, "Error: " + e1.getMessage(),
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
            List<RealtyObject> list =
                    realtyObjectService.findByRealtor(realtorForm.getRealtor().getId());
            for (RealtyObject ro : list) {
                if (ro != null) {
                    objectsModel.addElement(new ROWrapper(ro));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create(RealtyObject object, File file) {
        try {
            realtyObjectService.persist(object, file);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //objectsModel.addElement(object);
        loadObjects();
    }

    public void update(RealtyObject object, String url, File file) {
        try {
            realtyObjectService.update(object, url, file);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //objectsModel.addElement(object);
        loadObjects();
    }

    public void delete(RealtyObject object) {
        try {
            realtyObjectService.delete(object);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "DB error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //objectsModel.addElement(object);
        loadObjects();
    }
}
