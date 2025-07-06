package printer;

import model.Visita;

public class VisitaPrinter implements Printable {

    @Override
    public String print(Object object) {
        Visita visita = (Visita) object;
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo di Visita: " + visita.getTipo() + "\n");
        sb.append("Data: " + visita.getDataVisita() + "\n");
        sb.append("stato: " + visita.getStato() + "\n");
        
        return sb.toString();
    }    
}
