package model;

import java.util.UUID;

public class Prenotazione {
    private final String codice;
    private final Fruitore fruitore;
    private final int numPartecipanti;

    public Prenotazione(Fruitore fruitore, int numPartecipanti) {
        this.codice = UUID.randomUUID().toString();
        this.fruitore = fruitore;
        this.numPartecipanti = numPartecipanti;
    }

    public Prenotazione(String codice, Fruitore fruitore, int numPartecipanti) {
        this.codice = codice;
        this.fruitore = fruitore;
        this.numPartecipanti = numPartecipanti;
    }

    public String getCodice() {
        return codice;
    }

    public Fruitore getFruitore() {
        return fruitore;
    }

    public int getNumPartecipanti() {
        return numPartecipanti;
    }

    @Override
    public String toString() {
        return codice;
    }
}