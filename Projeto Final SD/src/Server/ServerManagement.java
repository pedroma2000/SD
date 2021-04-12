package Server;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class ServerManagement {

    public ServerManagement() {
    }

    public String[] login(int id) throws IOException {

        boolean found = false;
        String[] res = new String[3];

        File db = new File("Database.txt");

        Scanner reader = new Scanner(db);

        while (reader.hasNextLine() && !found) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(Integer.valueOf(data[0]) == id) {
                found = true;
                res[0] = data[0];
                res[1] = data[1];
                res[2] = data[2];
            }
        }
        reader.close();

        if(!found) {
            return null;
        }

        return res;
    }

    public int registo(String name, String concelho) throws IOException {

        File db = new File("Database.txt");
        File tmp_db = new File("TempDatabase.txt");
        tmp_db.createNewFile();

        BufferedReader reader = new BufferedReader(new FileReader(db));
        String newID = reader.readLine();

        BufferedWriter writer = new BufferedWriter(new FileWriter(db,true));
        writer.write(("\n" + newID + ";" + name + ";" + concelho.toLowerCase() + ";[]" + ";" + "false"));
        writer.close();
        reader.close();

        Scanner tempReader = new Scanner(new FileReader(db));
        BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tmp_db));
        int i = Integer.parseInt(newID) + 1;
        System.out.println(i);
        tempWriter.write(String.valueOf(i));
        System.out.println(String.valueOf(i));

        tempReader.nextLine();

        while (tempReader.hasNextLine() == true) {
            tempWriter.write("\n" + tempReader.nextLine());
        }

        tempWriter.close();
        tempReader.close();

        db.delete();
        tmp_db.renameTo(new File("Database.txt"));

        return Integer.valueOf(newID);
    }

    public boolean covidTest() {
        boolean res;

        Random rnd = new Random();

        res = rnd.nextBoolean();

        return res;
    }

    public boolean addContact(String id, String contactId) throws IOException {
        File db = new File("Database.txt");

        Scanner reader = new Scanner(new FileReader(db));

        String line = reader.nextLine();

        int idMax = Integer.parseInt(line);

        if(Integer.parseInt(contactId) >= idMax) {
            return false;
        } else {
            File tmp_db = new File("TempDatabase.txt");
            tmp_db.createNewFile();
            FileWriter writer = new FileWriter(tmp_db, true);

            writer.write(line);

            while (reader.hasNextLine()) {
                String data1 = reader.nextLine();
                String[] data = data1.split(";");

                if(data[0].equals(id)) {

                    if(data[3].equals("[]")) {
                        data[3] = contactId;
                        writer.write(("\n" + data[0] + ";" + data[1] + ";" + data[2] + ";" + data[3] + ";" + data[4]));
                    } else {

                        String[] contact = data[3].split("-");

                        String[] tempArr = new String[contact.length + 1];
                        for (int i = 0; i < tempArr.length - 1; i++) {
                            tempArr[i] = contact[i];
                        }
                        tempArr[tempArr.length - 1] = contactId;
                        data1 = String.join("-", tempArr);
                        writer.write(("\n" + data[0] + ";" + data[1] + ";" + data[2] + ";" + data1 + ";" + data[4]));
                    }
                } else {
                    data1 = String.join(";", data);
                    writer.write("\n" + data1);
                }
            }

            reader.close();
            writer.close();

            db.delete();
            tmp_db.renameTo(new File("Database.txt"));

            return true;
        }

    }

    public String[] getContacts(String id) throws IOException {
        File db = new File("Database.txt");

        Scanner reader = new Scanner(new FileReader(db));
        reader.nextLine();

        while (reader.hasNextLine()) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(data[0].equals(id)) {
                reader.close();
                return data[3].split("-");
            }
        }
        return null;
    }

    public void addNotification(String[] contacts) throws IOException {

        for(int i = 0; i < contacts.length; i++) {
            setTrueNotification(contacts[i]);
        }
    }

    public int getRegionPort(String concelho) {
        int port;

        switch (concelho) {
            case "amarante":
                port = 4441;
                break;
            case "baião":
                port = 4442;
                break;
            case "castelo de paiva":
                port = 4443;
                break;
            case "celorico de basto":
                port = 4444;
                break;
            case "cinfães":
                port = 4445;
                break;
            case "felgueiras":
                port = 4446;
                break;
            case "lousada":
                port = 4447;
                break;
            case "marco de canaveses":
                port = 4448;
                break;
            case "paços de ferreira":
                port = 4449;
                break;
            case "paredes":
                port = 4450;
                break;
            case "penafiel":
                port = 4451;
                break;
            case "resende":
                port = 4452;
                break;
            default:
                port = -1;
                break;
        }

        return port;
    }

    public void setTrueNotification(String id) throws IOException {
        File db = new File("Database.txt");
        File tmp_db = new File("TempDatabase.txt");
        tmp_db.createNewFile();

        Scanner reader = new Scanner(new FileReader(db));
        FileWriter writer = new FileWriter(tmp_db, true);

        writer.write(reader.nextLine());

        while (reader.hasNextLine()) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(data[0].equals(id)) {
                    writer.write(("\n" + data[0] + ";" + data[1] + ";" + data[2] + ";" + data[3] + ";" + "true"));

            } else {
                data1 = String.join(";", data);
                writer.write("\n" + data1);
            }
        }
        reader.close();
        writer.close();

        db.delete();
        tmp_db.renameTo(new File("Database.txt"));

    }

    public void setFalseNotification(String id) throws IOException {
        File db = new File("Database.txt");
        File tmp_db = new File("TempDatabase.txt");
        tmp_db.createNewFile();

        Scanner reader = new Scanner(new FileReader(db));
        FileWriter writer = new FileWriter(tmp_db, true);

        writer.write(reader.nextLine());

        while (reader.hasNextLine()) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(data[0].equals(id)) {
                System.out.println("AKI");
                writer.write(("\n" + data[0] + ";" + data[1] + ";" + data[2] + ";" + data[3] + ";" + "false"));

            } else {
                data1 = String.join(";", data);
                writer.write("\n" + data1);
            }
        }
        reader.close();
        writer.close();

        db.delete();
        tmp_db.renameTo(new File("Database.txt"));

    }

    public boolean verifyNotification(String id) throws FileNotFoundException {
        boolean found = false;
        boolean notification = false;

        File db = new File("Database.txt");

        Scanner reader = new Scanner(db);

        while (reader.hasNextLine() && !found) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(data[0].equals(id)) {
                found = true;

                if(data[4].equals("true")) {
                    notification = true;
                } else {
                    notification = false;
                }
            }
        }
        reader.close();

        return notification;
    }

    public String getConcelho(String id) throws FileNotFoundException {

        boolean found = false;
        String concelho = "notFound";

        File db = new File("Database.txt");

        Scanner reader = new Scanner(db);

        while (reader.hasNextLine() && !found) {
            String data1 = reader.nextLine();
            String[] data = data1.split(";");

            if(data[0] == id) {
                found = true;
                concelho = data[2];
            }
        }

        reader.close();

        return concelho;

    }

}
