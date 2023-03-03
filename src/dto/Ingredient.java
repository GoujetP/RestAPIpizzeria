package dto;

public class Ingredient {
    private int ino;
    private String name;
    private double prix;

    public Ingredient() {
        super();
    }

    public Ingredient(int ino, String name, double prix) {
        this.ino = ino;
        this.name = name;
        this.prix = prix;
    }

    public int getIno() {
        return ino;
    }

    public void setIno(int ino) {
        this.ino = ino;
    }

    public String getName() {
        return name;
    }

    public double getPrix() {
        return prix;
    }

    public void setName(String name) {
        this.name = name;
    }

}
