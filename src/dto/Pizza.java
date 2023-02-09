package dto;

import java.util.Map;
import java.util.TreeMap;

public class Pizza {
	protected int id;
	protected String name;
	protected String pate;
	protected double prix;
	
	private static int cpt = 0;
	
	public Pizza(String name, String pate, double prix) {
		super();
		this.id=cpt++;
		this.name = name;
		this.pate = pate;
		this.prix = prix;
		//this.ingredients = ingredients;
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
