package Client;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientMain {
    public static void main(String[] args) throws IOException {
        Notification notification = new Notification();
      new ClientThread(notification).start();
      new ClientNotificationThread(notification).start();
    }


}
