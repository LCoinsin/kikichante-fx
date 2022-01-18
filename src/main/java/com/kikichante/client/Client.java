package com.kikichante.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    /********************************/

    public String readLine() throws IOException {
        var line = this.reader.readLine();
        if (line == null) throw new IOException("Serveur disconnected");
        return line;
    }

    /********************************/

    public void println(String message) {
        this.writer.println(message);
    }

    /********************************/

    public void connexion(String username, String password) {
        String message = "LOGIN:" + username + ":" + password;
        println(message);
    }
}
