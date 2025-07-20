package printer;

import model.Prenotazione;

public class PrenotazionePrinter implements Printable {
    @Override
    public String print(Object object) {
        Prenotazione p = (Prenotazione) object;
        return "Codice prenotazione: " + p.getCodice() + " - partecipanti: " + p.getNumPartecipanti();
    }
}
