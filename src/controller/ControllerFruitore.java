package controller;
import view.IView;


import model.*;
import printer.FormatterRegister;

    public class ControllerFruitore {

        private final IView view;

        private final String AZIONI_FRUITORE_ACCCESSO =  "\n----------------------------------------------------------------------------------------------"
                + "\nBenvenuto Fruitore! Scegli una delle seguenti alternative: \n\n" +
                "1. Accedi\n" +
                "2. Registrati\n" +
                "3. Esci\n" +
                "--> ";
        private final String AZIONI_FRUITORE =  "\n----------------------------------------------------------------------------------------------"
                + "\nBenvenuto Fruitore! Scegli una delle seguenti alternative: \n\n" +
                "1. visualizza piano visite\n" +
                "2. Prenota visita\n" +
                "3. Visualizza prenotazioni\n" +
                "4. Annulla prenotazione\n" +
                "5. Esci\n" +
                "--> ";

        public ControllerFruitore(IView view) {
            this.view = view;
        }

        public void start() throws Exception{
            boolean continua = true;
            while(continua){
                int scelta = view.sceltaInt(AZIONI_FRUITORE_ACCCESSO);
                continua = loginFruitore(scelta);
            }
        }

        private boolean loginFruitore(int scelta) throws Exception {
            switch(scelta){
                case 1:
                    login();
                    break;
                case 2:
                    signin();
                    break;
                case 3:
                    return false;
                default:
            }
            return true;
        }

        private void login() throws Exception {
            String[] datiUtente = view.login();
            String username = datiUtente[0];
            String password = datiUtente[1];

            Fruitore f = (Fruitore) DatiCondivisi.getElencoUtenti().getElementByKey(username);
            if(f == null || !(f instanceof Fruitore))
                view.stampaMessaggio("utente non trovato");
            
            else if(!f.controllaPassword(password))
                view.stampaMessaggio("password errata");

            else{
                view.stampaMessaggio("accesso consentito");
                azioniPrenotazione(f);
            }
        }

        private void signin(){

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

        private void azioniPrenotazione(Fruitore f) throws Exception {
            boolean continua = true;
            while(continua){
                int scelta = view.sceltaInt(AZIONI_FRUITORE);
                switch(scelta){
                    case 1:
                        visualizzaPianoVisite();
                        break;
                    case 2:
                        prenotaVisita(f);
                        break;
                    case 3:
                        visualizzaPrenotazioni(f);
                        break;
                    case 4:
                        annullaPrenotazione(f);
                        break;
                    case 5:
                        continua = false;
                        break;
                    default:
                }
            }   
        }

        private void visualizzaPianoVisite() {
            String pianoVisite = FormatterRegister.print(GestoreVisite.getInstance().visiteDisponibili());
            view.stampaMessaggio("piano visite: \n\n");    
            view.stampaMessaggio(pianoVisite);
        }

        private void prenotaVisita(Fruitore f) {

            String pianoViste = FormatterRegister.print(GestoreVisite.getInstance().visitePrenotabili());
            view.stampaMessaggio("visite diponibili per la prenotazione: \n\n");
            view.stampaMessaggio(pianoViste);
            
            String[] datiPrenotazione = view.prenotaVisita();
            try{
                GestoreFruitori.getInstance().prenotaVisita(f, datiPrenotazione);
                view.stampaMessaggio("prenotazione effettuata con successo");
            }catch(IllegalArgumentException e){
                view.stampaMessaggio("prenotazione non riuscita" + e.getMessage());
            }
        }
        private void visualizzaPrenotazioni(Fruitore f) {
            view.stampaMessaggio("visite prenotate: \n\n");
            String prenotazioni = FormatterRegister.print(f.getPrenotazioniVisite());
            view.stampaMessaggio(prenotazioni);
        }
        private void annullaPrenotazione(Fruitore f) {
            view.stampaMessaggio(FormatterRegister.print(f.getPrenotazioniVisite()));
            String codiceVisita = view.sceltaString("inserire il codice della visita da annullare: ");
            try{
                GestoreFruitori.getInstance().annullaPrenotazione(f, codiceVisita);
                view.stampaMessaggio("annullamento effettuato con successo");
            }catch(IllegalArgumentException e){
                view.stampaMessaggio("annullamento non riuscito" + e.getMessage());
            }
        }
    }

