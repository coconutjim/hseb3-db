package gui.realtor;

import gui.MainController;
import gui.realtor.main.RealtorForm;

import javax.swing.*;

/***
 * Represents a realtor controller
 */
public class RealtorController extends MainController {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            // nothing to do here
        } catch (InstantiationException e) {
            // nothing to do here
        } catch (ClassNotFoundException e) {
            // nothing to do here
        } catch (UnsupportedLookAndFeelException e) {
            // nothing to do here
        }
        RealtorController controller = new RealtorController();
        controller.setLoginForm(new RealtorLoginForm(controller));
        controller.setRegistrationForm(new RealtorRegistrationForm(controller));
        controller.setMainForm(new RealtorForm(controller));
        controller.start();
    }
}
