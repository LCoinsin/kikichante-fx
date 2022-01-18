package com.kikichante.client;

import com.kikichante.utils.MapperMessage;
import com.kikichante.utils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    MapperMessage mapperMessage;
    String userId;
    String groupeId;
    Scanner scanner = new Scanner(System.in);
    static PrintWriter out;
    ClientThread clientThread;

    public Client() {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            out = new PrintWriter(socket.getOutputStream(), false);

            clientThread = new ClientThread(socket);
            clientThread.start();

            out.println("test");

            /*
            mapperMessage = new MapperMessage();


            userId = String.valueOf(UUID.randomUUID());
            out.println(mapperMessage.objectToJson(new Message(userId, 0, "Auth")));
            out.flush();

             */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***************************************/

    public void choiveEvent(String event) {
        switch (event) {
            case "message":
                System.out.print("Text : ");
                String toSend = scanner.nextLine();
                out.println(mapperMessage.objectToJson(new Message(userId, 1, userId, "String", toSend)));
                //out.println(mapperMessage.objectToJson(new Message(userId, new Cast(0), new MString("test", toSend))));
                break;
            case "broadcast":
                break;
            case "multicast":
                System.out.println("Message : ");
                String ms = scanner.nextLine();
                out.println(mapperMessage.objectToJson(new Message(userId, 2, groupeId, "multicastTest", ms)));
                break;
            case "updateGroupe":
                System.out.println("groupe : ");
                String group = scanner.nextLine();
                groupeId = group;
                out.println(mapperMessage.objectToJson(new Message(userId, 0, userId, "updateGroupId", group)));
                break;
            default:
                System.out.println("Type inconnu");
                break;
        }
    }

    /**
     * @return*************************************/

    public UUID userIdConnection() {
        int length = 25;
        boolean useLetters = true;
        boolean useNumbers = true;
        return UUID.randomUUID();
        //return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    /***************************************/

    public void connexion(String username, String password) {
        System.out.println("Connexion du client");
        out.println(mapperMessage.objectToJson(new Message(userId, 1, userId, "BddConnexion", username+":"+password)));
    }

    /***************************************/

    public ClientThread getClientThread() {
        return clientThread;
    }
}
