package dao;



import dto.Ingredient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CompoDao {

    public static List<Ingredient> findCompoById(int idP) {
        ArrayList<Ingredient> rslt = new ArrayList<Ingredient>();
        try {
            String query = "Select * from ingredients INNER JOIN compo ON compo.idI = ingredients.id where idP = " + idP + " ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                rslt.add(new Ingredient(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return rslt;
    }
}
