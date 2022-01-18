package com.kikichante.server;

import java.sql.*;

public class Bdd {

    private Connection connection;

    public Bdd() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://cl401116-001.eu.clouddb.ovh.net:35686/kikichante?characterEncoding=latin1", "Mysql", "9PmXX46ev");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
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
}
