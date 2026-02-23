package com.gestion.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_entreprise";
    private static final String USER = "root";
    private static final String PASSWORD = "";

public static Connection getConnection() {
    Connection con = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Établissement de la connexion
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println(" Connexion à MySQL réussie !");

    } catch (ClassNotFoundException e) {
        System.err.println(" Erreur : Le Driver MySQL est introuvable. Vérifie ton dossier lib.");
    } catch (SQLException e) {
        System.err.println(" Erreur de connexion : " + e.getMessage());
        System.err.println("Vérifie que XAMPP est lancé et que la base 'gestion_entreprise' existe.");
    }
    return con;
}
}
