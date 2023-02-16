package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Pizza {
	protected int id;
	protected String name;
	protected String pate;
	protected double prix;
	protected List<Ingredient>compo=new ArrayList<Ingredient>();

	
	public Pizza(int id,String name, String pate, double prix,List<Ingredient>compoP) {
		super();
		this.id=id;
		this.name = name;
		this.pate = pate;
		this.prix = prix;
		this.compo=compoP;		//this.ingredients = ingredients;
	}
	public Pizza(int id,String name, String pate, double prix) {
		super();
		this.id=id;
		this.name = name;
		this.pate = pate;
		this.prix = prix;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPate() {
		return pate;
	}

	public void setPate(String pate) {
		this.pate = pate;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public List<Ingredient> getCompo() {
		return compo;
	}

	public void setCompo(List<Ingredient> compo) {
		this.compo = compo;
	}
/*public Map<Integer, Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Map<Integer, Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	

	*
	*
	*/
	
	
}
