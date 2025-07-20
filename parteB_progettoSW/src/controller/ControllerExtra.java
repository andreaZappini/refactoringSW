package controller;

import model.*;
import view.IView;

public class ControllerExtra {

    private final IView view;
    private ExtraService action;

    public ControllerExtra(Configuratore configuratore, IView view) {
        this.view = view;
        this.action = new ExtraService(configuratore, view);
    }

    private static final String AZIONI_EXTRA = 
        "\n----------------------------------------------------------------------------------------------"
        + "\nAZIONI SUGLI ELENCHI\n\n" +
        "1. Introduci nuovo luogo e associalo a Tipi Visita\n" +
        "2. Aggiungi un tipo visita a un luogo gia esistente\n" +
        "3. aggiungi a un tipo di visita uno o piu volontari\n" + 
        "4. Aggiungi un nuovo tipo di visita con i relativi volontari\n" +
        "5. rimuovi luogo\n" +
        "6. rimuovi tipo visita relativo a un luogo\n" +
        "7. rimuovi un volontario da elenco volontari\n" +
        "8. Torna al menu principale\n" +
        "--> ";

    
    public void start() {
        boolean continua = true;
        while(continua){
            int scelta = view.sceltaInt(AZIONI_EXTRA);
            continua = azioniExtraConfiguratore(scelta);
        }
    }

    private boolean azioniExtraConfiguratore(int scelta) {
        switch (scelta) {
            case 1:
                action.aggiungiLuogo();
                break;
            case 2:
                action.aggiungiTipoVisitaLuogo();
                break;
            case 3:
                action.aggiungiVolontarioTipoVisita();
                break;
            case 4:
                action.aggiungiTipoVisita();
                break;
            case 5:
                action.rimuoviLuogo();
                break;
            case 6:
                action.rimuoviTipoVisitaDaLuogo();
                break;
            case 7:
                action.rimuoviVolontario();
                break;
            case 8:
                return false;
            default:
                return false;
        }
        return true;
    }
}