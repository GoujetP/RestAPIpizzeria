package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Commande {
    private int cno;
    private Utilisateur utilisateur;
    private List<Pizza> pizza;
    private int quantite;
    private LocalDate date;
    private LocalTime heure;
    private boolean fini;


    public Commande(int cno, Utilisateur utilisateur, List<Pizza> pizza, int quantite, LocalDate date, LocalTime heure, boolean fini) {
        this.cno =cno;
        this.utilisateur = utilisateur;
        this.heure =heure;
        this.pizza = pizza;
        this.quantite = quantite;
        this.date = date;
        this.fini = fini;
    }



    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Pizza> getPizza() {
        return pizza;
    }

    public void setPizza(List<Pizza> pizza) {
        this.pizza = pizza;
    }

    public int getQuantite() {
        return quantite;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

}
