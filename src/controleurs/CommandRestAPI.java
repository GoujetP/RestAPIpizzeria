package controleurs;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import dao.IngredientDAO;
import dao.OrdersDAO;
import dao.UserDAO;
import dto.Orders;
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
        String token = req.getParameter("token");
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
}
