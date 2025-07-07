package model.stato;

import model.DatiCondivisi;
import model.Fruitore;
import model.GestioneTempo;
import model.Visita;

public class VisitaProposta implements StatiVisita {

    @Override
    public void gestisciTransizione(Visita visita) {
        if(GestioneTempo.getInstance().getDataCorrente().equals(visita.getDataVisita().minusDays(3))){

            if(visita.getIscritti() < visita.getMinPartecipanti())
                visita.setStato(new VisitaCancellata());
            
            else
                visita.setStato(new VisitaConfermata());
        }
        if(visita.getMaxPartecipanti() == visita.getIscritti())
            visita.setStato(new VisitaCompleta());
    }

    @Override
    public String toString() {
       
        return "PROPOSTA";
    }

    @Override
    public void prenota(Visita visita, Fruitore fruitore, int numPersone){
        if(numPersone <= 0 || numPersone > DatiCondivisi.getNumeroMassimoIscrittiFruitore())
            throw new IllegalArgumentException("Numero di persone non valido");
        
        visita.aggiungiIscrizione(fruitore, numPersone);

        if(visita.getIscritti() == visita.getMaxPartecipanti()) {
            visita.setStato(new VisitaCompleta());
        }
    }

    @Override
    public boolean isPrenotabile() {
        return true;
    }

    @Override
    public boolean isDisponibile() {
        return true;
    }
}
