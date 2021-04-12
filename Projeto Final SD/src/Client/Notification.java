package Client;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class Notification {

    private String casos_concelho;
    private String casos_total;
    private String date;
    private int port;

    public Notification() {
        this.casos_concelho = "";
        this.casos_total = "";
        this.date = "";
        this.port = 0;
    }

    public String getCasos_concelho() {
        return casos_concelho;
    }

    public void setCasos_concelho(String casos_concelho) {
        this.casos_concelho = casos_concelho;
    }

    public String getCasos_total() {
        return casos_total;
    }

    public void setCasos_total(String casos_total) {
        this.casos_total = casos_total;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
