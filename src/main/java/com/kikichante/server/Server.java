package com.kikichante.server;

import org.apache.commons.text.similarity.LevenshteinDistance;

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

        ArrayList<ClientServer> activeClient = new ArrayList<>();
        ArrayList<GameServer> activeGames = new ArrayList<>();

        new CommandServer(bdd, activeClient, activeGames).start();

        //TODO -> Supprimer apres
        //activeGames.add(new GameServer("e", bdd));
        //activeGames.add(new GameServer("g", bdd));
        //activeGames.add(new GameServer("b", bdd));
        //activeGames.add(new GameServer("a", bdd));
        //activeGames.add(new GameServer("s", bdd));

        try (ServerSocket serverSocket  = new ServerSocket(5000)) {
            while (true) {
                System.out.println("Wainting new client");
                Socket socket = serverSocket.accept();
                ClientServer clientServer = new ClientServer(socket, activeClient, activeGames);
                activeClient.add(clientServer);
                new SenderThread(clientServer, bdd, activeGames, activeClient).start();
            }
        }
    }
}
