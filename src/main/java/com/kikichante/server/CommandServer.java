package com.kikichante.server;

import com.kikichante.utils.ColorOutput;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandServer extends Thread {

    Bdd bdd;
    ArrayList<ClientServer> activeClient;
    ArrayList<GameServer> activeGames;
    Scanner scanner = new Scanner(System.in);

    public CommandServer(Bdd bdd, ArrayList<ClientServer> activeClient, ArrayList<GameServer> activeGames) {
        this.bdd = bdd;
        this.activeClient = activeClient;
        this.activeGames = activeGames;
    }

    @Override
    public void run() {
        String command = "";
        while (!command.equals("exit")) {
            command = scanner.nextLine();
            if (command.equals("exit"))
                break;
            handle(command);
        }
        System.out.println("Fermeture du serveur - BDD");
        bdd.closeBdd();
        System.exit(0);
    }

    public void handle(String cmd) {
        if (cmd.equals("clients")){
            System.out.println("\nListe des clients : ");
            for (ClientServer c : activeClient) {
                System.out.print(ColorOutput.ANSI_WHITE + c.getUsernameFromBdd() + ColorOutput.ANSI_RESET + " - ");    //Print username
                System.out.print((c.isInListGame() ? ColorOutput.ANSI_GREEN_BACKGROUND : ColorOutput.ANSI_RED_BACKGROUND) + "isInListGame" + ColorOutput.ANSI_RESET + " ");
                System.out.print((c.isReady() ? ColorOutput.ANSI_GREEN_BACKGROUND : ColorOutput.ANSI_RED_BACKGROUND) + "isReady" + ColorOutput.ANSI_RESET + " ");
                System.out.print((c.isInGame() ? ColorOutput.ANSI_GREEN_BACKGROUND : ColorOutput.ANSI_RED_BACKGROUND) + "isInGame" + ColorOutput.ANSI_RESET + " ");
                System.out.print((c.isHaveReceivedMusic() ? ColorOutput.ANSI_GREEN_BACKGROUND : ColorOutput.ANSI_RED_BACKGROUND) + "haveReceivedMusic" + ColorOutput.ANSI_RESET + " ");
                System.out.println("");
                //System.out.println(c.getUsernameFromBdd() + " - ");
            }
            System.out.println("");
        } else if (cmd.equals("games")) {
            System.out.println("Listes des parties : ");
            for (GameServer game : activeGames) {
                System.out.print(game.getGameName() + " - ");
            }
        } else if (cmd.equals("game")) {
            System.out.print("Listes des parties : ");
            for (GameServer game : activeGames) {
                System.out.print(game.getGameName() + " - ");
            }
            System.out.println("");
            System.out.print("Nom de la partie : ");
            String info = scanner.nextLine();
            for (GameServer game : activeGames) {
                if (game.getGameName().equals(info)) {
                    for (ClientServer c : game.getCurrentPlayer()) {
                        System.out.print(c.getUsernameFromBdd() + "  ");
                    }
                    System.out.println("");
                    break;
                }
            }
        }
    }

}
