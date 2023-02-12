package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAO {
	public static Pizza findById(int id) {
        Pizza pizza = null;
        try {
            String query = "Select * from compo where idP = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            rs.next();
            pizza = new Pizza( rs.getString("name"),rs.getString("pate"),rs.getDouble("prix"));
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return pizza;
    }
    
    
    
    public static List<Pizza> findAll() {
        List<Pizza> pizza = new ArrayList<>();
        try {
            String query = "Select * from compo";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()){
                pizza.add(new Pizza( rs.getString("name"),rs.getString("pate"),rs.getDouble("prix")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
        }
        return pizza;
    }

    public static void save(Pizza pizza){
		try{
			String query = "Insert into pizza values("+pizza.getId()+",'"+pizza.getName()+"','"+pizza.getPrix()+")";
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    
    public static void remove(int id){
		try{
			String query = "DELETE from pizza where id="+id;
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
}
