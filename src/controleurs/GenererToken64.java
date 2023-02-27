package controleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GenererToken64 {
    
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        
        if(UserDAO.connect(username, password)){
            out.println(genereTOKEN64(username,password));
        }else{
            out.println("false"+" "+username+" "+password);
        };
    }
    public String genereTOKEN64(String login,String password){
        
        try {
           String token=java.util.Base64.getEncoder().encodeToString((login+":"+password).getBytes());
            System.out.println("All is ok!");
            return token;
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
            return "echec creation token";
        }
            
    }
}
