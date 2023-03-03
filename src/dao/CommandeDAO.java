package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Commande;
import dto.Pizza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO {
    public static List<Commande> findById(int cno) {
        List<Commande> commandes = new ArrayList<>();
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("Select * from commandes where cno = ? LIMIT 1");
            stmt.setInt(1,cno);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                commandes.add(new Commande(rs.getInt("cno"), UtilisateurDAO.findById(rs.getInt("uno")), CompoPizzaDao.findCompoById(rs.getInt("cno")), rs.getInt("quantite"), rs.getDate("date").toLocalDate(), rs.getTime("heure").toLocalTime(), rs.getBoolean("fini")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return commandes;
    }
    public static List<Commande> findByStatus(boolean isFinish) {
        List<Commande> commandes = new ArrayList<>();
        try {DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from commandes where fini = ?");
            stmt.setBoolean(1,isFinish);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while(rs.next()) {
                commandes.add (new Commande(rs.getInt("cno"), UtilisateurDAO.findById(rs.getInt("uno")),CompoPizzaDao.findCompoById(rs.getInt("cno")), rs.getInt("quantite"), rs.getDate("date").toLocalDate(),rs.getTime("heure").toLocalTime() , rs.getBoolean("fini")));
            }System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return commandes;
    }
    public static void save(Commande commandes){
        try {
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into commandes values (?,?,?,?,?,?,?)");
            for (int i = 0 ; i < commandes.getPizza().size();i++){
                stmt.setInt(1,commandes.getCno());
                stmt.setInt(2, commandes.getUtilisateur().getUno());
                stmt.setInt(3,commandes.getPizza().get(i).getPno());
                stmt.setInt(4, commandes.getQuantite());
                stmt.setDate(5, Date.valueOf(commandes.getDate()));
                stmt.setTime(6,Time.valueOf(commandes.getHeure()));
                stmt.setBoolean(7, commandes.isFini());
                stmt.executeUpdate();
            }

            DS.closeConnection();
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }

    public static double prixfinal(int cno) {
        double res=0;
        Commande commande = CommandeDAO.findById(cno).get(0);
        try {
            for (Pizza p : commande.getPizza()){
                res+=p.getPrix();
            }
        } catch (Exception e) {
            return res;
        }
        return res;
    }

    public static int getUserId(JsonNode node) {
        return node
                .get("uno")
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
                .get("cno")
                .asInt();
    }

    public static int getQuantite(JsonNode node){
        return node
                .get("quantite")
                .asInt();
    }

    public static String getDate(JsonNode node){
        return String.valueOf(node
                .get("date")).substring(1,String.valueOf(node
                .get("date")).length()-1);
    }


    public static String getHeure(JsonNode node){
        return String.valueOf(node
                .get("heure")).substring(1,String.valueOf(node
                .get("heure")).length()-1);
    }

    public static boolean getFini(JsonNode node){
        return node
                .get("fini")
                .asBoolean();
    }
}
