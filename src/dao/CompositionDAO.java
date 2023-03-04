package dao;



import dto.Ingredient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CompositionDAO {

    public static List<Ingredient> findCompoById(int pno) {
        ArrayList<Ingredient> res = new ArrayList<Ingredient>();
        try {
            String query = "Select * from ingredients INNER JOIN composition ON composition.ingredient = ingredients.ino where composition.pizza = " + pno + " ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                res.add(new Ingredient(rs.getInt("ino"), rs.getString("name"), rs.getDouble("prix")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return res;
    }
}
