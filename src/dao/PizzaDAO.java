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
            String query = "Select * from pizza where id = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            rs.next();
            pizza = new Pizza( rs.getInt("id"),rs.getString("name"),rs.getString("pate"),rs.getDouble("price"));
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
                pizza.add(new Pizza( rs.getInt("id"),rs.getString("name"),rs.getString("pate"),rs.getDouble("price")));
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
    public static boolean contient (int idP,int idI){
        boolean rslt=false;
        try{
            String query = "Select * from compo where id="+idP+"and idI ="+idI+";";
            DS.getConnection();
			ResultSet rs=DS.executeQuery(query);
			DS.closeConnection();
            rslt=rs.next();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
        return rslt;
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
    public static void removeIP(int idP,int idI){
		try{
			String query = "DELETE from compo where id="+idP+"and idI ="+idI+";";
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}

}
