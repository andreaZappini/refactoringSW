package printer;

import model.Elenco;
import model.Luogo;
import model.TipoVisita;
import model.Utente;
import model.Visita;
public class FormatterSetup {
    public static void setup(){
        // FormatterRegister.registerPrinter(Volontario.class, new VolontarioPrinter());
        // FormatterRegister.registerPrinter(Configuratore.class, new ConfiguratorePrinter());
        // FormatterRegister.registerPrinter(Fruitore.class, new FruitorePrinter());
        FormatterRegister.registerPrinter(Utente.class, new UtentePrinter());
        FormatterRegister.registerPrinter(Visita.class, new VisitaPrinter());
        FormatterRegister.registerPrinter(Luogo.class, new LuogoPrinter());
        FormatterRegister.registerPrinter(TipoVisita.class, new TipoVisitaPrinter());
        FormatterRegister.registerPrinter(Elenco.class, new ElencoPrinter());

        
    }
}
