package dao;

import dto.Ingredient;
import dto.Pizza;
import dto.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static int connect(String username,String password){
        int present =0;
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select id from users where username=? and password=?;");
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            present=rs.getInt("id");
            System.out.println("All is ok!");
        } catch (Exception e) {
            return 0;
        }
        return present;
    }

    public static boolean checkToken(String token){
        boolean check=false;
        try {
        DS.getConnection();
        PreparedStatement stmt=DS.connection.prepareStatement("Select token from users where token=? ;");
        stmt.setString(1,token);

        ResultSet rs = stmt.executeQuery();
        DS.closeConnection();
        check=rs.next();
        System.out.println("All is ok!");
    } catch (Exception e) {
        return false;
    }
        return check;
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
