package dao;

import dto.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
            orders = new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")),PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), new Date(rs.getTimestamp("date").getTime()),rs.getBoolean("finish") );
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
                orders.add(new Orders(rs.getInt("orderId"),UserDAO.findById(rs.getInt("idU")), PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), new Date(rs.getTimestamp("date").getTime()), rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }

    public static void save(Orders orders){
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into orders values (?,?,?,?)");
            stmt.setInt(1, orders.getUser().getId());
            stmt.setInt(2, orders.getQty());
            stmt.setTimestamp(3,new Timestamp(orders.getDate().getTime()));
            stmt.setBoolean(4, orders.isFinish());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

}
