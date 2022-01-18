package com.kikichante.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientServer {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientServer(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    // !! WARNING !! if IOException -> disconnect
    public String readLine() throws IOException{
        var line = this.reader.readLine();
        if (line == null) throw new IOException("Serveur disconnected");
        return line;
    }

    //Permet l'envoie d'un message
    public void println(String message){
        this.writer.println(message);
    }
}
