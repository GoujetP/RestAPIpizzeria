package dao;

import dto.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilisateurDAO {
    public static Utilisateur findById(int uno) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from utilisateurs where uno = ?");
            stmt.setInt(1,uno);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            utilisateur = new Utilisateur(rs.getInt("uno"), rs.getString("name"), ("" + rs.getString("rue") + " " + rs.getString("ville")), rs.getString("tel"), rs.getString("email"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return utilisateur;
    }
}
