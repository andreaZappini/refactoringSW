package model.stato;

import model.DatiCondivisi;
import model.Fruitore;
import model.GestioneTempo;
import model.Visita;

public class VisitaConfermata implements StatiVisita {

    @Override
    public void gestisciTransizione(Visita visita) {
        if(visita.getDataVisita().isBefore(GestioneTempo.getInstance().getDataCorrente())) {
            visita.setStato(new VisitaEffettuata());
            DatiCondivisi.aggiungiVisitaArchivio(visita);
        }
    }

    @Override
    public String toString() {

        return "CONFERMATA";
    }
        
    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        throw new IllegalStateException("Non è possibile prenotare una visita già confermata");
    }

    @Override
    public boolean isDisponibile() {
        return true;
    }
}
