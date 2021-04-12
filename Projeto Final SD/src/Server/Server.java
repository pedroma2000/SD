package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class Server {

    private static Socket socket;
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private static PrintWriter out;

    private static ServerManagement sm = new ServerManagement();

    public static void main(String[] args) throws IOException {

        try {

            serverSocket = new ServerSocket(7);
            new ServerNotificationThread().start();
            System.out.println("Server Started");

            System.out.println("Waiting for client");

            String line = "";

            while (true) {

                socket = serverSocket.accept();
                System.out.println("Client accepted");

                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                line = in.readLine();

                if(line == null){
                } else {
                    switch (line) {
                        case "login":
                            //CODIGO LOGIN
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("id?");
                            String id = in.readLine();

                            if(sm.login(Integer.parseInt(id)) == null) {
                                out.println("ID Inválido");
                                System.out.println("ID INV");
                            } else {
                                String[] res = sm.login(Integer.parseInt(id));
                                System.out.println(res[0] + "-" + res[1] + "-" + sm.getRegionPort(res[2]));
                                out.println(res[0] + "-" + res[1] + "-" + sm.getRegionPort(res[2]));
                            }
                            break;
                        case "registo":
                            //CODIGO REGISTO
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("nome?");
                            String nome = in.readLine();
                            out.println("concelho?");
                            String concelho = in.readLine();

                            int new_id = sm.registo(nome, concelho);
                            out.println(String.valueOf(new_id));
                            break;
                        case "teste":
                            //CODIGO TESTE
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("id?");
                            String test_id = in.readLine();

                            boolean positive = sm.covidTest();

                            if(positive) {
                                out.println("positive");
                                sm.addNotification(sm.getContacts(test_id));
                            } else {
                                out.println("negative");
                            }
                            break;
                        case "contacto":
                            //CODIGO CONTACTO
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("id?");
                            String client_id = in.readLine();
                            out.println("contactID?");
                            String contactID = in.readLine();

                            if(sm.addContact(client_id, contactID)) {
                                out.println("success");
                            } else {
                                out.println("failed");
                            }
                            break;
                        case "notificacao":
                            //CODIGO NOTIFICACAO
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("id?");
                            if(sm.verifyNotification(in.readLine())) {
                                out.println("true");
                            } else {
                                out.println("false");
                            }
                            break;
                        case "notificado":
                            //CODIGO NOTIFICADO
                            out = new PrintWriter(socket.getOutputStream(), true);
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            out.println("id?");
                            sm.setFalseNotification(in.readLine());
                            break;
                        default:
                            break;
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        serverSocket.close();
    }
}
