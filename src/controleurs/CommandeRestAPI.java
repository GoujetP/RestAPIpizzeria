package controleurs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import dao.*;
import dto.Commande;
import dto.Pizza;
import dto.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandeRestAPI extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String info = req.getPathInfo();
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Basic")){
            res.sendError(999);
            return;
        }
        Collection<Commande> models;
        if (info == null || info.equals("/")) {
            models = CommandeDAO.findByStatus(false);
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 && splits.length != 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        if (splits.length == 3) {
            if (splits[2].equals("prixfinal")) {
                out.print(objectMapper.writeValueAsString(CommandeDAO.prixfinal(Integer.parseInt(id))));//a modifier mettre map(Pizza,QTY) dans orders
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            return;
        }
        if (CommandeDAO.findById(Integer.parseInt(id)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        models = CommandeDAO.findById(Integer.parseInt(id));
        out.print(objectMapper.writeValueAsString(models));
        return;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line ="";
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Basic")){
            res.sendError(999);
            return;
        }
        if (info == null || info.equals("/")) {
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            JsonNode nodeOrder= objectMapper.readTree(data.toString());
            int orderid= CommandeDAO.getOrderId(nodeOrder);
            int qty= CommandeDAO.getQuantite(nodeOrder);
            boolean finish = CommandeDAO.getFini(nodeOrder);
            String dateString = CommandeDAO.getDate(nodeOrder);
            String hoursString= CommandeDAO.getHeure(nodeOrder);
            int userid = CommandeDAO.getUserId(nodeOrder);
            Utilisateur user = UtilisateurDAO.findById(userid);
            List<Pizza> pizza = CommandeDAO.getListPizza(nodeOrder);


            Commande order = new Commande(orderid,user,pizza,qty, LocalDate.parse(dateString), LocalTime.parse(hoursString),finish);
            CommandeDAO.save(order);
        }
    }
}
