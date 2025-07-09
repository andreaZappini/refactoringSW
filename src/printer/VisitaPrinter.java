package printer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.Visita;
import model.stato.StatiVisita;
import model.stato.*;

public class VisitaPrinter implements Printable {

    private final Map<Class<? extends StatiVisita>, Function<Visita, String>> strategiaStampa = new HashMap<>();

    public VisitaPrinter(){
        strategiaStampa.put(VisitaProponibile.class, this::stampaProponibile);
        strategiaStampa.put(VisitaProposta.class, this::stampaPoposta);
        strategiaStampa.put(VisitaCompleta.class, this::stampaCompleta);
        strategiaStampa.put(VisitaConfermata.class, this::stampaConfermata);
        strategiaStampa.put(VisitaEffettuata.class, this::stampaEffettuata);
        strategiaStampa.put(VisitaCancellata.class, this::stampaCancellata);
    }

    @Override
    public String print(Object object) {
        Visita visita = (Visita) object;
    
        Function<Visita, String> stampaFunzione = strategiaStampa.get(visita.getStato().getClass());
        if (stampaFunzione != null) {
            return stampaFunzione.apply(visita);
        } else {
            throw new IllegalArgumentException("Stato di visita non supportato: " + visita.getStato().getClass().getSimpleName());
        }
    }

    private String stampaConfermata(Visita visita){
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Confermata: "+ visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");
        sb.append("Punto di incontro: " + visita.getTipo().getPuntoIncontro() + "\n");
        sb.append("Descrizione: " + visita.getTipo().getDescrizione() + "\n");
        sb.append("Numero di iscritti: " + visita.getIscritti() + "\n");
        sb.append("Biglietto necessario: " + visita.getTipo().getBigliettoNecessario() + "\n");

        return sb.toString();
    }

    private String stampaPoposta(Visita visita){
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Proposta: "+ visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");
        sb.append("Punto di incontro: " + visita.getTipo().getPuntoIncontro() + "\n");
        sb.append("Descrizione: " + visita.getTipo().getDescrizione() + "\n");
        sb.append("Numero di iscritti: " + visita.getIscritti() + "\n");
        sb.append("Biglietto necessario: " + visita.getTipo().getBigliettoNecessario() + "\n");

        return sb.toString();
    }

    private String stampaCancellata(Visita visita) {
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Cancellata: " + visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");

        return sb.toString();
    }

    private String stampaEffettuata(Visita visita) {
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Effettuata: " + visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");

        return sb.toString();
    }

    private String stampaCompleta(Visita visita) {
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Confermata: " + visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");

        return sb.toString();
    }

    private String stampaProponibile(Visita visita) {
        StringBuilder sb = new StringBuilder();
        sb.append("Visita Proponibile: " + visita.getTipo().getTitolo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");
        sb.append("stato: " + visita.getStato().toString() + "\n");

        return sb.toString();
    }
} 
