package com.company;

import java.util.ArrayList;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class SynchronizedArrayList {

    private ArrayList list = new ArrayList();

    public synchronized void addObject(Object o) {
        list.add(o);
    }

    public synchronized ArrayList get() {
        return list;
    }
}
