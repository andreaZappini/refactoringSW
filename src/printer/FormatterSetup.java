package printer;

import model.Elenco;
import model.Luogo;
import model.TipoVisita;
import model.Utente;
import model.Visita;
public class FormatterSetup {
    public static void setup(){
        // Register printers for each model class
        FormatterRegister.registerPrinter(Utente.class, new UtentePrinter());
        /*
         * qui chiamo il costruttore di UtentePrinter che a sua volta contiene una mappa per le classi derivate di Utente
         * in modo che gli oggetti di tipo utente usino solo il metodo print di UtentePrinter che penserà a "smistare" le classi derivate
         * in modo da stampare il tipo corretto di utente (Configuratore, Volontario, Fruitore)
         */
        FormatterRegister.registerPrinter(Visita.class, new VisitaPrinter());
        FormatterRegister.registerPrinter(Luogo.class, new LuogoPrinter());
        FormatterRegister.registerPrinter(TipoVisita.class, new TipoVisitaPrinter());
        FormatterRegister.registerPrinter(Elenco.class, new ElencoPrinter());

        
    }
}
