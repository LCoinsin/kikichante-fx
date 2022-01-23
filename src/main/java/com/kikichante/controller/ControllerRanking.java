package com.kikichante.controller;

import com.kikichante.client.Client;

import java.io.IOException;

public class ControllerRanking {
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void printScore() {
        try {
            var message = client.readLine();
            System.out.println("message = " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
