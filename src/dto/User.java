package dto;

public class User {
    protected int id;



    protected String name;
    protected String adresse;
    protected String number;
    protected String mail;
    public User(int id, String name, String adresse, String number, String mail) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.number = number;
        this.mail = mail;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public User() {

    }
}
