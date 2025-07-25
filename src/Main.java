import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import model.DatiCondivisi;
import model.GestioneTempo;
import model.GestoreVisite;
import model.RipristinoDati;
import printer.FormatterSetup;
import view.CLI;
import view.IView;

public class Main {
    
    public static void main(String[] args) throws Exception{    

        FormatterSetup.setup();
        boolean primaConfigurazione = RipristinoDati.datiRipristino();
        if(primaConfigurazione)
            DatiCondivisi.setDataUltimaEsecuzione(LocalDate.of(2025, 7, 21));
        else
            RipristinoDati.datiCondivisi();

        GestoreVisite.getInstance();
        GestioneTempo.getInstance();




        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String oggi = GestioneTempo.getInstance().getDataCorrente().format(formatter);
        System.out.println("Data odierna: " + oggi);

        GestioneTempo.getInstance().buchiTemporali();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
        GestioneTempo.getInstance().passaggioTempo();
        GestoreVisite.getInstance().aggiornaStati();
        GestoreVisite.getInstance().rimuoviVisitePassateFruitore();
        }, 0, 1, TimeUnit.SECONDS);
        

        IView view = new CLI();
        Controller controller = new Controller(view);
        controller.start(primaConfigurazione);
        
        scheduler.shutdown();
    }    
}


