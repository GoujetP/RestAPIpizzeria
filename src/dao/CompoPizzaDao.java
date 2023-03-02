package dao;



import dto.Ingredient;
import dto.Pizza;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CompoPizzaDao {

    public static List<Pizza> findCompoById(int idO) {
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