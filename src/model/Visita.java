package model;

import java.time.LocalDate;

import model.stato.VisitaProponibile;
import model.stato.StatiVisita;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Visita {
    
    private StatiVisita stato;
    LocalDate dataVisita;
    private TipoVisita tipo;
    private HashMap<Fruitore, Prenotazione> prenotazioni; 
    private int iscritti;

    public Visita(LocalDate dataVisita, TipoVisita tipo) {
        this.stato = new VisitaProponibile();
        this.dataVisita = dataVisita;   
        this.tipo = tipo;
        this.prenotazioni = new HashMap<>();
        this.iscritti = 0;
    }

    public Visita(LocalDate dataVisita, TipoVisita tipo, StatiVisita stato, HashMap<Fruitore, Prenotazione> prenotazioni, int iscritti) {
        this.stato = stato;
        this.dataVisita = dataVisita;   
        this.tipo = tipo;
        this.prenotazioni = prenotazioni;
        this.iscritti = iscritti;
    }  
    
    // public Volontario getVolontario() {
    //     return tipo.getVolontario();
    // }

    public StatiVisita getStato() {
        return stato;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public int getMaxPartecipanti() {
        return tipo.getMaxPartecipanti();
    }

    public int getMinPartecipanti() {
        return tipo.getMinPartecipanti();
    }

    public int getIscritti() {
        return iscritti;
    }

    public TipoVisita getTipo() {
        return tipo;
    }

    public void setStato(StatiVisita stato) {
        this.stato = stato;
    }

    public void aggiornaStato() {
        stato.gestisciTransizione(this);
    }

    public HashMap<Fruitore, Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public List<Prenotazione> getPrenotazioniPerFruitore(Fruitore fruitore) {
        List<Prenotazione> res = new ArrayList<>();
        for(Prenotazione p : prenotazioni.values()) {
            if(p.getFruitore().equals(fruitore)) {
                res.add(p);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return this.tipo.toString() + "-" + this.dataVisita.toString();
    }

    public void aggiungiPrenotazione(Fruitore f, Prenotazione prenotazione) {
        int numPersone = prenotazione.getNumPartecipanti();

        if(numPersone <= 0)
            throw new IllegalArgumentException("Numero di partecipanti non valido");

        if(this.iscritti + numPersone <= this.tipo.getMaxPartecipanti()){
            iscritti += numPersone;
            try{
                prenotazioni.put(f,prenotazione);
            }catch(IllegalArgumentException e){
                throw new IllegalArgumentException("Prenotazione già esistente");
            }
            stato.gestisciTransizione(this);
        }else
            throw new IllegalArgumentException("Numero di iscritti superato");
    }

    public void rimuoviPrenotazione(Fruitore fruitore, String id) {
        boolean removed = false;
        Prenotazione p = prenotazioni.get(fruitore);
        if(p.CodiceEquals(id)) {
            iscritti -= p.getNumPartecipanti();
            prenotazioni.remove(fruitore);
            removed = true;
        } else {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
        if(removed) {
            stato.gestisciTransizione(this);
        }else
            throw new IllegalArgumentException("Non sei iscritto a questa visita");
    }
}