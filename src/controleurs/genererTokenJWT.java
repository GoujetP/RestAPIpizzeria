package controleurs;

import java.io.IOException;
import java.io.PrintWriter;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users/token")
public class genererTokenJWT extends HttpServlet {
    
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String username = req.getParameter("login");
        String password = req.getParameter("password");
        
        if(UserDAO.connect(username, password)){
            out.println(genererTokenJwt(username,password));
        }else{
            out.println("Non reconnu"+" "+username+" "+password);
        };
    }
    public static String genererTokenJwt(String login, String password){
        
        try {
           String token=JwtManager.createJWT(login,password);
           
            System.out.println("All is ok!");
            return token;
        } catch (Exception e) {
            System.out.println("ERREUR \n" + e.getMessage());
            return "echec creation token";
        }
            
    }


    
}
