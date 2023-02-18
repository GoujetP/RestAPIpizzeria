package controleurs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.databind.JsonNode;
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
            Collection<Pizza> models = PizzaDAO.findAll();
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
        		out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt(id)).getName()));
        		return;
        	} else if(splits[2].equals("prixFinal")) {
                    out.print(objectMapper.writeValueAsString(PizzaDAO.getFinalPrice(Integer.parseInt(id))));
                    return;
            } else {
        		res.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
        	}
        }

        
        if (PizzaDAO.findById(Integer.parseInt(id)) == null  ) {
        	res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        out.print(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt(id))));
        
        
        
        
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
        if (info == null || info.equals("/")) {
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            String[] pizzaSplitCompo = data.toString().split("compo");
            System.out.println("Ingredients --> "+pizzaSplitCompo[1].substring(2,pizzaSplitCompo[1].length()-1));
            System.out.println("Pizza --> "+pizzaSplitCompo[0].substring(0,pizzaSplitCompo[0].length()-2)+"}");
            Ingredient[] compoIngredient = objectMapper.readValue(pizzaSplitCompo[1].substring(2,pizzaSplitCompo[1].length()-1) , Ingredient[].class);
            Pizza p = objectMapper.readValue(pizzaSplitCompo[0].substring(0,pizzaSplitCompo[0].length()-2)+"}", Pizza.class);
            ArrayList<Ingredient> compoFinal = new ArrayList<Ingredient>();
            Collections.addAll(compoFinal, compoIngredient);
            p.setCompo(compoFinal);
            if(IngredientDAO.findById(p.getId()) != null){
                res.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }
            PizzaDAO.save(p);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length != 2 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];
        Pizza p = objectMapper.readValue(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt(id))), Pizza.class);
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        Ingredient i = objectMapper.readValue(data.toString(), Ingredient.class);
        PizzaDAO.addIngredient(p,i);
        return ;



    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("DELETE:"  + req.toString());
        res.setContentType("application/json;charset=UTF-8");

        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
        	res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = info.split("/");
        if (splits.length > 3) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        String idP = splits[1];

        if (PizzaDAO.findById(Integer.parseInt(idP)) == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if(splits.length==3){
            String idI = splits[2];
            if(PizzaDAO.contient(Integer.parseInt(idP), Integer.parseInt(idI))){
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        PizzaDAO.removeIP(Integer.parseInt(idP), Integer.parseInt(idI));

        }
        if(splits.length==2){
        PizzaDAO.remove(Integer.parseInt(idP));
        return;
        }

	}


    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, res);
        } else {
            super.service(req, res);
        }
    }
    public void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        String info = req.getPathInfo();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line ="";
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();

        String[] splits = info.split("/");
        if (splits.length != 2 ) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id = splits[1];

        while ((line = reader.readLine()) != null) {
            data.append(line);
        }


        JsonNode jsonNodePizzaBasic = objectMapper.readTree(objectMapper.writeValueAsString(PizzaDAO.findById(Integer.parseInt(id))));
        JsonNode jsonNodePizzaModif = objectMapper.readTree(data.toString());
        JsonNode mergePatch = PizzaDAO.doMergeWithJackson(jsonNodePizzaBasic, jsonNodePizzaModif);
        String pizzaFinalModif = objectMapper.writeValueAsString(mergePatch);
        Pizza p = objectMapper.readValue(pizzaFinalModif, Pizza.class);
        PizzaDAO.remove(Integer.parseInt(id));
        p.setPrix(p.getPrix()+2.0);
        PizzaDAO.save(p);
    }




}
