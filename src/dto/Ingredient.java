package dto;

public class Ingredient {
    private int id;
    private String name;
    private double price;

    public Ingredient() {
        super();
    }



    public Ingredient(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price=price;
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
    public double getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Ingredient [id=" + id + ", name=" + name +", price=" +price+" ]";
    }

}
