package controleurs;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import dao.OrdersDAO;
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
        if (info == null || info.equals("/")) {
            Collection<Orders> models = OrdersDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(models);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];

        if (OrdersDAO.findById(Integer.parseInt(id)) == null  ) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(OrdersDAO.findById(Integer.parseInt(id))));




        return;
    }
}
