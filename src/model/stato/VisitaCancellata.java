package model.stato;

import model.DatiCondivisi;
import model.Fruitore;
import model.Visita;

public class VisitaCancellata implements StatiVisita{

    @Override
    public void gestisciTransizione(Visita visita) {
        DatiCondivisi.getVisite().getElementByKey("0").getVisite().rimuovi(visita);
    }

    @Override
    public String toString() {
    
        return "CANCELLATA";
    }
    
    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        throw new IllegalStateException("Non è possibile prenotare una visita cancellata");

    }

    @Override
    public boolean isDisponibile() {
        return true;
    }

}
