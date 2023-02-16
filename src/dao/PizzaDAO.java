package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;
import dto.Pizza;

public class PizzaDAO {
	public static Pizza findById(int id) {
        Pizza pizza = new Pizza();
        try {
            String query = "Select * from pizza where id = " + id + "";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
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
			String query = "Insert into pizza values("+pizza.getId()+",'"+pizza.getName()+"',"+pizza.getPrix()+",'"+pizza.getPate()+"')";
			DS.getConnection();
            String queryCompo = "";
            for (Ingredient i : pizza.getCompo()){
                queryCompo = queryCompo +" Insert into compo values("+pizza.getId()+","+i.getId()+");";

            }
			DS.executeUpdate(query);
            DS.executeUpdate(queryCompo);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
    public static boolean contient (int idP,int idI){
        int size=0;
        try{
            String query = "Select count(*) from compo where idP="+idP+"and idI ="+idI+";";
            DS.getConnection();
			ResultSet rs=DS.executeQuery(query);
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
			String query = "DELETE from compo where idP="+idP+"and idI ="+idI+";";
			DS.getConnection();
			DS.executeUpdate(query);
			DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}

}
