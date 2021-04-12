package pt.ipp.estg;

import java.util.ArrayList;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class SynchronizedUserDataList {

    private ArrayList<UserData> data = new ArrayList<>();

    public synchronized void addUser(UserData userData) {
        data.add(userData);
    }

    public synchronized ArrayList<UserData> getList() {
        return data;
    }

}
