package dao;

import dto.Ingredient;
import dto.Pizza;
import dto.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static User findById(int id) {
        User user = new User();
        try {
            String query = "Select * from users where id = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            rs.next();
            user = new User(rs.getInt("id"), rs.getString("name"), ("" + rs.getString("rue") + "" + rs.getString("city")), rs.getString("number"), rs.getString("email"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * from users ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (rs.next()) {

                users.add(new User(rs.getInt("id"), rs.getString("name"), ("" + rs.getString("rue") + "" + rs.getString("city")), rs.getString("number"), rs.getString("email")));

            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return users;
    }

    public static void save(User user) {
        try {
            String query = "Insert into users values(" + user.getId() + ",'" + user.getName() + "'," + user.getAdresse() + ",'" + user.getNumber() + ",'"+user.getMail() +"')";
            DS.getConnection();
            DS.executeUpdate(query);
            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }
}
