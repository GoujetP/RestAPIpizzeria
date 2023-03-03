package controleurs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import dao.*;
import dto.Ingredient;
import dto.Orders;
import dto.Pizza;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/commandes/*")
public class CommandRestAPI extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String info = req.getPathInfo();
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer")){
            res.sendError(999);
            return;
        }
        String token = authorization.substring("Bearer".length()).trim();
        if (token==null || !UserDAO.checkToken(token)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;

        } else {
            Collection<Orders> models;
            if (info == null || info.equals("/")) {
                models = OrdersDAO.findByStatus(false);
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
                    out.print(objectMapper.writeValueAsString(OrdersDAO.prixfinal(Integer.valueOf(id))));//a modifier mettre map(Pizza,QTY) dans orders
                    return;
                } else {
                    res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            }
            if (OrdersDAO.findById(Integer.parseInt(id)) == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            models = OrdersDAO.findById(Integer.parseInt(id));
            out.print(objectMapper.writeValueAsString(models));


            return;
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String info = req.getPathInfo();
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line ="";

        if (info == null || info.equals("/")) {
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
            JsonNode nodeOrder= objectMapper.readTree(data.toString());
            int orderid=OrdersDAO.getOrderId(nodeOrder);
            int qty=OrdersDAO.getQty(nodeOrder);
            boolean finish = OrdersDAO.getFinish(nodeOrder);
            String dateString = OrdersDAO.getDate(nodeOrder);
            String hoursString=OrdersDAO.getHours(nodeOrder);
            int userid = OrdersDAO.getUserId(nodeOrder);
            User user = UserDAO.findById(userid);
            List<Pizza> pizza = OrdersDAO.getListPizza(nodeOrder);


            Orders order = new Orders(orderid,user,pizza,qty, LocalDate.parse(dateString), LocalTime.parse(hoursString),finish);
            OrdersDAO.save(order);
        }
    }
}
