/**
 * CompoDao permet de requêter les ingredients d'une pizza via la table DAO
 * @author Pierre Goujet & Khatri Goujet
 * @since 2023-03-04
 */
package dao;
import dto.Ingredient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CompoDao {
    /**
     * cette méthode permet de récupérer toutes les ingredients d'une pizza précise'
     * @param idP qui est l'identifiant de la pizza
     * @return List<Ingredient>
     */
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

    /**
     * Permet de verifier si une pizza existe dans la base de données.
     * @param idP
     * @param idI
     * @return true si la composition existe, false sinon.
     */
    public static boolean contient (int idP,int idI){
        int size=0;
        try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("Select count(*) from compo where idP=? and idi=?");
            stmt.setInt(1,idP);
            stmt.setInt(2,idI);
			ResultSet rs=stmt.executeQuery();
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

    /**
     * cette methode supprime un ingrédient précis dans une pizza précise.
     * @param idP identifiant de la pizza à pointer.
     * @param idI identifiant de l'ingrédient à pointer.'
     */

    public static void removeIP(int idP,int idI){
		try{
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("DELETE from compo where idP= ? and idI=?");
            stmt.setInt(1,idP);
            stmt.setInt(2,idI);
            stmt.executeUpdate();
            DS.closeConnection();
		} catch(Exception e) {
			System.out.println("ERREUR \n" + e.getMessage());
		}
	}
}
