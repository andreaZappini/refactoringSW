package printer;

import model.TipoVisita;
import model.Volontario;

public class VolontarioPrinter implements Printable {

    @Override
    public String print(Object object) {
        Volontario v = (Volontario) object;
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: " + v.getUsername() + "\n");
        sb.append("tipi di visita associati:\n");
        for(TipoVisita tipo : v.getElencoTipiVisita().getElenco().values()) {
            sb.append(" - " + tipo + "\n");
        }
        return sb.toString();
    }

    // Additional methods can be added here if needed
    
}
