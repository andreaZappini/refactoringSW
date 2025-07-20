package model;

public class GestoreFruitori {
    
    private static final GestoreFruitori instance = new GestoreFruitori();

    private GestoreFruitori() {}

    public static GestoreFruitori getInstance() {
        return instance;
    }


    public void aggiungiFruitore(String nome, String password) {
        UserFactory.creaFruitore(nome, password);
    }


    public boolean verificaPSW(String p1, String p2) {
        return (p1 != null && p1.equals(p2));
    }

    public String prenotaVisita(Fruitore fruitore, String[] datiPrenotazione) {

        String codiceVisita = datiPrenotazione[0];
        int numPersone = Integer.parseInt(datiPrenotazione[1]);
        if(numPersone <= 0)
            throw new IllegalArgumentException("Numero di partecipanti non valido");
        if(numPersone > DatiCondivisi.getNumeroMassimoIscrittiFruitore())
            throw new IllegalArgumentException("Numero di persone superiore al massimo consentito");

        try{
            Visita visita = DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElementByKey(codiceVisita);
                    
            if(visita.getStato().isPrenotabile()) {
                Prenotazione p = new Prenotazione(fruitore, numPersone);
                visita.aggiungiPrenotazione(p);
                fruitore.aggiungiPrenotazione(visita);
                return p.getCodice();
            } else {
                throw new IllegalArgumentException("Visita non prenotabile");
            }
        }catch(IllegalArgumentException e){
            throw e;
        }


    }

    public void annullaPrenotazione(Fruitore f, String c, String id) {

        try{
            Visita v = DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElementByKey(c);

            if(v.getStato().isNotAnnullabile())
                throw new IllegalArgumentException("Visita non annullabile");
            
            v.rimuoviPrenotazione(f);
            f.rimuoviPrenotazione(v);
        }catch(IllegalArgumentException e){
            throw e;
        }

    }
}
