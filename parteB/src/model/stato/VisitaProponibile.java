package model.stato;

import model.Fruitore;
import model.Visita;

public class VisitaProponibile implements StatiVisita {

    @Override 
    public void gestisciTransizione(Visita visita) {
        visita.setStato(new VisitaProposta());
    }

    @Override
    public String toString() {

        return "PROPONIBILE";
    }

    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        throw new IllegalStateException("Non è possibile prenotare una visita proponibile");
    }
}
