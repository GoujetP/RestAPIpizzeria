package dto;

public class Utilisateur {
    protected int uno;



    protected String name;
    protected String adresse;
    protected String tel;
    protected String mail;
    public Utilisateur() {}
    public Utilisateur(int uno, String name, String adresse, String tel, String mail) {
        this.uno = uno;
        this.name = name;
        this.adresse = adresse;
        this.tel = tel;
        this.mail = mail;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
