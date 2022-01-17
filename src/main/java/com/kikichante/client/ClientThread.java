package com.kikichante.client;

import com.kikichante.utils.MapperMessage;
import com.kikichante.utils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private BufferedReader in;
    private MapperMessage mapperMessage;

    public ClientThread(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        mapperMessage = new MapperMessage();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String response = in.readLine();
                choiceEvent(mapperMessage.jsonToObject(response));
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void choiceEvent(Message message) {
        System.out.println(message.msg);
    }
}
