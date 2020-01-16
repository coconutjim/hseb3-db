package gui.realtor.main.realty_obj;

import domain.realty_obj.RealtyObject;

import javax.swing.*;
import java.io.File;
import java.text.ParseException;

/***
 * Create
 */
public class ROCreatePanel extends ROChooser {

    public ROCreatePanel(JFrame parent, RealtyObjectPanel realtyObjectPanel) {
        super(parent, realtyObjectPanel);
    }

    @Override
    protected boolean ok() {
        if (! super.ok()) {
            return false;
        }
        Float s = null;
        Float p = null;
        try {
            s = format.parse(square.getText()).floatValue();
            p = format.parse(price.getText()).floatValue();
        }
        catch (ParseException e) {
            // nothing
        }
        realtyObject = new RealtyObject(name.getText(),
                (String) city.getSelectedItem(), address.getText(),
                s, p,
                description.getText(), null, realtor);
        realtyObjectPanel.create(realtyObject, file);
        dispose();
        return true;
    }
}
