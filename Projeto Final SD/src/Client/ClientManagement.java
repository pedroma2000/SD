package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ClientManagement {

    public ClientManagement() {
    }

    public static String[] clientLogin(Socket socket, int id) throws IOException {

        socket = new Socket("localhost", 7);

        String[] client;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        String line = "";

        out.println("login");

        System.out.println(in.readLine());
        out.println(id);

        line = in.readLine();

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line.equals("ID Inválido")) {
            return null;

        } else {
            System.out.println(line);
            return client = line.split("-");
        }
    }

    public static int clientRegisto(Socket socket, String first_name, String last_name, String concelho) throws IOException {

        socket = new Socket("localhost", 7);
        concelho.toLowerCase();
        String name = nameGenerator(first_name.toLowerCase(), last_name.toLowerCase());
        int id;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        String line = "";

        out.println("registo");

        System.out.println(in.readLine());
        out.println(name);
        System.out.println(in.readLine());
        out.println(concelho);
        id = Integer.valueOf(in.readLine());

        return id;

    }

    public static boolean covidTest(Socket socket, int id) throws IOException {

        socket = new Socket("localhost", 7);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = "";

        out.println("teste");

        System.out.println(in.readLine());
        out.println(String.valueOf(id));
        line = in.readLine();

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line.equals("positive")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean addContact(Socket socket, int id, int contactID) throws IOException {

        socket = new Socket("localhost", 7);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = "";

        out.println("contacto");

        System.out.println(in.readLine());
        out.println(String.valueOf(id));
        System.out.println(in.readLine());
        out.println(String.valueOf(contactID));
        line = in.readLine();

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line.equals("success")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean notificacao(Socket socket, int id) throws IOException {

        socket = new Socket("localhost", 7);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = "";

        out.println("notificacao");

        System.out.println(in.readLine());
        out.println(String.valueOf(id));
        line = in.readLine();

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(line);

        if(line.equals("true")) {
            return true;
        } else {
            return false;
        }

    }

    public static void notificado(Socket socket, int id) throws IOException {

        socket = new Socket("localhost", 7);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = "";

        out.println("notificado");

        System.out.println(in.readLine());
        out.println(String.valueOf(id));

        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String nameGenerator(String firstName, String lastName) {

        String f1 = firstName.substring(0,1).toUpperCase();
        String fname = f1 + firstName.substring(1);

        String l1 = lastName.substring(0,1).toUpperCase();
        String lname = l1 + lastName.substring(1);

        String name = fname + " " + lname;

        return name;
    }
}
