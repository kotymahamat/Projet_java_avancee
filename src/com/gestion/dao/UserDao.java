package com.gestion.dao;
import java.sql.*;


public class UserDao {
    public boolean authentifier(String login, String password) {
        String sql = "SELECT * FROM utilisateurs WHERE login = ? AND password = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
