package dto;

import java.util.Date;

public class Orders {
    private int orderId;
    private User user;
    private Pizza pizza;
    private int qty ;
    private Date date;
    private boolean finish;

    public Orders(int orderId, dto.User user, dto.Pizza pizza, int qty, Date date, boolean finish) {
        this.orderId=orderId;
        this.user = user;
        this.pizza = pizza;
        this.qty = qty;
        this.date = date;
        this.finish = finish;
    }

    public dto.User getUser() {
        return user;
    }

    public void setUser(dto.User user) {
        this.user = user;
    }

    public dto.Pizza getPizza() {
        return pizza;
    }

    public void setPizza(dto.Pizza pizza) {
        this.pizza = pizza;
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

    public Orders() {

    }
}
