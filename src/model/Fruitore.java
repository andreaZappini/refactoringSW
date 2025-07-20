package model;

public class Fruitore extends Utente{

    private Elenco<Visita> prenotazioniVisite;

    public Fruitore(String username, String password) {
        super(username, password, false);

        this.prenotazioniVisite = new Elenco<Visita>();
    }

    public Elenco<Visita> getPrenotazioniVisite() {
        return prenotazioniVisite;
    }

    public void aggiungiPrenotazione(Visita visita) {
        prenotazioniVisite.aggiungi(visita);
    }

    public void rimuoviPrenotazione(Visita visita) {
        prenotazioniVisite.rimuovi(visita);
    }
}
