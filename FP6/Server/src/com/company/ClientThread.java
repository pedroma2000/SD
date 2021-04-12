package com.company;

import java.io.IOException;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientThread extends Thread {

    private static DatagramSocket socket;
    private static final int PORT = 4449;

    private static final String SEND_DATA = "Send Me Your Data!";
    private static final String SEND_PING = "PING!";

    private String serverAddress;
    private int serverPort;
    private SystemDate systemDate;
    private SimpleDateFormat formatter;


    public ClientThread() {
        super();
        systemDate = new SystemDate();
        formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    }

    @Override
    public void run() {
        super.run();
        try {
            MulticastSocket multicastSocket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            multicastSocket.joinGroup(group);

            DatagramPacket packet;

            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);

            multicastSocket.setSoTimeout(10000);
            multicastSocket.receive(packet);

            serverAddress = packet.getAddress().toString().substring(1);
            serverPort = packet.getPort();

            String received = new String(packet.getData());

            if (received.contains(SEND_DATA)) {
                multicastSocket.leaveGroup(group);
                multicastSocket.close();
                socket = new DatagramSocket(PORT);
                sendDateTime();
            }

            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.setSoTimeout(60000);
            socket.receive(packet);

            while (packet != null) {
                received = new String(packet.getData());
                if (received.contains(SEND_PING)) {
                    sendPing();
                } else {
                    Thread.sleep(1000);
                    System.out.println("Definir relogio: ");
                    System.out.println("Set SystemDate Internal : " + received);
                    systemDate.setDate(formatter.parse(received));
                    System.out.println("Get SystemDate Internal : " + systemDate.getDate());
                    System.out.println("---------------------------------------------------");
                    socket.close();
                    System.exit(0);
                }

                buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
            }

        } catch (IOException e) {
            System.out.println("Socket Response Client Fechado!");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendDateTime() throws UnknownHostException {
        byte[] buf = new byte[256];
        buf = formatter.format(systemDate.getDate()).getBytes();
        InetAddress address = InetAddress.getByName(serverAddress);

        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, address, serverPort);
        try {
            Thread.sleep(5);
            socket.send(packet);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendPing() throws InterruptedException, UnknownHostException {
        byte[] buf = new byte[256];
        buf = SEND_PING.getBytes();
        InetAddress address = InetAddress.getByName(serverAddress);

        DatagramPacket packet;
        packet = new DatagramPacket(buf, buf.length, address, serverPort);
        try {
            Thread.sleep(5);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
