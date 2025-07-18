package printer;

import java.util.HashMap;
import java.util.Map;

import model.Configuratore;
import model.Fruitore;
import model.Utente;
import model.Volontario;

public class UtentePrinter implements Printable {

    private final Map<Class<? extends Utente>, Printable> printers = new HashMap<>();

    public UtentePrinter() {
        printers.put(Volontario.class, new VolontarioPrinter());
        printers.put(Configuratore.class, new ConfiguratorePrinter());
        printers.put(Fruitore.class, new FruitorePrinter());
    }

    @Override
    public String print(Object object) {
        Printable printer = printers.get(object.getClass());
        if (printer != null) 
            return printer.print(object);
        
        throw new UnsupportedOperationException("classe non valida per il metodo 'print'");
    }
    
}
