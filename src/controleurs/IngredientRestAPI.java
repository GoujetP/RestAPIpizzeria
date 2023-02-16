package controleurs;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



import com.fasterxml.jackson.databind.ObjectMapper;

import dao.IngredientDAO;
import dto.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@WebServlet("/ingredients/*")
public class IngredientRestAPI extends HttpServlet {
	private Map<Integer, Ingredient> ingredients ;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            Collection<Ingredient> models = IngredientDAO.findAll();
            String jsonstring = objectMapper.writeValueAsString(models);
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
        		out.print(objectMapper.writeValueAsString(IngredientDAO.findById(Integer.valueOf(id)).getName()));
        		return;
        	}
        	else {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        	}
        }

        
        if (IngredientDAO.findById(Integer.valueOf(id)) == null  ) {
        	res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(IngredientDAO.findById(Integer.valueOf(id))));
        
        
        
        
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
        
        Ingredient i = objectMapper.readValue(data.toString(), Ingredient.class);
        System.out.println(data);
        if(IngredientDAO.findById(i.getId()) != null){
            res.sendError(HttpServletResponse.SC_CONFLICT); 
            return;
        }
        IngredientDAO.save(i);
    }
	
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
        	res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        if (IngredientDAO.findById(Integer.valueOf(id)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        IngredientDAO.remove(Integer.parseInt(id));
        return;
	}
    
        
}