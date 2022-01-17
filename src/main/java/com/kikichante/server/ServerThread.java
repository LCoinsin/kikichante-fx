package com.kikichante.server;

import com.kikichante.utils.MapperMessage;
import com.kikichante.utils.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private MapperMessage mapperMessage;
    private String groupeId = "0";
    private String userId;
    private String serverId="00000000000000000000";

    /***************************************/

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
        //mapperMessage = new com.kikichante.utils.MapperMessage();
    }

    /***************************************/

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            while(true) {
                String outString = (String) in.readLine();
                System.out.println("outString = " + outString);
                /*
                if(outString.equals("exit") )
                    break;
                 */
                //choiceEvent(mapperMessage.jsonToObject(outString));
                //printAll("Message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************************************/

    private void choiceEvent(Message message) {

        switch (message.cast) {
            case 0:
                if (message.type.equals("Auth"))
                    userId = message.sender;
                if (message.type.equals("updateGroupId"))
                    groupeId = message.msg;
                break;
            case 1:
                System.out.println("Cast : 1");
                if (message.type.equals("String"))
                    output.println(mapperMessage.objectToJson(new Message(serverId, -1, "", "String", message.msg)));
                //Selection du rcvd + envoie
                break;
            case 2 :    //MultiCast
                System.out.println("Cast : 2");
                if (message.type.equals("multicastTest"))
                    multicastMessage(message.msg, "reponse"+message.type);
                break;
            case 3:     //BroadCast
                System.out.println("Cast : 3");
                break;
            default:
                System.out.println("Error cast");
                break;
        }

    }

    /***************************************/

    private void multicastMessage(String msg, String type) {
        for (ServerThread sT : threadList) {
            if (sT.groupeId.equals(groupeId)) {
                sT.output.println(mapperMessage.objectToJson(new Message(serverId, -1, "", type, msg)));
                //sT.output.println(new Message(serverId, -1, "", ""));
            }
        }
    }

    /***************************************/

    private void broadcastMessage() {

    }

    /***************************************/

    private void printAll(String outString) {
        for (ServerThread sT : threadList) {
            break;
            //System.out.println(sT.groupeId);
            //sT.output.println(outString);
        }
    }
}
