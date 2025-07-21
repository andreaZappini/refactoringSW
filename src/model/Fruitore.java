package model;

import java.util.ArrayList;

public class Fruitore extends Utente{

    private Elenco<Visita> prenotazioniVisite;

    public Fruitore(String username, String password) {
        super(username, password, false);

        this.prenotazioniVisite = new Elenco<Visita>();
    }

    public Elenco<Visita> getPrenotazioniVisite() {

        rimuoviCancellate();
        
        return prenotazioniVisite;
    }

    private void rimuoviCancellate() {

        ArrayList<Visita> daRimuovere = new ArrayList<>();
        for(Visita v : prenotazioniVisite.getElenco().values()) 
            if(v.getStato() instanceof model.stato.VisitaCancellata) 
                daRimuovere.add(v);

        for(Visita v : daRimuovere)
            prenotazioniVisite.rimuovi(v);
    }

    public void aggiungiPrenotazione(Visita visita) {
        prenotazioniVisite.aggiungi(visita);
    }

    public void rimuoviPrenotazione(Visita visita) {
        prenotazioniVisite.rimuovi(visita);
    }
}
