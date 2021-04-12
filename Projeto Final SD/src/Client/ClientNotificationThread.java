package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientNotificationThread extends Thread {

    private int port;
    private Notification notification;

    public ClientNotificationThread(Notification notification) {
        this.notification = notification;
        this.port = 0;
    }

    private MulticastSocket multicastSocket;
    private MulticastSocket broadcastSocket;

    @Override
    public void run() {
        super.run();

        while (this.port == 0) {
            this.port = this.notification.getPort();
            System.out.println(this.port);
            System.out.println(this.notification.getPort());
        }

        try {
            multicastSocket = new MulticastSocket(this.port);
            broadcastSocket = new MulticastSocket(4453);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(group);
            broadcastSocket.joinGroup(group);

            while (true) {

                    byte[] multicast = new byte[256];
                    DatagramPacket multicastPacket = new DatagramPacket(multicast, multicast.length);
                    byte[] broadcast = new byte[256];
                    DatagramPacket broadcastPacket = new DatagramPacket(broadcast, broadcast.length);

                    multicastSocket.receive(multicastPacket);
                    String m_received = new String(trim(multicastPacket.getData()));
                    notification.setCasos_concelho(m_received);
                    System.out.println("CASOS DO CONCELHO:" + m_received);

                    broadcastSocket.receive(broadcastPacket);
                    String b_received = new String(trim(broadcastPacket.getData()));
                    notification.setCasos_total(b_received);
                    System.out.println("CASOS DO TOTAIS:" + b_received);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }
}
