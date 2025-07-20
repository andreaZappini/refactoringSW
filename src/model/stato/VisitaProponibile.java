package model.stato;

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
}
