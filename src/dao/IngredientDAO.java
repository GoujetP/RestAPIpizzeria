package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAO {
    
    public static Ingredient findById(int id) {
        Ingredient ingredient = new Ingredient();
        try {
            String query = "Select * from ingredients where id = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            rs.next();
            ingredient = new Ingredient(rs.getInt("id"), rs.getString("name"),rs.getDouble("price"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return ingredient;
    }
    
    
    
    public static List<Ingredient> findAll() {
        List<Ingredient> ingredient = new ArrayList<>();
        try {
            String query = "Select * from ingredients";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()){
                ingredient.add(new Ingredient(rs.getInt("id"), rs.getString("name"),rs.getDouble("price")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return ingredient;
    }

    public static void save(Ingredient ingredient){
		try{
			String query = "Insert into ingredients values("+ingredient.getId()+",'"+ingredient.getName()+"',"+ingredient.getPrice()+")";
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    
    public static void remove(int id){
		try{
			String query = "DELETE from ingredients where id="+id;
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
}