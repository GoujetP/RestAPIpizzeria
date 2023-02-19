package dao;

import dto.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public static Orders findById(int id) {
        Orders orders = new Orders();
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("Select * from orders where idU = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            orders = new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")),PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), rs.getDate("date").toLocalDate(),rs.getTime("hours").toLocalTime() , rs.getBoolean("finish"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }


    public static List<Orders> findAll() {
        List<Orders> orders = new ArrayList<>();
        try {
            String query = "Select * from orders ";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()) {
                orders.add (new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")),PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), rs.getDate("date").toLocalDate(),rs.getTime("hours").toLocalTime() , rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }
    public static List<Orders> findByStatus(boolean isFinish) {
        List<Orders> orders = new ArrayList<>();
        try {DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from orders where finish = ?");
            stmt.setBoolean(1,isFinish);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                orders.add (new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")),PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), rs.getDate("date").toLocalDate(),rs.getTime("hours").toLocalTime() , rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }
    public static void save(Orders orders){
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into orders values (?,?,?,?,?,?,?)");
            stmt.setInt(1,orders.getOrderId());
            stmt.setInt(2, orders.getUser().getId());
            stmt.setInt(3,orders.getPizza().getId());
            stmt.setInt(4, orders.getQty());
            stmt.setDate(5, Date.valueOf(orders.getDate()));
            stmt.setTime(6,Time.valueOf(orders.getHours()));
            stmt.setBoolean(7, orders.isFinish());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

}
