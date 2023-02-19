package dto;

import java.util.Date;

public class Commande {
    private User User ;
    private Pizza Pizza ;
    private int qty ;
    private Date date;
    private boolean finish;

    public Commande(dto.User user, dto.Pizza pizza, int qty, Date date, boolean finish) {
        User = user;
        Pizza = pizza;
        this.qty = qty;
        this.date = date;
        this.finish = finish;
    }

    public dto.User getUser() {
        return User;
    }

    public void setUser(dto.User user) {
        User = user;
    }

    public dto.Pizza getPizza() {
        return Pizza;
    }

    public void setPizza(dto.Pizza pizza) {
        Pizza = pizza;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Commande() {

    }
}
