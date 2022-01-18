package com.kikichante.server;

import java.util.Scanner;

public class CommandServer extends Thread {

    Bdd bdd;
    Scanner scanner = new Scanner(System.in);

    public CommandServer(Bdd bdd) {
        this.bdd = bdd;
    }

    @Override
    public void run() {
        String command = "";
        while (!command.equals("exit")) {
            command = scanner.nextLine();
            if (command.equals("exit"))
                break;
        }
        System.out.println("Fermeture du serveur - BDD");
        bdd.closeBdd();
        System.exit(0);
    }
}
