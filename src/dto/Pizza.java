package dto;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
	protected int pno;
	protected String name;
	protected String pate;
	protected double prix;
	protected List<Ingredient> composition =new ArrayList<Ingredient>();

	public Pizza(){super();}
	public Pizza(int pno, String name, String pate, double prix, List<Ingredient>composition) {
		super();
		this.pno =pno;
		this.name = name;
		this.pate = pate;
		this.prix = prix;
		this.composition =composition;		//this.ingredients = ingredients;
	}
	public Pizza(int pno,String name, String pate, double prix) {
		super();
		this.pno =pno;
		this.name = name;
		this.pate = pate;
		this.prix = prix;
	}


	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
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

	public List<Ingredient> getComposition() {
		return composition;
	}



	public void setComposition(ArrayList<Ingredient> compoFinal) {
		this.composition =compoFinal;
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
				"id=" + pno +
				", name='" + name + '\'' +
				", pate='" + pate + '\'' +
				", price=" + prix +
				", compo=" + composition +
				'}';
	}
}
