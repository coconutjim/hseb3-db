package gui.client;

import gui.MainController;
import gui.client.main.ClientForm;

import javax.swing.*;

/***
 * Represents a client controller
 */
public class ClientController extends MainController {

    public static void main(String[] args) {
        /*ClientService service = new ClientService();
        Account account1 = new Account("login", "password");
        Client client1 = new Client(account1, "Lev", "osidpv",
                "e-maidfl", 6343567);
        service.persist(account1, client1);*/
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (IllegalAccessException e) {
            // nothing to do here
        }
        catch (InstantiationException e) {
            // nothing to do here
        }
        catch (ClassNotFoundException e) {
            // nothing to do here
        }
        catch (UnsupportedLookAndFeelException e) {
            // nothing to do here
        }
        ClientController controller = new ClientController();
        controller.setLoginForm(new ClientLoginForm(controller));
        controller.setRegistrationForm(new ClientRegistrationForm(controller));
        controller.setMainForm(new ClientForm(controller));
        controller.start();
    }
}
