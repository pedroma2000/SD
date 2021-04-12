package com.company;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class Main {

    public static void main(String[] args) {
        new WriterThread().start();
        new PrinterThread().start();
    }
}
