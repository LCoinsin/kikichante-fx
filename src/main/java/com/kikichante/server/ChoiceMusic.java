package com.kikichante.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class ChoiceMusic {

    Connection con;

     public ChoiceMusic() {
        try
        {
            this.con = DriverManager.getConnection("jdbc:mysql://cl401116-001.eu.clouddb.ovh.net:35686/test?characterEncoding=latin1", "Mysql", "9PmXX46ev");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void closeBddConnexionMusic() {
        try {
            con.close();
            System.out.println("Deconnexion de la BDD");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public Music queryConnexionMusic() {
        Random r = new Random();
        int n = r.nextInt(3);

        boolean retour = false;
        ResultSet resultats = null;
        Music randomMusicChoice = null;
        try
        {
            PreparedStatement titreMusique = this.con.prepareStatement("Select * from kikichante.musique where Code_musique = ?;");

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

