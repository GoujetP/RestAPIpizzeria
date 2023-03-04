/**
 * OrdersDao permet de requêter les commandes dans la base de données.
 * @author Pierre Goujet & Khatri Goujet
 * @since 2023-03-04
 */

package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Orders;
import dto.Pizza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {
    /**
     * cette méthode permet de récupérer tout les details d'une commande dans la base de données.
     * @param id qui est l'identifiant de la commande à récupérer
     * @return List<Orders>
     */
    public static List<Orders> findById(int id) {
        List<Orders> orders = new ArrayList<>();
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("Select * from orders where orderid = ? LIMIT 1");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                orders.add(new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")), findPizzasOrderById(rs.getInt("orderId")), rs.getInt("qty"), rs.getDate("date").toLocalDate(), rs.getTime("hours").toLocalTime(), rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }
    /**
     * cette méthode permet de récupérer tout les details de toutes les commandes dans la base de données.
     * @return List<Orders>
     */

    public static List<Orders> findAll() {
        List<Orders> orders = new ArrayList<>();
        List<Pizza> pizzas = new ArrayList<Pizza>();
        try {
            String query = "Select * from orders ";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()) {

                orders.add (new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")), findPizzasOrderById(rs.getInt("orderId")), rs.getInt("qty"), rs.getDate("date").toLocalDate(),rs.getTime("hours").toLocalTime() , rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }
    /**
     * cette méthode permet de récupérer tout les details de toutes les commandes dans la base de données.
     * @param isFinish qui correspond au statut de la commande
     * @return List<Orders>
     */
    public static List<Orders> findByStatus(boolean isFinish) {
        List<Orders> orders = new ArrayList<>();
        try {DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from orders where finish = ?");
            stmt.setBoolean(1,isFinish);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                orders.add (new Orders(rs.getInt("orderId"), UserDAO.findById(rs.getInt("idU")), findPizzasOrderById(rs.getInt("orderId")), rs.getInt("qty"), rs.getDate("date").toLocalDate(),rs.getTime("hours").toLocalTime() , rs.getBoolean("finish")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return orders;
    }
    /**
     * cette méthode permet d'enregistrer une nouvelle commande dans la base de données.
     * @param orders qui correspond à la commande
     */
    public static void save(Orders orders){
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into orders values (?,?,?,?,?,?,?)");
            for (int i = 0 ; i < orders.getPizza().size();i++){
                stmt.setInt(1,orders.getOrderId());
                stmt.setInt(2, orders.getUser().getId());
                stmt.setInt(3,orders.getPizza().get(i).getId());
                stmt.setInt(4, orders.getQty());
                stmt.setDate(5, Date.valueOf(orders.getDate()));
                stmt.setTime(6,Time.valueOf(orders.getHours()));
                stmt.setBoolean(7, orders.isFinish());
                stmt.executeUpdate();
            }

            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }
    /**
     * cette méthode permet de récupérer le prix final d'une commande.
     * @param id id de la commande
     */
    public static double prixfinal(int id) {
        double rslt=0;
        Orders order = OrdersDAO.findById(id).get(0);
        try {
            for (Pizza p : order.getPizza()){
                rslt+=p.getPrice();
            }
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



    public static List<Pizza> getListPizza(JsonNode node) throws JsonProcessingException {
            String str = node.get("pizzas").toString();
            System.out.println(str);
            List<Pizza> res = new ArrayList<Pizza>();
            ObjectMapper objectMapper = new ObjectMapper();
            String[] separatedStrings = str.replaceAll("\\[", "")
                    .replaceAll("]", "").split(",");
            int[] intArray = new int[separatedStrings.length];
            for (int i = 0; i < separatedStrings.length; i++) {
                try {
                    intArray[i] = Integer.parseInt(separatedStrings[i]);
                } catch (Exception e) {
                    System.out.println("Unable to parse string to int: " + e.getMessage());
                }
            }
            //JsonNode nodeOrder = objectMapper.readTree(str);
            for (int integer : intArray) {
                res.add(PizzaDAO.findById(integer));
            }
            return res;


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


    public static List<Pizza> findPizzasOrderById(int idO) {
        ArrayList<Pizza> rslt = new ArrayList<Pizza>();
        try {
            String query = "select * from pizza inner join orders on orders.orderid="+idO+" where pizza.id=orders.idp ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                rslt.add(PizzaDAO.findById(rs.getInt("id")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return rslt;
    }
}
