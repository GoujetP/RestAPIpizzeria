package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;

public class IngredientDAO {
    
    public static Ingredient findById(int id) {
        Ingredient ingredient = new Ingredient();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select * from ingredients where id = ?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
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
            DS.getConnection();
            PreparedStatement stmt= DS.connection.prepareStatement("insert into ingredients values(?,?,?)");
            stmt.setInt(1,ingredient.getId());
            stmt.setString(2, ingredient.getName());
            stmt.setDouble(3,ingredient.getPrice());
			stmt.executeUpdate();
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    
    public static void remove(int id){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from ingredients where id= ?");
            stmt.setInt(1,id);
            stmt.executeUpdate();
            DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
}