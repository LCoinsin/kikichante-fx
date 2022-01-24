package com.kikichante.server;

import com.kikichante.utils.ColorOutput;
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

        try (ServerSocket serverSocket  = new ServerSocket(5000)) {
            while (true) {
                ColorOutput.purpleMessage("En attente d'une nouvelle connexion ...");
                Socket socket = serverSocket.accept();
                ClientServer clientServer = new ClientServer(socket, activeClient, activeGames);
                activeClient.add(clientServer);
                new SenderThread(clientServer, bdd, activeGames, activeClient).start();
            }
        }
    }
}
