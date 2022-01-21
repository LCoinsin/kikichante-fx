package com.kikichante.exception;

public class ClientDisconnectedException extends Exception {
    public ClientDisconnectedException(){
        super("Le client a été déconnecté.");
    }
}
