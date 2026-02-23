package com.gestion.dao;

import com.gestion.model.departement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DepartementDAO {

    public void ajouter(departement d) {
        String sql = "INSERT INTO departement (nom, description) VALUES (?, ?)";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, d.getNom());
            pstmt.setString(2, d.getDescription());
            pstmt.executeUpdate();
            System.out.println("Département ajouté !");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<departement> listerTout() {
        List<departement> liste = new ArrayList<>();
        String sql = "SELECT * FROM departement";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                liste.add(new departement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    // MODIFIER un département
    public void modifier(departement dept) {
        String sql = "UPDATE departement SET nom=?, description=? WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dept.getNom());
            pstmt.setString(2, dept.getDescription());
            pstmt.setInt(3, dept.getId());

            pstmt.executeUpdate();
            System.out.println("✅ Département mis à jour !");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // SUPPRIMER un département
    public void supprimer(int id) {

        String sql = "DELETE FROM departement WHERE id=?";
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("🗑️ Département supprimé !");
        } catch (SQLException e) { e.printStackTrace(); }
    }

}
