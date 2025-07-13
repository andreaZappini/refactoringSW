package printer;

import model.TipoVisita;

public class TipoVisitaPrinter implements Printable {

    @Override
    public String print(Object object) {

        TipoVisita t = (TipoVisita) object;
        
    	StringBuffer s = new StringBuffer();
    	
    	if (!t.getGiorniDisponibili().isEmpty()) {
            s.append(t.getGiorniDisponibili().get(0));
            for (int i = 1; i < t.getGiorniDisponibili().size(); i++) {
                s.append(", ").append(t.getGiorniDisponibili().get(i));
            }
        }
    	
    	return t.getTitolo() + " [" + t.getDescrizione() + "]\n"
        + "Il punto d'incontro per questo tipo di visita sarà: " + t.getPuntoIncontro()
        + "\ne si terrà in " + t.getPeriodoAnno() + " nei giorni " + s.toString() 
        + "\na partire dalle ore " + t.getOraInizio()
        + " per una durata di " + t.getDurata() + " minuti.\n"
        + "Necessità del biglietto: " + t.getBigliettoNecessario() 
        + "\n(numero minimo partecipanti: " + t.getMinPartecipanti()
        + "; numero massimo partecipanti: " + t.getMaxPartecipanti() + ")\n"
        + "L'elenco delle guide volontarie è:\n" + FormatterRegister.printCorto(t.getElencoVolontari());
    }

        @Override
    public String printCorto(Object object) {

        TipoVisita t = (TipoVisita) object;
    	
    	return t.getTitolo();
    }
    

}
