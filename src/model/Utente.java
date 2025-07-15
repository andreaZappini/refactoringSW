package model;
public abstract class Utente {
    
    private String username;
    private String password;
    private boolean primoAccesso;


    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
        this.primoAccesso = true;
    }

    public Utente(String username, String password, boolean primoAccesso) {
        this.username = username;
        this.password = password;
        this.primoAccesso = primoAccesso;
    }
    
    public boolean getPrimoAccesso() {
        return this.primoAccesso;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String psw){
        this.password = psw;
    }

    public boolean controllaPassword(String psw){
        return this.password.equals(psw);
    }

    @Override
    public String toString() {
        return this.username;
    }

    public void setPrimoAccesso() {
        this.primoAccesso = false;
    }
}
