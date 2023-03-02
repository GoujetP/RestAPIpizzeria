package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Orders {
    private int orderId;
    private User user;
    private List<Pizza> pizza;
    private int qty ;
    private LocalDate date;
    private LocalTime hours;
    private boolean finish;


    public Orders(int orderId, dto.User user, List<Pizza> pizza, int qty, LocalDate date,LocalTime hours, boolean finish) {
        this.orderId=orderId;
        this.user = user;
        this.hours=hours;
        this.pizza = pizza;
        this.qty = qty;
        this.date = date;
        this.finish = finish;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Pizza> getPizza() {
        return pizza;
    }

    public void setPizza(List<Pizza> pizza) {
        this.pizza = pizza;
    }

    public int getQty() {
        return qty;
    }

    public LocalTime getHours() {
        return hours;
    }

    public void setHours(LocalTime hours) {
        this.hours = hours;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
