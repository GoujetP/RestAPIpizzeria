package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Ingredient;
import dto.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PizzaDAO {
	public static Pizza findById(int pno) {
        Pizza pizza = new Pizza();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from pizza where pno = ?");
            stmt.setInt(1,pno);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            pizza = new Pizza( rs.getInt("pno"),rs.getString("name"),rs.getString("pate"),rs.getDouble("prix"), CompositionDAO.findCompoById(rs.getInt("pno")));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return pizza;
    }
    
    
    
    public static List<Pizza> findAll() {
        List<Pizza> pizza = new ArrayList<>();
        try {
            String query = "select * from pizza ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()){

                pizza.add(new Pizza( rs.getInt("pno"),rs.getString("name"),rs.getString("pate"),rs.getDouble("prix"), CompositionDAO.findCompoById(rs.getInt("pno"))));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return pizza;
    }

    public static void save(Pizza pizza){
		try{
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into pizza values(?,?,?,?)");
            stmt.setInt(1,pizza.getPno());
            stmt.setString(2, pizza.getName());
            stmt.setDouble(3,pizza.getPrix());
            stmt.setString(4,pizza.getPate());
            String queryCompo = "";
            for (Ingredient i : pizza.getComposition()){
                queryCompo = queryCompo +" Insert into composition values("+pizza.getPno()+","+i.getIno()+");";

            }
			stmt.executeUpdate();
            DS.executeUpdate(queryCompo);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    public static boolean contient (int pno,int ino){
        int size=0;
        try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select count(*) from composition where pno=? and ino=?");
            stmt.setInt(1,pno);
            stmt.setInt(2,ino);
			ResultSet rs=stmt.executeQuery();
			DS.closeConnection();
            if (rs != null) 
            {
              rs.last();    // moves cursor to the last row
                 size = rs.getRow(); // get row id 
            }

		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
       
        return size!=0;
    }  

    public static void remove(int pno){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from pizza where pno= ?");
            stmt.setInt(1,pno);
			stmt.executeUpdate();
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    public static void removeIP(int pno,int ino){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from composition where pno= ? and inno=?");
            stmt.setInt(1,pno);
            stmt.setInt(2,ino);
            stmt.executeUpdate();
            DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}

    public static void addIngredient(Pizza p ,Ingredient i){
        try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Insert into composition values(?,?)");
            stmt.setInt(1,p.getPno());
            stmt.setInt(2,i.getIno());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch(Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }
    public static double getFinalPrice(int pno){
       double prix=0.0;
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("select pizza.prix,sum(ingredients.prix)from pizza INNER JOIN composition ON composition.pno = pizza.pno INNER JOIN ingredients ON composition.ino = ingredients.ino where pno= ? group by pizza.prix;");
            stmt.setInt(1,pno);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                prix=Math.round(rs.getDouble("sum")+rs.getDouble("prix"));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return 0.0;
        }
        return prix;
    }
    public static JsonNode doMergeWithJackson(JsonNode jsonNode1, JsonNode jsonNode2) {
        if (jsonNode1.isObject() && jsonNode2.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode1;
            jsonNode2.fields().forEachRemaining(entry -> {
                JsonNode value = entry.getValue();
                if (value.isObject()) {
                    objectNode.set(entry.getKey(), doMergeWithJackson(objectNode.get(entry.getKey()), value));
                } else {
                    objectNode.set(entry.getKey(), value);
                }
            });
            return objectNode;
        }
        return jsonNode2;
    }

}
