package controller;

import model.*;
import view.IView;

public class ControllerVolontario {

    private final IView view;
    private VolontarioService action;
    
    private static final String AZIONI_VOLONTARIO =  "\n----------------------------------------------------------------------------------------------"
                + "\nBenvenuto Volontario! Scegli una delle seguenti alternative: \n\n" +
                "1. Visualizzare tutti i tipi di visita a cui sei associato\n" +
                "2. Esprimere le disponibilità in termini di date\n" +
                "3. Logout\n" +
                "--> ";


    public ControllerVolontario(Volontario volontario, IView view) {
        this.view = view;
        this.action = new VolontarioService(volontario, view);
    }

    public void start(){
        boolean continua = true;
        while(continua) {
            int sccelta = view.sceltaInt(AZIONI_VOLONTARIO);
            continua = azioniVolontario(sccelta);
        }
    }

    private boolean azioniVolontario(int scelta) {
        switch(scelta) {
            case 1:
                action.visualizzaTipiVisita();
                break;
            case 2:
                action.esprimiDisponibilita();
                break;
            case 3:
                action.visualizzaVisiteConfermate();
            case 4:
                return false;
            default:
                view.stampaMessaggio("Scelta non valida");
        }
        return true;
    }

    

}
