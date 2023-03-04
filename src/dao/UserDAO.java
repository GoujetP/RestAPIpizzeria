/**
 * UserDAO permet de requêter la table User dans la base de données.
 * @author Pierre Goujet & Khatri Goujet
 * @since 2023-03-04
 */
package dao;

import dto.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dto.JwtManager;

public class UserDAO {
    /**
     * cette méthode permet de récupérer un utilisateur de la base de données grâce à son id.
     * @param id identifiant de l'utilisateur
     * @return User
     */
    public static User findById(int id) {
        User user = new User();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from users where id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            user = new User(rs.getInt("id"), rs.getString("name"), ("" + rs.getString("rue") + " " + rs.getString("city")), rs.getString("number"), rs.getString("email"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return user;
    }
    /**
     * cette méthode permet de vérifier si des identifiants sont corrects.
     * @param username identifiant de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return true si les identifiants sont corrects, false sinon
     */
    public static boolean connect(String username,String password){
        boolean present =false;
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select id from users where username=? and password=?;");
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            present=rs.next();
            System.out.println("All is ok!");
            return present;
        } catch (Exception e) {
            return false;
        }
        
    }
    /**
     * cette méthode permet de vérifier si le token est valable.
     * @param token token de connexion JWT
     * @return true si le token est valable, false sinon
     */
    public static boolean checkToken(String token){

        String username=JwtManager.tokenUsername(token);
        String password = null;
        try {
            DS.getConnection();
        PreparedStatement stmt=DS.connection.prepareStatement("Select password from users where username= ? ;");
        stmt.setString(1,username);
        ResultSet rs = stmt.executeQuery();
        DS.closeConnection();
        if(rs.next()){
            password=rs.getString("password");
        };
        JwtManager.decodeJWT(token,password);//pour check la validité
        //check=JwtManager.createJWT(username,password).equals(token);
        System.out.println("All is ok!");
        return true;
            
    } catch (Exception e) {
        return false;
    }

}

}
