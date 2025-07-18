package test;

import static org.junit.Assert.*;

import model.*;
import org.junit.Before;
import org.junit.Test;

public class UserFactoryTest {

    @Before
    public void setUp() {
    
        DatiCondivisi.getElencoUtenti().pulisciElenco();
    }
    
    @Test
    public void testCreateConfiguratore() {
        Configuratore c = UserFactory.creaConfiguratore("Mario", "123");
        assertTrue(DatiCondivisi.getElencoUtenti().contiene("Mario"));
        assertTrue(DatiCondivisi.getElencoUtenti().getClassiUtente(Configuratore.class).contiene("Mario"));
    }

    @Test
    public void testVolontario() {
        Volontario v = UserFactory.creaVolontario("Luigi", "123");
        assertTrue(DatiCondivisi.getElencoUtenti().contiene("Luigi"));
        assertTrue(DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class).contiene("Luigi"));
    }

    @Test
    public void testFruitore() {
        Fruitore f = UserFactory.creaFruitore("Anna", "123");
        assertTrue(DatiCondivisi.getElencoUtenti().contiene("Anna"));
        assertTrue(DatiCondivisi.getElencoUtenti().getClassiUtente(Fruitore.class).contiene("Anna"));
    }
}
