package gui.realtor.main.realty_obj;

import domain.realtor.Realtor;
import domain.realty_obj.RealtyObject;
import gui.DrawPanel;
import gui.ImageResource;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Pattern;

/***
 * Singleton
 */
public abstract class ROChooser extends JDialog {

    protected JTextField name;
    protected JComboBox<String> city;
    protected JTextField address;
    protected JTextField square;
    protected JTextField price;
    protected JTextField description;
    private DrawPanel drawPanel;

    protected File file;
    protected String url;

    protected RealtyObject realtyObject;
    protected Realtor realtor;

    protected RealtyObjectPanel realtyObjectPanel;

    private JFileChooser fc;

    private Pattern patternName;
    protected DecimalFormat format;

    public ROChooser(JFrame parent, final RealtyObjectPanel realtyObjectPanel) {
        super(parent);
        if (realtyObjectPanel == null) {
            throw new IllegalArgumentException("ROChooser: null arguments!");
        }
        this.realtyObjectPanel = realtyObjectPanel;

        format = new DecimalFormat("#.###");


        setTitle("Realtor");
        setLayout(new MigLayout("", "center", "center"));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        add(new JLabel("* - must be filled"), "span");

        add(new JLabel("*Name: "));
        name = new JTextField();
        add(name, new CC().wrap().width("150:150:150"));

        add(new JLabel("*City: "));
        String[] cities = new String[] { "Moscow", "Perm", "Novosibirsk" };
        city = new JComboBox<String>(cities);
        city.setSelectedIndex(0);
        add(city, new CC().wrap().width("150:150:150"));

        add(new JLabel("*Address: "));
        address = new JTextField();
        add(address, new CC().wrap().width("150:150:150"));

        add(new JLabel("*Square (meters): "));
        square = new JTextField();
        add(square, new CC().wrap().width("150:150:150"));

        add(new JLabel("*Price (rubles): "));
        price = new JTextField();
        add(price, new CC().wrap().width("150:150:150"));

        add(new JLabel("Description: "));
        description = new JTextField();
        add(description, new CC().wrap().width("150:150:150"));

        JPanel imPanel = new JPanel();
        imPanel.setLayout(new MigLayout("", "center", "center"));
        JPanel butPanel = new JPanel();
        butPanel.setLayout(new MigLayout("", "center", "center"));

        fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter(
                "Image files (.png, .jpg, .jpeg)", "png", "jpg", "jpeg");
        fc.setFileFilter(filter);

        JButton choose = new JButton("Choose image");
        choose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = fc.showOpenDialog(realtyObjectPanel);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        BufferedImage image = ImageIO.read(file);
                        drawPanel.setImage(image);
                        ROChooser.this.file = file;
                        if (realtyObject != null && realtyObject.getImageUrl() != null) {
                            url = realtyObject.getImageUrl();
                        }
                    }
                    catch (IOException e1) {
                        JOptionPane.showMessageDialog(ROChooser.this,
                                "Error when opening the image",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        butPanel.add(choose, "wrap");

        JButton delete = new JButton("Delete image");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                file = null;
                drawPanel.setImage(null);
                if (realtyObject.getImageUrl() != null) {
                    url = realtyObject.getImageUrl();
                }
            }
        });
        butPanel.add(delete);

        drawPanel = new DrawPanel();
        imPanel.add(drawPanel, new CC().width("300:300:300")
                .height("300:300:300"));
        imPanel.add(butPanel);

        add(new JLabel("Photo:"), "span");
        add(imPanel, "span");

        JPanel bp = new JPanel();
        bp.setLayout(new MigLayout("", "center", "center"));
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        bp.add(ok);

        JButton can = new JButton("Cancel");
        can.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bp.add(can);

        add(bp, "span");

        patternName = Pattern.compile("^[a-zA-Z]{1,15}$");

        pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getSize().width / 2, dimension.height / 2 - getSize().height / 2);

    }

    public void showDialog(Realtor realtor, RealtyObject object) {
        clearFields();
        if (object != null) {
            fillFields(object);
        }
        this.realtyObject = object;
        this.realtor = realtor;
        setVisible(true);
    }

    private void clearFields() {
        name.setText("");
        city.setSelectedIndex(0);
        address.setText("");
        square.setText("");
        price.setText("");
        description.setText("");
        drawPanel.setImage(null);
        url = null;
        file = null;
    }

    private void fillFields(RealtyObject object) {
        if (object == null) {
            return;
        }
        name.setText(object.getName());
        city.setSelectedItem(object.getCity());
        address.setText(object.getAddress());

        square.setText(format.format(object.getSquare()));
        price.setText(format.format(object.getPrice()));
        description.setText(object.getDescription());
        drawPanel.setImage(ImageResource.getImage(object.getImageUrl()));
    }

    protected boolean ok() {
        if (! patternName.matcher(name.getText()).matches()) {
            JOptionPane.showMessageDialog(this,
                    "Realty object name can contain only " +
                            "English and must be not more than to 15 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (address.getText().length() > 100) {
            JOptionPane.showMessageDialog(this,
                    "Address can not be longer than 100 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Float p = format.parse(square.getText()).floatValue();// Float.parseFloat(square.getText());
            if (p > 1000 || p < 10) {
                throw new IllegalArgumentException();
            }
        }
        catch (ParseException eee) {
            JOptionPane.showMessageDialog(this,
                    "Price must be a number from 10 to 1 000 !",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch (IllegalArgumentException ee) {
            JOptionPane.showMessageDialog(this,
                    "Price must be a number from 10 to 1 000 !",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Float p = format.parse(price.getText()).floatValue(); //Float.parseFloat(price.getText());
            if (p > 1000000000 || p < 100000) {
                throw new IllegalArgumentException();
            }
        }
        catch (ParseException eee) {
            JOptionPane.showMessageDialog(this,
                    "Price must be a number from 100 000 to 1 000 000 000!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch (IllegalArgumentException ee) {
            JOptionPane.showMessageDialog(this,
                    "Price must be a number from 100 000 to 1 000 000 000!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (description.getText().length() > 150) {
            JOptionPane.showMessageDialog(this,
                    "Description can not be longer than 150 symbols!",
                    "Input error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
