package model.stato;

import model.Fruitore;
import model.GestioneTempo;
import model.Visita;

public class VisitaCompleta implements StatiVisita {

    @Override
    public void gestisciTransizione(Visita visita) {
        if(GestioneTempo.getInstance().getDataCorrente().equals(visita.getDataVisita().minusDays(3)))
            visita.setStato(new VisitaConfermata());
        else if(visita.getIscritti() < visita.getMinPartecipanti())
            visita.setStato(new VisitaProposta());
    }

    @Override
    public String toString() {
       
        return "COMPLETA";
    }

    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        throw new IllegalStateException("Non è possibile prenotare una visita completa");

    }
    
}
