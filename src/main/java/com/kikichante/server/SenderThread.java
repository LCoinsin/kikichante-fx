package com.kikichante.server;

import com.kikichante.client.Client;
import com.kikichante.exception.ClientDisconnectedException;

import java.io.IOException;
import java.util.ArrayList;

public class SenderThread extends Thread {

    private ClientServer clientServer;
    private Bdd bdd;
    private ArrayList<GameServer> activeGame;
    private ArrayList<ClientServer> activeClient;
    private boolean isLeaved = false;

    public SenderThread (ClientServer clientServer, Bdd bdd, ArrayList<GameServer> activeGame, ArrayList<ClientServer> activeClient) {
        this.clientServer = clientServer;
        this.bdd = bdd;
        this.activeGame = activeGame;
        this.activeClient = activeClient;
    }

    @Override
    public void run() {
        try {
            while (!isLeaved) {
                var line = this.clientServer.readLine();
                handleLine(line);
            }
        } catch (ClientDisconnectedException e) {
            //disconnect
            e.printStackTrace();
        }
    }

    public void handleLine(String message) {
        if (message.startsWith("UUID")) {
            String[] messageUUID = message.split(":");
            this.clientServer.setUserId(messageUUID[1]);
        }
        else if (message.startsWith("LOGIN")) {  //LOGIN:USERNAME:PASSWORD
            String[] messageConnexion = message.split(":");
            String username = messageConnexion[1];
            String password = messageConnexion[2];

            if (bdd.queryConnexion(username, password)) {
                System.out.println("Connexion OK");
                boolean isAlreadyConnected = false;
                for (ClientServer c : activeClient) {
                    if (!(c.getUsernameFromBdd() == null))
                        if (c.getUsernameFromBdd().equals(username))
                            isAlreadyConnected = true;
                }
                if (isAlreadyConnected) {
                    this.clientServer.println("LOGIN:KO");
                } else {
                    this.clientServer.setUsernameFromBdd(username);
                    this.clientServer.println("LOGIN:OK");
                }
            } else {
                System.out.println("Connexion KO");
                this.clientServer.println("LOGIN:KO");
            }

        }
        else if (message.startsWith("CREATEACCOUNT")) {
            String[] messageInscription = message.split(":");
            String username = messageInscription[1];
            String password = messageInscription[2];

            if (bdd.queryInscription(username, password)) {
                System.out.println("Nouvelle inscription : " + username);
                this.clientServer.setUsernameFromBdd(username);
                this.clientServer.println("INSCRIPTION:OK");
            } else {
                System.out.println("ERROR inscription : " + username);
                this.clientServer.println("INSCRIPTION:KO");
            }
        }
        //TO LIST GAME
        else if (message.startsWith("GOVIEWLISTGAME")) {
            this.clientServer.setInListGame(true);
        }
        else if (message.startsWith("GOOUTVIEWLISTGAME")) {
            this.clientServer.setInListGame(false);
        }
        //OUT GAME
        else if (message.startsWith("CREATEGAME")) {
            String[] messageCreateGame = message.split(":");
            String gameName = messageCreateGame[1];
            boolean createGame = true;
            //Creation de la partie et run
            for (GameServer game : activeGame) {
                if (game.getGameName().equals(gameName)) {
                    createGame = false;
                    this.clientServer.println("CREATEGAME:KO");
                }
            }
            if (createGame) {
                GameServer game = new GameServer(messageCreateGame[1]);
                this.activeGame.add(game);
                this.clientServer.setGame(game);
                game.addClient(this.clientServer);
                new GameThread(game).start();
                System.out.println("activeGame = " + activeGame);
                this.clientServer.println("CREATEGAME:OK");
                updateListGames();
            }
        }
        else if (message.startsWith("GETCURRENTLISTGAME")) {
            String messageListGame = "LISTGAME";
            for (GameServer game : activeGame) {
                if (game.isAccessible())
                    messageListGame = messageListGame.concat(":" + game.getGameName());
            }
            clientServer.println(messageListGame);
        }
        else if (message.startsWith("LEAVEGAME")) {
            clientServer.getGame().removeClient(clientServer);
            if(clientServer.getGame().getCurrentPlayer().size() < 1) {
                activeGame.removeIf(game -> game.getGameName().equals(clientServer.getGame().getGameName()));
                updateListGames();
            }
            else
                clientServer.getGame().updateListPlayerWaitingRoom(clientServer);
            clientServer.println("EXIT");
        }
        else if (message.startsWith("JOINGAME")) {
            //TODO -> Rejoindre une game
            String[] messageJoinGame = message.split(":");
            String gameName = messageJoinGame[1];
            boolean gameExist = false;
            for (GameServer game : activeGame) {
                if (game.getGameName().equals(gameName)){
                    game.addClient(this.clientServer);
                    game.updateListPlayerWaitingRoom(clientServer);
                    this.clientServer.setGame(game);
                    gameExist = true;
                    break;
                }
            }

            if (gameExist) {
                System.out.println("Join game ok");
                this.clientServer.println("JOINGAME:OK");
            } else {
                System.out.println("la partie existe pas !!");
                this.clientServer.println("JOINGAME:KO");
            }
        }
        //IN GAME
        else if (message.startsWith("CLIENTLEAVEGAME")) {
            clientServer.getGame().removeClient(clientServer);
            if(clientServer.getGame().getCurrentPlayer().size() < 1) {
                activeGame.removeIf(game -> game.getGameName().equals(clientServer.getGame().getGameName()));
                updateListGames();
            } else {
                this.clientServer.getGame().updateListPlayerInGame(this.clientServer);
            }
            clientServer.println("EXIT");
        }
        else if (message.startsWith("GETCURRENTPLAYERINGAME")) {
            updateCurrentPlayerInGame();
        }
        //WAITING ROOM
        else if (message.startsWith("GETCURRENTPLAYERINWAITINGROOM")) {
            String messageCurrentPlayer = "LISTCURRENTPLAYERINWAITINGROOM";
            for (ClientServer client : this.clientServer.getGame().getCurrentPlayer()) {
                messageCurrentPlayer = messageCurrentPlayer.concat(":"+client.getUsernameFromBdd()+"-"+(client.isReady() ? "OK" : "KO"));
            }
            this.clientServer.println(messageCurrentPlayer);
        }
        else if (message.startsWith("SETREADY")) {
            String[] resMessage = message.split(":");
            System.out.println("SETREADY");
            if (resMessage[1].equals("OK")) {
                clientServer.setReady(true);
                this.clientServer.getGame().updateListPlayerStateWaitingRoom();
                //TODO Check si on peut lancer une partie ? START GAME : RIEN
                if (this.clientServer.getGame().canLaunchGame()) {
                    this.clientServer.getGame().startGame();
                    updateListGames();
                }
            } else if (resMessage[1].equals("KO")) {
                clientServer.setReady(false);
                this.clientServer.getGame().updateListPlayerStateWaitingRoom();
            }
        }
        //DEFAULT
        else if (message.startsWith("EXIT")) {
            this.clientServer.println("EXIT");
        }
        else {
            System.out.println("message = " + message);
        }

    }
    public void updateCurrentPlayerInGame() {
        String messageCurrentPlayer = "LISTCURRENTPLAYERINGAME";
        for (ClientServer client : this.clientServer.getGame().getCurrentPlayer()) {
            messageCurrentPlayer = messageCurrentPlayer.concat(":" + client.getUsernameFromBdd() + "-" + client.getScore() );
        }
        this.clientServer.println(messageCurrentPlayer);
    }

    public void updateListGames() {
        String messageListGame = "LISTGAME";
        for (GameServer game : activeGame) {
            if (game.isAccessible())
                messageListGame = messageListGame.concat(":" + game.getGameName());
        }
        for (ClientServer c : activeClient) {
            if (c.isInListGame()) {
                System.out.println(c.getUsernameFromBdd());
                c.println(messageListGame);
            }
        }
    }
}
