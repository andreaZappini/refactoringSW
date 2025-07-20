package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import model.*;

import org.junit.Before;
import org.junit.Test;

public class VisitaAnnullaPrenotazioneTest {
    private Visita visita;
    private Fruitore fruitore;
    private String codice;

    @Before
    public void setUp() {
        // ensure singletons initialize data structures
        GestoreVisite.getInstance();
        GestioneTempo.getInstance();

        // clean visits of current month
        DatiCondivisi.getVisite().getElementByKey("0").getVisite().pulisciElenco();

        TipoVisita tipo = new TipoVisita(
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
        visita = new Visita(LocalDate.now(), tipo);
        DatiCondivisi.getVisite().getElementByKey("0").getVisite().aggiungi(visita);

        fruitore = new Fruitore("Mario", "pwd");
        Prenotazione p = new Prenotazione(fruitore, 1);
        visita.aggiungiPrenotazione(fruitore, p);
        fruitore.aggiungiPrenotazione(visita);
        codice = p.getCodice();
    }

    @Test
    public void annullaPrenotazioneSuccess() {
        GestoreFruitori.getInstance().annullaPrenotazione(fruitore, visita.toString(), codice);
        assertFalse(visita.getPrenotazioni().containsKey(fruitore));
        assertEquals(0, visita.getIscritti());
    }

    @Test(expected = IllegalArgumentException.class)
    public void annullaPrenotazioneIdErrata() {
        GestoreFruitori.getInstance().annullaPrenotazione(fruitore, visita.toString(), "wrong-id");
    }
}