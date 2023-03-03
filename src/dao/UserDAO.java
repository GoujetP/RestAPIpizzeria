package dao;

import dto.Ingredient;
import dto.Pizza;
import dto.User;
import io.jsonwebtoken.Claims;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import controleurs.JwtManager;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDAO {
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


    public static Map<Integer,String> findAllToken() {
        Map<Integer,String> tokens = new HashMap<Integer,String>();
        try {
            String query = "select id,token from users ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (rs.next()) {
                tokens.put(rs.getInt("id"), rs.getString("token"));

            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return tokens;
    }

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * from users ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (rs.next()) {

                users.add(new User(rs.getInt("id"), rs.getString("name"), ("" + rs.getString("rue") + " " + rs.getString("city")), rs.getString("number"), rs.getString("email")));

            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return users;
    }

    public static void save(User user) {
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
            stmt.setInt(1,user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3,user.getAdresse());
            stmt.setString(4,user.getAdresse());
            stmt.setString(5,user.getNumber());
            stmt.setString(6,user.getMail());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }
}
