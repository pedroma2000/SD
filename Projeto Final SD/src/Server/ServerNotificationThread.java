package Server;

import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ServerNotificationThread extends Thread {

    private static DatagramSocket multicastSocket;
    private static DatagramSocket broadcastSocket;
    private static InetAddress group;

    @Override
    public void run() {
        super.run();

        try {
            multicastSocket = new DatagramSocket(4440);
            group = InetAddress.getByName("230.0.0.1");

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                //Thread.sleep(8640000);
                Thread.sleep(10000);

                int[] casos = gerarCasos();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                String dateString = format.format(new Date());
                System.out.println(dateString);
                int i = 0;
                int port = 4441;

                while (i < casos.length && port != 4452) {

                    byte[] buf;
                    String conc = (dateString + ";" + String.valueOf(casos[i]));
                    buf = conc.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
                    multicastSocket.send(packet);
                    port++;
                    i++;

                }

                int total = 0;

                for(int j = 0; j < casos.length; j++) {

                    total += casos[j];
                }

                Thread.sleep(2000);

                String reg = (dateString + ";" + String.valueOf(total));

                broadcast(reg, group);

                Thread.sleep(2000);

                broadcast(dateString, group);


                /**
                System.out.println("CASOS NOVOS:");

                for (int i = 0; i < casos.length; i++) {
                    System.out.println(casos[i]);
                }

                System.out.println("-------------------------");
                 */

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int[] gerarCasos() {
        int[] casos = new int[12];

        for(int i = 0; i < casos.length; i++) {
            casos[i] = new Random().nextInt(490);
            casos[i] += 10;
        }

        return casos;
    }

    private static void broadcast(String message, InetAddress address) throws IOException {

        broadcastSocket = new DatagramSocket();
        broadcastSocket.setBroadcast(true);

        byte[] buf = message.getBytes();

        DatagramPacket broadcastPacket = new DatagramPacket(buf, buf.length, address, 4453);
        broadcastSocket.send(broadcastPacket);
        broadcastSocket.close();
    }

}
