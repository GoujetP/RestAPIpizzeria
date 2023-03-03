package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public  class DS {

    public static Connection connection = null;

    public static void getConnection() {
        try {

            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://psqlserv:5432/but2";
            String login = "mounirkhatrietu";
            String mdp = "moi";
            String urlM ="jdbc:postgresql://localhost:5432/postgres";
            String loginM ="pierre";
            String mdpM = "moi";
            Connection con = DriverManager.getConnection(urlM,loginM,mdpM);
            DS.connection = con;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            Statement statement = DS.connection.createStatement();
            return statement.executeQuery(query);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {

        try {
            DS.connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(String update) {
        try {
            Statement statement = DS.connection.createStatement();
            statement.execute(update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}