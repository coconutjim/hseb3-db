package gui.realtor.main.realty_obj;

import javax.swing.*;
import java.text.ParseException;

/***
 * ROUpdatePanel
 */
public class ROUpdatePanel extends ROChooser {

    public ROUpdatePanel(JFrame parent, RealtyObjectPanel realtyObjectPanel) {
        super(parent, realtyObjectPanel);
    }

    @Override
    protected boolean ok() {
        if (! super.ok()) {
            return false;
        }
        realtyObject.setName(name.getText());
        realtyObject.setCity((String) city.getSelectedItem());
        realtyObject.setAddress(address.getText());
        Float s = null;
        Float p = null;
        try {
            s = format.parse(square.getText()).floatValue();
            p = format.parse(price.getText()).floatValue();
        }
        catch (ParseException e) {
            // nothing
        }
        realtyObject.setSquare(s);
        realtyObject.setPrice(p);
        realtyObject.setDescription(description.getText());
        realtyObjectPanel.update(realtyObject, url, file);
        dispose();
        return true;
    }
}
