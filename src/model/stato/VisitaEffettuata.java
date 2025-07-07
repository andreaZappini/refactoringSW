package model.stato;

import model.Visita;

public class VisitaEffettuata implements StatiVisita {

    @Override
    public void gestisciTransizione(Visita visita) {
        // Logica per gestire la transizione a VisitaEffettuata
        // Ad esempio, potresti aggiornare lo stato della visita o eseguire altre operazioni necessarie
        throw new UnsupportedOperationException("Unimplemented method 'gestisciTransizione'");
    }

    @Override
    public void setStato(StatiVisita stato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStato'");
    }

    @Override
    public String getNomeStato() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNomeStato'");
    }
    
}
