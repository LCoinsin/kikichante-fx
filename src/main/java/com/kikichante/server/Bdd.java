package com.kikichante.server;

import com.kikichante.exception.ExceptionBddConnexion;

import java.sql.*;
import java.util.Random;

public class Bdd {

    private Connection connection;

    public Bdd() throws ExceptionBddConnexion {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://cl401116-001.eu.clouddb.ovh.net:35686/kikichante?characterEncoding=latin1", "Mysql", "9PmXX46ev");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            System.exit(0);
        }
    }

    public void closeBdd() {
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean queryConnexion(String username, String password) {
        ResultSet resultSet = null;

        try {
            PreparedStatement recherchePersonne = this.connection.prepareStatement("SELECT * FROM users WHERE username = ? and password = ?");

            recherchePersonne.setString(1, username);
            recherchePersonne.setString(2, password);

            resultSet = recherchePersonne.executeQuery();

            if (resultSet.isBeforeFirst())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean queryInscription(String username, String password) {
        try {
            PreparedStatement inscriptionUser = this.connection.prepareStatement("INSERT INTO users VALUE (?,?)");

            inscriptionUser.setString(1, username);
            inscriptionUser.setString(2, password);

            inscriptionUser.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Music querySelectMusic() {
        Random r = new Random();
        int n = r.nextInt(3);

        boolean retour = false;
        ResultSet resultats = null;
        Music randomMusicChoice = null;
        try
        {
            PreparedStatement titreMusique = this.connection.prepareStatement("Select * from kikichante.musique where Code_musique = ?;");

            titreMusique.setInt(1,n);

            resultats = titreMusique.executeQuery();

            if (resultats.isBeforeFirst()) {
                while (resultats.next()) {
                    int codeMusic = resultats.getInt(6);
                    String titre = resultats.getString(1);
                    String interprete = resultats.getString(2);
                    String url = resultats.getString(3);
                    int annee = resultats.getInt(4);
                    String theme = resultats.getString(5);

                    randomMusicChoice = new Music(codeMusic,titre,interprete,url,annee,theme);

                    System.out.println("randomMusicChoice = " + randomMusicChoice.toString());
                }
            } else {
                return null;
            }
            //System.out.println( resultats.getString(1));

        }
        catch(Exception e){
            System.out.println(e);
        }
        return randomMusicChoice;
    }
}
