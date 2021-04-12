package Client;
import java.io.IOException;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientMain2 {
    public static void main(String[] args) throws IOException {
        Notification notification = new Notification();
      new ClientThread(notification).start();
      new ClientNotificationThread(notification).start();
    }


}
