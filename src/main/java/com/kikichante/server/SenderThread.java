package com.kikichante.server;

import com.kikichante.client.Client;
import com.kikichante.utils.ColorOutput;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class SenderThread extends Thread {

    private ClientServer clientServer;
    private Bdd bdd;
    private ArrayList<GameServer> activeGame;
    private ArrayList<ClientServer> activeClient;
    private boolean isLeaved = false;
    private LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    public SenderThread (ClientServer clientServer, Bdd bdd, ArrayList<GameServer> activeGame, ArrayList<ClientServer> activeClient) {
        this.clientServer = clientServer;
        this.bdd = bdd;
        this.activeGame = activeGame;
        this.activeClient = activeClient;
    }

    @Override
    public void run() {
        while (!isLeaved) {
            var line = this.clientServer.readLine();
            if (line.startsWith("CLIENTLEAVED"))
                break;
            handleLine(line);
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
                GameServer game = new GameServer(messageCreateGame[1], bdd);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        game.generateListMusic();
                    }
                }).start();
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
        else if (message.startsWith("LEAVEINGAME")) {
            clientServer.println("EXIT");
            clientServer.getGame().removeClient(clientServer);
            if(clientServer.getGame().getCurrentPlayer().size() < 1) {
                activeGame.removeIf(game -> game.getGameName().equals(clientServer.getGame().getGameName()));
                updateListGames();
            } else {
                updateCurrentPlayerInGame();
                clientServer.setScore(0);
                clientServer.setGame(null);
            }
        }
        else if (message.startsWith("GETCURRENTPLAYERINGAME")) {
            updateCurrentPlayerInGame();
        }
        else if (message.startsWith("MUSIQUEDJ")) {
            this.clientServer.getGame().sendMusic(clientServer);
        }
        else if (message.startsWith("YESIHAVEMUSIC")) {
            this.clientServer.setHaveReceivedMusic(true);
        }
        else if (message.startsWith("READYFORTHISROUND")) {
            this.clientServer.setReadyToRound(true);
            updateCurrentPlayerInGame();
            if (clientServer.getGame().canStart()) {
                clientServer.getGame().playRound();
            } else {
                updateCurrentPlayerInGame();
            }
        }
        else if (message.startsWith("SUPPOSEANSWER")) {
            ColorOutput.redMessage(message);
            String[] messageAnswer = message.split(":");
            String author = null;
            String songName = null;

            for (String answ : messageAnswer) {
                if (answ.startsWith("author")) {
                    String[] auth = answ.split("-");
                    author = auth[1];
                }
                else if (answ.startsWith("song")) {
                    String[] son = answ.split("-");
                    songName = son[1];
                }
            }
            boolean winner = false;
            if (this.clientServer.getGame().getMusic() != null) {
                if (author != null)
                    if (levenshteinDistance.apply(author, this.clientServer.getGame().getMusic().getInterprete()) < 2) {
                    //if (new LevenshteinDistance().apply(author, this.clientServer.getGame().getMusic().getInterprete()) < 2) {
                    //if (author.equalsIgnoreCase(this.clientServer.getGame().getMusic().getInterprete())) {
                        this.clientServer.setScore(clientServer.getScore() + 1);
                        winner = true;
                    }
                if (songName != null)
                    if (levenshteinDistance.apply(songName, this.clientServer.getGame().getMusic().getTitre()) < 2) {
                    //if (new LevenshteinDistance().apply(songName, this.clientServer.getGame().getMusic().getTitre()) < 2) {
                    //if (songName.equalsIgnoreCase(this.clientServer.getGame().getMusic().getTitre())) {
                        this.clientServer.setScore(clientServer.getScore() + 1);
                        winner = true;
                    }
            }

            if (winner) {
                if (clientServer.getScore() >= 2) {
                    //Fin de partie
                    for (ClientServer c : clientServer.getGame().getCurrentPlayer()) {
                        c.println("ENDGAME");
                        //updateCurrentPlayerInGame();
                    }
                } else {
                    for (ClientServer c : clientServer.getGame().getCurrentPlayer() ) {
                        c.println("STOPMUSICWITHWINNER:"+clientServer.getUsernameFromBdd());
                        updateCurrentPlayerInGame();
                    }
                }
                clientServer.getGame().setRound(clientServer.getGame().getRound()+1);
            } else {
                clientServer.println("WRONGANSWER");
            }

        }
        else if (message.startsWith("MUSICNOTFOUND")) {
            clientServer.getGame().setRound(clientServer.getGame().getRound()+1);
            for (ClientServer c : clientServer.getGame().getCurrentPlayer()) {
                c.println("STOPWITHOUTWINNER");
            }
            this.clientServer.getGame().setSelectedMusic(false);
        }
        else if (message.startsWith("EXITENDGAME")) {
            clientServer.println("EXITENDGAME");
        }
        //END GAME
        else if (message.startsWith("GETSCOREENDGAME")) {
            String resMessage = "SCORESCREEN:";

            for (ClientServer c : clientServer.getGame().getCurrentPlayer()) {
                resMessage = resMessage.concat(c.getUsernameFromBdd()+"-"+c.getScore()+":");
            }
            clientServer.println(resMessage);
            clientServer.getGame().removeClient(clientServer);
            if(clientServer.getGame().getCurrentPlayer().size() < 1) {
                activeGame.removeIf(game -> game.getGameName().equals(clientServer.getGame().getGameName()));
                updateListGames();
            }
            clientServer.setScore(0);
            clientServer.setInGame(false);
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
            ColorOutput.greenMessage("message : " + message);
            //System.out.println("message = " + message);
        }
    }

    public void updateCurrentPlayerInGame() {
        String messageCurrentPlayer = "LISTCURRENTPLAYERINGAME";
        for (ClientServer client : this.clientServer.getGame().getCurrentPlayer()) {
            messageCurrentPlayer = messageCurrentPlayer.concat(":" + client.getUsernameFromBdd() + "-" + client.getScore() + "-"+client.isReadyToRound() );
        }
        for (ClientServer c : this.clientServer.getGame().getCurrentPlayer()) {
            c.println(messageCurrentPlayer);
        }
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
