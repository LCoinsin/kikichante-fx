package com.kikichante.server;

public class  GameThread extends Thread {

    private GameServer game;

    public GameThread(GameServer game) {
        this.game = game;
    }

    @Override
    public void run() {
    }
}
