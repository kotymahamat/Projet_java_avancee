package com.gestion.dao;
import com.gestion.model.Employe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {
    public void ajouter(Employe emp) {
        String sql = "INSERT INTO employe (nom, prenom, email, id_departement) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getNom());
            pstmt.setString(2, emp.getPrenom());
            pstmt.setString(3, emp.getEmail());
            pstmt.setInt(4, emp.getIdDepartement());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employe> listerTout() {
        List<Employe> liste = new ArrayList<>();
        String sql = "SELECT * FROM employe";
        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Employe(rs.getInt("id"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("email"), rs.getInt("id")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }


    public void modifier(Employe emp) {
        String sql = "UPDATE employe SET nom=?, prenom=?, email=?, id_departement=? WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getNom());
            pstmt.setString(2, emp.getPrenom());
            pstmt.setString(3, emp.getEmail());
            pstmt.setInt(4, emp.getIdDepartement());
            pstmt.setInt(5, emp.getId()); // On cible l'employé par son ID

            pstmt.executeUpdate();
            System.out.println("Employé mis à jour !");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM employe WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Employé supprimé !");
        } catch (SQLException e) { e.printStackTrace(); }
    }



}
