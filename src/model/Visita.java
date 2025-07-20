package model;

import java.time.LocalDate;
import java.util.HashMap;

import model.stato.VisitaProponibile;
import model.stato.StatiVisita;

public class Visita {
    
    private StatiVisita stato;
    LocalDate dataVisita;
    private TipoVisita tipo;
    private HashMap<Fruitore, Integer> iscrizioni; 
    private int iscritti;

    public Visita(LocalDate dataVisita, TipoVisita tipo) {
        this.stato = new VisitaProponibile();
        this.dataVisita = dataVisita;   
        this.tipo = tipo;
        this.iscrizioni = new HashMap<Fruitore, Integer>();
        this.iscritti = 0;
    }

    public Visita(LocalDate dataVisita, TipoVisita tipo, StatiVisita stato, HashMap<Fruitore, Integer> iscrizioni, int iscritti) {
        this.stato = stato;
        this.dataVisita = dataVisita;   
        this.tipo = tipo;
        this.iscrizioni = iscrizioni;
        this.iscritti = iscritti;
    }   

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

    public HashMap<Fruitore, Integer> getIscrizioni() {
        return iscrizioni;
    }

    @Override
    public String toString() {
        return this.tipo.toString() + "-" + this.dataVisita.toString();
    }

    public void aggiungiIscrizione(Fruitore fruitore, int numPersone) {

        if(numPersone <= 0)
            throw new IllegalArgumentException("Numero di partecipanti non valido");

        if(this.iscritti + numPersone <= this.tipo.getMaxPartecipanti()){
            iscritti += numPersone;
            if(iscrizioni.containsKey(fruitore)){
                int numPersonePrecedenti = iscrizioni.get(fruitore);
                iscrizioni.put(fruitore, numPersonePrecedenti + numPersone);
            } else {
                iscrizioni.put(fruitore, numPersone);
            }
            stato.gestisciTransizione(this);
        }else
            throw new IllegalArgumentException("Numero di iscritti superato");
    }

    public void rimuoviPrenotazione(Fruitore fruitore) {
        if(iscrizioni.containsKey(fruitore)){
            int numPersonePrecedenti = iscrizioni.get(fruitore);
            iscritti -= numPersonePrecedenti;
            iscrizioni.remove(fruitore);
            stato.gestisciTransizione(this);
        }else
            throw new IllegalArgumentException("Non sei iscritto a questa visita");
    }
}