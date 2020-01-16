package gui;

import domain.realty_obj.RealtyObject;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/***
 * For processing realty object
 */
public class RealtyObjectRenderer extends JPanel implements ListCellRenderer {

    /** Highlight color */
    private final Color highlightColor = Color.GRAY;

    private JLabel name;
    private JLabel city;
    private JLabel address;
    private JLabel square;
    private JLabel price;
    private JLabel descr;
    private DrawPanel imPanel;

    protected DecimalFormat format;

    /***
     * Constructor
     */
    public RealtyObjectRenderer() {
        format = new DecimalFormat("#.###");
        initComponents();
    }

    private void initComponents() {
        setOpaque(true);
        setLayout(new MigLayout("", "center", "center"));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new MigLayout("", "left", "center"));

        infoPanel.add(new JLabel("Name: "));
        name = new JLabel();
        infoPanel.add(name, "wrap");

        infoPanel.add(new JLabel("City: "));
        city = new JLabel();
        infoPanel.add(city, "wrap");

        infoPanel.add(new JLabel("Address: "));
        address = new JLabel();
        infoPanel.add(address, "wrap");

        infoPanel.add(new JLabel("Square (meters): "));
        square = new JLabel();
        infoPanel.add(square, "wrap");

        infoPanel.add(new JLabel("Price (rubles): "));
        price = new JLabel();
        infoPanel.add(price, "wrap");

        infoPanel.add(new JLabel("Description: "));
        descr = new JLabel();
        infoPanel.add(descr, "wrap");

        imPanel = new DrawPanel();
        add(imPanel, new CC().width("150:150:150").height("150:150:150"));
        add(infoPanel);
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        ROWrapper wrapper = (ROWrapper) value;
        RealtyObject realtyObject = wrapper.getObject();

        name.setText(realtyObject.getName());
        city.setText(realtyObject.getCity());
        address.setText(realtyObject.getAddress());
        square.setText(format.format(realtyObject.getSquare()));
        price.setText(format.format(realtyObject.getPrice()));
        descr.setText(realtyObject.getDescription());

        imPanel.setImage(wrapper.getImage());


        if (isSelected) {
            setBackground(highlightColor);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}

