package com.company;

import java.io.IOException;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class QuoteServer {

    public static void main(String[] args) {
	// write your code here
        try {
            new QuoteServerThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
