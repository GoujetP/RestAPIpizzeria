package dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Ingredient;
import dto.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.*;
public class PizzaDAO {
	public static Pizza findById(int id) {
        Pizza pizza = new Pizza();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from pizza where id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            rs.next();
            pizza = new Pizza( rs.getInt("id"),rs.getString("name"),rs.getString("pate"),rs.getDouble("price"),CompoDao.findCompoById(rs.getInt("id")));
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

                pizza.add(new Pizza( rs.getInt("id"),rs.getString("name"),rs.getString("pate"),rs.getDouble("price"),CompoDao.findCompoById(rs.getInt("id"))));
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
            stmt.setInt(1,pizza.getId());
            stmt.setString(2, pizza.getName());
            stmt.setDouble(3,pizza.getPrice());
            stmt.setString(4,pizza.getPate());
            String queryCompo = "";
            for (Ingredient i : pizza.getCompo()){
                queryCompo = queryCompo +" Insert into compo values("+pizza.getId()+","+i.getId()+");";

            }
			stmt.executeUpdate();
            DS.executeUpdate(queryCompo);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    public static boolean contient (int idP,int idI){
        int size=0;
        try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select count(*) from compo where idP=? and idi=?");
            stmt.setInt(1,idP);
            stmt.setInt(2,idI);
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

    public static void remove(int id){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from pizza where id= ?");
            stmt.setInt(1,id);
			stmt.executeUpdate();
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    public static void removeIP(int idP,int idI){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from compo where idP= ? and idI=?");
            stmt.setInt(1,idP);
            stmt.setInt(2,idI);
            stmt.executeUpdate();
            DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}

    public static void addIngredient(Pizza p ,Ingredient i){
        try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Insert into compo values(?,?)");
            stmt.setInt(1,p.getId());
            stmt.setInt(2,i.getId());
            stmt.executeUpdate();
            DS.closeConnection();
        } catch(Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
    }
    public static double getFinalPrice(int idP){
       double price=0.0;
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("select pizza.price,sum(ingredients.price)from pizza INNER JOIN compo ON compo.idP = pizza.id INNER JOIN ingredients ON compo.idI = ingredients.id where idP= ? group by pizza.price;");
            stmt.setInt(1,idP);
            ResultSet rs = stmt.executeQuery();
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                price=Math.round(rs.getDouble("sum")+rs.getDouble("price"));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return 0.0;
        }
        return price;
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
