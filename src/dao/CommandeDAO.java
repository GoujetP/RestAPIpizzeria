package dao;

import dto.Commande;
import dto.Pizza;
import dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeDAO {
    public static  Commande findById(int id) {
        Commande commande  = new Commande();
        try {
            String query = "Select * from commande where id = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            rs.next();
            commande = new Commande(UserDAO.findById(rs.getInt("idU")),PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), new Date(rs.getTimestamp("date").getTime()),rs.getBoolean("finish") );
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return commande;
    }
    public static List<Commande> findAll() {
        List<Commande> commandes  = new ArrayList<>();
        try {
            String query = "Select * from commande ";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()) {
                commandes.add(new Commande(UserDAO.findById(rs.getInt("idU")), PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), new Date(rs.getTimestamp("date").getTime()), rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return commandes;
    }

    public static void save(Commande commande){
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into commande values (?,?,?,?)");
            stmt.setInt(1,commande.getUser().getId());
            stmt.setInt(2,commande.getQty());
            stmt.setTimestamp(3,new Timestamp(commande.getDate().getTime()));
            stmt.setBoolean(4,commande.isFinish());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

}
