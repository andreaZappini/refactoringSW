package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import model.*;
import model.stato.*;

import org.junit.Before;
import org.junit.Test;

public class VisitaArchivioTest {

    private TipoVisita tipo;

    @Before
    public void setUp(){
        // ensure singletons are initialized
        GestoreVisite.getInstance();
        GestioneTempo.getInstance();

        DatiCondivisi.getArchivio().pulisciElenco();

        tipo = new TipoVisita(
            "Test",
            "desc",
            "punto",
            "periodo",
            new ArrayList<>(),
            10.0,
            60,
            "S",
            0,
            5
        );
    }

    @Test
    public void propostaDiventaEffettuataEArchiviata() {
        LocalDate dataPassata = GestioneTempo.getInstance().getDataCorrente().minusDays(1);
        Visita visita = new Visita(dataPassata, tipo, new VisitaConfermata(), new java.util.HashMap<>(), 0);
        visita.aggiornaStato();

        assertTrue(visita.getStato() instanceof VisitaEffettuata);
        assertTrue(DatiCondivisi.getArchivio().contiene(visita.toString()));
    }
}
