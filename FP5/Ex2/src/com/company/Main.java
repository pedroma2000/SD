package com.company;

import java.io.IOException;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            new MulticastServerThread().start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
