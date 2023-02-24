package controleurs;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DS;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/token")
public class GenererToken extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        int userid=UserDAO.connect(username,password);
        if(userid!=0){
            out.println(genereTOKEN(userid));
        }else{
            out.println(""+userid+" "+username+" "+password);
        };
    }

    public String genereTOKEN(int idU){
        String token=java.util.UUID.randomUUID().toString();
        try {
            DS.getConnection();
            PreparedStatement stmt=DS.connection.prepareStatement("update users set token=? where id=?;");
            stmt.setString(1,token);
            stmt.setInt(2,idU);
            stmt.executeUpdate();
            DS.closeConnection();
            System.out.println("All is ok!");
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
            return "users inconnu";
        }
            return token;
    }
}
