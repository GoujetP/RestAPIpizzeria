package dao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Ingredient;



public class CompoDao {

    public static List<Ingredient> findCompoById(int idP) {
        ArrayList<Ingredient> rslt = new ArrayList<Ingredient>();
        try {
            String query = "Select * from ingredients INNER JOIN compo ON compo.idI = ingredients.id where idP = "+idP+" ;";
            DS.getConnection();
            ResultSet rs = DS.executeQuery(query);
            DS.closeConnection();
            while(rs.next()){
                rslt.add(new Ingredient(rs.getInt("id"), rs.getString("name"),rs.getDouble("price")));
            }
            System.out.println("All is ok!");
        } catch (Exception e) {
            return null;
        }
        return rslt;
    }

   // public static void main(String[] args) {
     //   System.out.println(findCompoById(5).toString());
    //}

}
