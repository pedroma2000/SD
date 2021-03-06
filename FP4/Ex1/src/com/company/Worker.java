package com.company;

import java.util.ArrayList;

/**
 *
 * @author Pedro Machado pedroma2000
 */
public class Worker extends Thread {
    ArrayList frases;
    int numero;

    public Worker(ArrayList f, int n) {
        super("Worker");
        this.frases = f;
        this.numero = n;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                frases.add("Frase " + i + " da thread " + numero);
                Thread.sleep(500 + i * 10);
            } catch (Exception ex) {
            }
        }
    }
}