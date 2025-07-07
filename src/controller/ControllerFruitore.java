package controller;
import view.CLI;


import model.*;
import printer.FormatterRegister;

    public class ControllerFruitore {

        private static final String AZIONI_FRUITORE_ACCCESSO =  "\n----------------------------------------------------------------------------------------------"
                + "\nBenvenuto Fruitore! Scegli una delle seguenti alternative: \n\n" +
                "1. Accedi\n" +
                "2. Registrati\n" +
                "3. Esci\n" +
                "--> ";
        private static final String AZIONI_FRUITORE =  "\n----------------------------------------------------------------------------------------------"
                + "\nBenvenuto Fruitore! Scegli una delle seguenti alternative: \n\n" +
                "1. visualizza piano visite\n" +
                "2. Prenota visita\n" +
                "3. Visualizza prenotazioni\n" +
                "4. Annulla prenotazione\n" +
                "5. Esci\n" +
                "--> ";
        public static void start() throws Exception{
            boolean continua = true;
            while(continua){
                int scelta = CLI.sceltaInt(AZIONI_FRUITORE_ACCCESSO);
                continua = loginFruitore(scelta);
            }
        }

        private static boolean loginFruitore(int scelta) throws Exception {
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

        private static void login() throws Exception {
            String[] datiUtente = CLI.login();
            String username = datiUtente[0];
            String password = datiUtente[1];

            Fruitore f = (Fruitore) DatiCondivisi.getElencoUtenti().getElementByKey(username);
            if(f == null || !(f instanceof Fruitore))
                CLI.stampaMessaggio("utente non trovato");
            
            else if(!f.controllaPassword(password))
                CLI.stampaMessaggio("password errata");

            else{
                CLI.stampaMessaggio("accesso consentito");
                azioniPrenotazione(f);
            }
        }

        private static void signin(){

            String username = "";
            String password_1 = "";
            String password_2 = "";
            do{
                String[] datiUtente = CLI.signin();
                username = datiUtente[0];
                password_1 = datiUtente[1];
                password_2 = datiUtente[2];
            }while(!GestoreFruitori.getInstance().verificaPSW(password_1, password_2));
            try{

                if(DatiCondivisi.getElencoUtenti().getElementByKey(username) != null)
                CLI.stampaMessaggio("utente gia' registrato");
            }catch(IllegalArgumentException e){

                GestoreFruitori.getInstance().aggiungiFruitore(username, password_1);
            }
        }

        private static void azioniPrenotazione(Fruitore f) throws Exception {
            boolean continua = true;
            while(continua){
                int scelta = CLI.sceltaInt(AZIONI_FRUITORE);
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

        private static void visualizzaPianoVisite() {
            String pianoVisite = FormatterRegister.print(GestoreVisite.getInstance().visiteDisponibili());
            CLI.stampaMessaggio("piano visite: \n\n");    
            CLI.stampaMessaggio(pianoVisite);
        }

        private static void prenotaVisita(Fruitore f) {

            String pianoViste = FormatterRegister.print(GestoreVisite.getInstance().visitePrenotabili());
            CLI.stampaMessaggio("visite diponibili per la prenotazione: \n\n");
            CLI.stampaMessaggio(pianoViste);
            
            String[] datiPrenotazione = CLI.prenotaVisita();
            try{
                GestoreFruitori.getInstance().prenotaVisita(f, datiPrenotazione);
                CLI.stampaMessaggio("prenotazione effettuata con successo");
            }catch(IllegalArgumentException e){
                CLI.stampaMessaggio("prenotazione non riuscita" + e.getMessage());
            }
        }
        private static void visualizzaPrenotazioni(Fruitore f) {
            CLI.stampaMessaggio("visite prenotate: \n\n");
            String prenotazioni = FormatterRegister.print(f.getPrenotazioniVisite());
            CLI.stampaMessaggio(prenotazioni);
        }
        private static void annullaPrenotazione(Fruitore f) {
            CLI.stampaMessaggio(FormatterRegister.print(f.getPrenotazioniVisite()));
            String codiceVisita = CLI.sceltaString("inserire il codice della visita da annullare: ");
            try{
                GestoreFruitori.getInstance().annullaPrenotazione(f, codiceVisita);
                CLI.stampaMessaggio("annullamento effettuato con successo");
            }catch(IllegalArgumentException e){
                CLI.stampaMessaggio("annullamento non riuscito" + e.getMessage());
            }
        }
    }

