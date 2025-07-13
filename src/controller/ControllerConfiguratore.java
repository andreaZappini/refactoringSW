package controller;

import model.*;
import view.*;
import printer.*;

public class ControllerConfiguratore {

    private Configuratore configuratore;
    private final IView view;
    private ConfiguratoreService action;

    private boolean chiudiApp = true;

        private static final String AZIONI_CONFIGURATORE = 
        "\n----------------------------------------------------------------------------------------------"
        + "\nBenvenuto Configuratore! Scegli una delle seguenti alternative: \n\n" +
        "1. Aggiungere un nuovo Configuratore\n" +
        "2. Indicare le date del mese i+3 precluse ad ogni visita\n" +
        "3. Modificare il numero massimo di iscritti per ogni fruitore\n" +
        "4. Visualizzare l'elenco dei volontari, con relativi tipi di visita per cui ha dato disponibilità\n" +
        "5. Visualizzare l'elenco dei luoghi visitabili\n" +
        "6. Visualizzare l'elenco dei tipi di visita associati ad un determinato luogo\n" +
        "7. Visualizzare lo stato delle visite\n"+
        "8. passa a controlli su elenchi(creazione/eliminazione soggetti)\n" +
        "9. Chiudi raccolta disponibilità per il mese i+1\n" +
        "10. Creare il piano visite per il mese i+1\n" +
        "11. Aprire la raccolta disponibilità per il mese i+2\n" +
        "12. Visualizzare l'archivio delle visite\n" +
        "13. logout\n" +
        "14. Chiudere l'applicazione\n" + 
        "--> ";


    public ControllerConfiguratore(Configuratore conf, IView view) {
        this.configuratore = conf;
        this.view = view;
        this.action = new ConfiguratoreService(conf, view);
    }

    public boolean start() {
        try {
            boolean continua = true;
            while (continua) {
                int scelta = view.sceltaInt(AZIONI_CONFIGURATORE);
                continua = azioniConfiguratore(scelta);
            }
            return chiudiApp;
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return false;
        }
    }

    private boolean azioniConfiguratore(int scelta){

        boolean continua = true;
        switch (scelta) {
            case 1:
                action.aggiungiConfiguratore();
                break;
            case 2:
                action.indicaDatePrecluse();
                break;
            case 3:
                action.cambiaNumeroMassimoIscritti();
                break;
            case 4:
                action.visualizzaVolontari();
                break;
            case 5:
                view.stampaMessaggio(FormatterRegister.printCorto(DatiCondivisi.getElencoLuoghi()));
                break;
            case 6:
                action.visualizzaVisiteLuogo();
                break;
            case 7:
                action.visualizzaStatoVisite();
                break;
            case 8:
                if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA){
                    ControllerExtra controllerExtra = new ControllerExtra(configuratore, view);
                    controllerExtra.start();
                } else {
                    view.stampaMessaggio("impossibile accedere a questa funzionalita" +
                    "(non è possibile apportare modifiche con la raccolta disponibilita aperta)");
                }
                break;
            case 9:
                action.chiudiDiponibilitaMese1();
                break;
            case 10:
                action.creaPianoVisite();
                break;
            case 11:
                action.apriDipsonibilitaMese2();
                break;
            case 12:
                action.visualizzaArchivio();
                break;
            case 13:
                continua = false;
                break;
            case 14:
                chiudiApp = false;
                continua = false;
                break;
            default:
                break;
        }
        return continua;
    }

}
