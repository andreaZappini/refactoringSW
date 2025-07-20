package model.stato;

import model.DatiCondivisi;
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
    public boolean isDisponibile() {
        return true;
    }
}
