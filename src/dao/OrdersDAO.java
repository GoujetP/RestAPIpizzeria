package dao;

import com.fasterxml.jackson.databind.JsonNode;
import dto.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    public static List<Orders> findById(int id) {
        List<Orders> orders = new ArrayList<>();
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("Select * from orders where orderid = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                orders.add(new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")), PizzaDAO.findById(rs.getInt("idP")), rs.getInt("qty"), rs.getDate("date").toLocalDate(), rs.getTime("hours").toLocalTime(), rs.getBoolean("finish")));
            }System.out.println("All is ok!");
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

    public static double prixfinal(int id) {
        int rslt=0;
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("select sum(i.price+p.price)*qty as total from orders INNER JOIN pizza p on p.id = orders.idP  INNER JOIN compo c on p.id = c.idP INNER JOIN ingredients i on i.id = c.idI where orderId=? group by idU,qty;");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            rslt=rs.getInt("total");
            System.out.println("All is ok!");
        } catch (Exception e) {
            return rslt;
        }
        return rslt;
    }

    public static int getUserId(JsonNode node) {
        return node
                .get("idu")
                .asInt();
    }

    public static int getPizzaId(JsonNode node) {
        return node
                .get("idp")
                .asInt();
    }

    public static int getOrderId(JsonNode node){
        return node
                .get("orderId")
                .asInt();
    }

    public static int getQty(JsonNode node){
        return node
                .get("qty")
                .asInt();
    }

    public static String getDate(JsonNode node){
        return String.valueOf(node
                .get("date")).substring(1,String.valueOf(node
                .get("date")).length()-1);
    }


    public static String getHours(JsonNode node){
        return String.valueOf(node
                .get("hours")).substring(1,String.valueOf(node
                .get("hours")).length()-1);
    }

    public static boolean getFinish(JsonNode node){
        return node
                .get("finish")
                .asBoolean();
    }
}
