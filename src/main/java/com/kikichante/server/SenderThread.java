package com.kikichante.server;

import java.io.IOException;

public class SenderThread extends Thread {

    private ClientServer clientServer;
    private Bdd bdd;

    public SenderThread (ClientServer clientServer, Bdd bdd) {
        this.clientServer = clientServer;
        this.bdd = bdd;
    }

    @Override
    public void run() {
        try {
            while (true) {
                var line = this.clientServer.readLine();
                handleLine(line);
            }
        } catch (IOException e) {
            //disconnect
            e.printStackTrace();
        }
    }

    public void handleLine(String message) {
        if (message.startsWith("LOGIN")) {
            String[] messageConnexion = message.split(":");
            String username = messageConnexion[1];
            String password = messageConnexion[2];

            if (bdd.queryConnexion(username, password)) {
                System.out.println("Connexion OK");
                this.clientServer.println("LOGIN:OK");
            } else {
                System.out.println("Connexion KO");
                this.clientServer.println("LOGIN:KO");
            }

        } else {
            System.out.println("message = " + message);
        }
    }
}
