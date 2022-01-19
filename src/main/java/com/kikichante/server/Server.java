package com.kikichante.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {

        Bdd bdd = null;

        try {
            bdd = new Bdd();
        } catch (Exception e) {
            System.exit(0);
        }

        new CommandServer(bdd).start();

        ArrayList<ClientServer> activeClient = new ArrayList<>();
        //TODO -> Array list des parties

        try (ServerSocket serverSocket  = new ServerSocket(5000)) {
            while (true) {
                System.out.println("Wainting new client");
                Socket socket = serverSocket.accept();
                ClientServer clientServer = new ClientServer(socket, activeClient);
                new SenderThread(clientServer, bdd).start();
                activeClient.add(clientServer);
            }
        }
    }
}
