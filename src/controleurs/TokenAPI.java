package controleurs;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PizzaDAO;
import dao.UserDAO;
import dto.Pizza;
import dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
@WebServlet("/users/*")
public class TokenAPI extends HttpServlet{




        public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            String info = req.getPathInfo();
            String authorization = req.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic")){
                res.sendError(999);
                return;
            }
            String token = authorization.substring("Basic".length()).trim();
            if (token==null || !UserDAO.checkToken(token)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            if (info == null || info.equals("/")) {
                Collection<User> models = UserDAO.findAll();
                String jsonstring = objectMapper.writeValueAsString(models);
                out.print(jsonstring);
                return;
            }
            String[] splits = info.split("/");
            if (splits.length != 2) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            String id = splits[1];

            if (id.equals("token")) {
                out.print(objectMapper.writeValueAsString(UserDAO.findAllToken()));
                return;
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        }
    }

