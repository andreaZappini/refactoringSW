package controller;
import view.IView;


import model.*;

    public class ControllerFruitore {

        private final IView view;
        private FruitoreService action;

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
            this.action = new FruitoreService(view);
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
                    Fruitore f = action.login();
                    if(f != null)
                        azioniPrenotazione();
                    break;
                case 2:
                    action.signin();
                    break;
                case 3:
                    return false;
                default:
            }
            return true;
        }

        private void azioniPrenotazione() throws Exception {
            boolean continua = true;
            while(continua){
                int scelta = view.sceltaInt(AZIONI_FRUITORE);
                switch(scelta){
                    case 1:
                        action.visualizzaPianoVisite();
                        break;
                    case 2:
                        action.prenotaVisita();
                        break;
                    case 3:
                        action.visualizzaPrenotazioni();
                        break;
                    case 4:
                        action.annullaPrenotazione();
                        break;
                    case 5:
                        continua = false;
                        break;
                    default:
                }
            }   
        }

       
    }

