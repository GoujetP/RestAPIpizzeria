package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAO;
import dao.PizzaDAO;
import dto.Ingredient;
import dto.Pizza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/pizzas/*")
public class PizzaRestAPI extends HttpServlet {
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Pizza> model = PizzaDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(model);
            out.print(jsonstring);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 && splits.length != 3 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        if (splits.length==3) {
        	if (splits[2].equals("name")) {
        		out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.valueOf(id)).getName()));
        		return;
        	}
        	else {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        	}
        }

        
        if (PizzaDAO.findById(Integer.valueOf(id)) == null  ) {
        	res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.valueOf(id))));
        
        
        
        
        return;
    }
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
        	data.append(line);
        }
        
        Pizza p = objectMapper.readValue(data.toString(), Pizza.class);
        System.out.println(data);
        if(IngredientDAO.findById(p.getId()) != null){
            res.sendError(HttpServletResponse.SC_CONFLICT); 
            return;
        }
        PizzaDAO.save(p);
    }

    /*@Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        Pizza p = objectMapper.readValue(data.toString(), Pizza.class);
        System.out.println(data);
        if(IngredientDAO.findById(p.getId()) != null){
            res.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        PizzaDAO.save(p);
    }
*/
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("requete: " +req.toString());
        //doGet(req, res);
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        System.out.println("PathInfo :" +info );
        if (info == null || info.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");


        String idP = splits[1];
        System.out.println("Splits : "+Arrays.toString(splits));
        if (PizzaDAO.findById(Integer.parseInt(idP)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (splits.length == 2) {
            PizzaDAO.remove(Integer.parseInt(idP));
            return;
        }
        if (splits.length != 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (splits.length == 3) {
            String idI = splits[2];
            if (PizzaDAO.contient(Integer.parseInt(idP), Integer.parseInt(idI))) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            PizzaDAO.removeIP(Integer.valueOf(idP), Integer.valueOf(idI));

        }
    }
}