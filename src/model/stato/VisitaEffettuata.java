package model.stato;

import model.Fruitore;
import model.Visita;

public class VisitaEffettuata implements StatiVisita {

    @Override
    public String toString() {

        return "EFFETTUATA";
    }
        
    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        throw new IllegalStateException("Non è possibile prenotare una visita già effettuata");
    }
}
