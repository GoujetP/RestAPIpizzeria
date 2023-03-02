package dto;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
	protected int id;
	protected String name;
	protected String pate;
	protected double price;
	protected List<Ingredient>compo=new ArrayList<Ingredient>();

	public Pizza(){super();}
	public Pizza(int id, String name, String pate, double price, List<Ingredient>compoP) {
		super();
		this.id=id;
		this.name = name;
		this.pate = pate;
		this.price = price;
		this.compo=compoP;		//this.ingredients = ingredients;
	}
	public Pizza(int id,String name, String pate, double price) {
		super();
		this.id=id;
		this.name = name;
		this.pate = pate;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Ingredient> getCompo() {
		return compo;
	}



	public void setCompo(ArrayList<Ingredient> compoFinal) {
		this.compo=compoFinal;
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

	@Override
	public String toString() {
		return "Pizza{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pate='" + pate + '\'' +
				", price=" + price +
				", compo=" + compo +
				'}';
	}
}
