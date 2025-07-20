package controller;

import model.DatiCondivisi;
import model.Fruitore;
import model.GestoreFruitori;
import model.GestoreVisite;
import model.Utente;
import model.Visita;
import model.Prenotazione;
import printer.FormatterRegister;
import view.IView;

public class FruitoreService {

    private IView view;

    private Fruitore fruitore;

    public FruitoreService(IView view) {
        this.view = view;
    }

    public Fruitore login(){
        String[] datiUtente = view.login();
        String username = datiUtente[0];
        String password = datiUtente[1];
        Utente u = null;
        try{
            u = DatiCondivisi.getElencoUtenti().getElementByKey(username);
            Fruitore f = (Fruitore) u;
            if(f == null){
                view.stampaMessaggio("utente non trovato");
                return null;
            }else if(!f.controllaPassword(password)){
                view.stampaMessaggio("password errata");
                return null;
            }else{
                view.stampaMessaggio("accesso consentito");
                fruitore = f;
                return f;
            }
        }catch(ClassCastException e){
            view.stampaMessaggio("impossibile accedere qui perchè " + username + " è un " + u.getClass().getSimpleName());
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("utente non trovato");
        }
        return null;
    }

     public void visualizzaPianoVisite() {
        String pianoVisite = FormatterRegister.print(GestoreVisite.getInstance().visiteDisponibili());
        view.stampaMessaggio("piano visite: \n\n");    
        view.stampaMessaggio(pianoVisite);
    }

    public void prenotaVisita() {

        String pianoViste = FormatterRegister.print(GestoreVisite.getInstance().visitePrenotabili());
        view.stampaMessaggio("visite diponibili per la prenotazione: \n\n");
        view.stampaMessaggio(pianoViste);
        
        String[] datiPrenotazione = view.prenotaVisita();
        try{
            String codice = GestoreFruitori.getInstance().prenotaVisita(fruitore, datiPrenotazione);
            view.stampaMessaggio("prenotazione effettuata con successo. Codice: " + codice);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("prenotazione non riuscita" + e.getMessage());
        }
    }
    
    public void visualizzaPrenotazioni() {
        view.stampaMessaggio("visite prenotate: \n\n");
        StringBuilder sb = new StringBuilder();
        for(Visita v : fruitore.getPrenotazioniVisite().getElenco().values()) {
            sb.append(FormatterRegister.print(v));
            for(Prenotazione p : v.getPrenotazioniPerFruitore(fruitore)) {
                sb.append("  Codice prenotazione: ").append(p.getCodice()).append("\n");
            }
        }
        view.stampaMessaggio(sb.toString());
    }

    public void annullaPrenotazione() {
        view.stampaMessaggio(FormatterRegister.print(fruitore.getPrenotazioniVisite()));
        String codiceVisita = view.sceltaString("inserire il codice della visita da annullare: ");
        String id = view.sceltaString("inserire il codice univoco della prenotazione da annullare: ");
        try{
            GestoreFruitori.getInstance().annullaPrenotazione(fruitore, codiceVisita, id);
            view.stampaMessaggio("annullamento effettuato con successo");
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("annullamento non riuscito: " + e.getMessage());
        }
    }

    public void signin(){

        String username = "";
        String password_1 = "";
        String password_2 = "";
        do{
            String[] datiUtente = view.signin();
            username = datiUtente[0];
            password_1 = datiUtente[1];
            password_2 = datiUtente[2];
        }while(!GestoreFruitori.getInstance().verificaPSW(password_1, password_2));
        try{

            if(DatiCondivisi.getElencoUtenti().getElementByKey(username) != null)
            view.stampaMessaggio("utente gia' registrato");
        }catch(IllegalArgumentException e){

            GestoreFruitori.getInstance().aggiungiFruitore(username, password_1);
        }
    }
    
}
