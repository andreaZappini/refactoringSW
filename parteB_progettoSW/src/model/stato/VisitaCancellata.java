package model.stato;

import model.DatiCondivisi;

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
    public boolean isDisponibile() {
        return true;
    }

}
