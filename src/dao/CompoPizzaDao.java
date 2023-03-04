package dao;



import dto.Ingredient;
import dto.Pizza;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CompoPizzaDao {

    public static List<Pizza> findCompoById(int cno) {
        ArrayList<Pizza> res = new ArrayList<Pizza>();
        try {
            String query = "select * from pizza inner join commandes on commandes.cno="+cno+" where pizza.pno=commandes.pizza ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                res.add(PizzaDAO.findById(rs.getInt("pno")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return res;
    }
}