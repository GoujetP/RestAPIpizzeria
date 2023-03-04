/**
 * PizzaDao permet de requêter les Pizzas dans la base de données.
 * @author Pierre Goujet & Khatri Goujet
 * @since 2023-03-04
 */

package dao;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Ingredient;
import dto.Pizza;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PizzaDAO {
    /**
     * Permet de recuperer les Pizzas dans la base de données grâce à l'identifiant.
     * @param id identifiant de la pizza à recuperer.
     * @return Pizza
     */
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


    /**
     * Permet de récupérer les Pizzas dans la base de données grâce à l'identifiant.
     * @return List<Pizza>
     */
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

    /**
     * Permet d'enregistrer une nouvelle pizza dans la base de données.
     * @param pizza
     */
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

    /**
     * Permet de supprimer une pizza dans la base de données.
     * @param id identifiant de la pizza à supprimer.
     */
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

    /**
     * Permet d'ajouter un ingrédient précis dans une pizza précise.
     * @param p Pizza à pointer
     * @param i ingrédient à ajouter
     */
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
    /**
     * Permet d'obtenir le prix final aprés additions ,d'un pizza .
     * @param idP Pizza à pointer
     * @return  prix final
     */
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

    public static void createPizza(Pizza p , String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] pizzaSplitCompo = data.split("compo");
        System.out.println("Ingredients --> "+pizzaSplitCompo[1].substring(2,pizzaSplitCompo[1].length()-1));
        System.out.println("Pizza --> "+pizzaSplitCompo[0].substring(0,pizzaSplitCompo[0].length()-2)+"}");
        Ingredient[] compoIngredient = objectMapper.readValue(pizzaSplitCompo[1].substring(2,pizzaSplitCompo[1].length()-1) , Ingredient[].class);
        p = objectMapper.readValue(pizzaSplitCompo[0].substring(0,pizzaSplitCompo[0].length()-2)+"}", Pizza.class);
        ArrayList<Ingredient> compoFinal = new ArrayList<Ingredient>();
        Collections.addAll(compoFinal, compoIngredient);
        p.setCompo(compoFinal);
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
