package controller;

import model.*;
import view.IView;

public class Controller{
    
    private final String CATEGORIE = "\n----------------------------------------------------------------------------------------------"
            + "\nBenvenuto! Scegli una delle seguenti alternative: \n\n" +
            "1. Configuratore/Volontario\n" +
            "2. Fruitore\n" +
            "--> ";
    private boolean working;

    private final IView view;

    public Controller(IView view) {
        this.view = view;
    }

    public void start(boolean primaConfigurazione) throws Exception{
        
        if(!primaConfigurazione){

            RipristinoDati.datiCondivisi();
            System.out.println(DatiCondivisi.getDatePrecluse().getElementByKey("0").getDate().size());
        }else{

            RipristinoDati.primoConfiguratore();
            boolean primoAccesso = true;
            Utente x = null;
            while(primoAccesso){
                String[] datiUtente = view.login();
                String username = datiUtente[0];
                String password = datiUtente[1];
              
                if(!DatiCondivisi.getElencoUtenti().contiene(username)){
                    view.stampaMessaggio("utente non trovato");
                }else{
                    x = DatiCondivisi.getElencoUtenti().getElementByKey(username);
                    if(x.controllaPassword(password)){
                        view.stampaMessaggio("accesso consentito");
                        x.getPrimoAccesso();
                        view.stampaMessaggio("PRIMO ACCESSO -> cambia password: ");
                        String nuovaPassword = view.cambiaPassword();
                        x.setPassword(nuovaPassword);
                        x.setPrimoAccesso();
                        primoAccesso = false;
                    }else{
                        view.stampaMessaggio("password errata");
                    }
                }
            }
            new ConfiguratoreService((Configuratore)x, view).primaConfigurazione();
        }

        working = true;
        while(working){

            int classeUtente = view.sceltaInt(CATEGORIE);
            switch(classeUtente){
                case 1:
                    loginConfiguratoreVolontario();
                    break;
                case 2:
                    new ControllerFruitore(view).start();;
                    break;
            }
        }
        RipristinoDati.salvataggioDati();
    }

    private boolean loginConfiguratoreVolontario() throws Exception{
        Utente x;
            while(true){
                String[] datiUtente = view.login();
                String username = datiUtente[0];
                String password = datiUtente[1];


                if(!DatiCondivisi.getElencoUtenti().contiene(username)){
                    view.stampaMessaggio("utente non trovato");
                }else{
                    x = DatiCondivisi.getElencoUtenti().getElementByKey(username);
                    if(x.controllaPassword(password)){
                        view.stampaMessaggio("accesso consentito");
                        if(x.getPrimoAccesso()){
                            view.stampaMessaggio("PRIMO ACCESSO -> cambia password: ");
                            String nuovaPassword = view.cambiaPassword();
                            x.setPassword(nuovaPassword);
                            x.setPrimoAccesso();
                        }
                        break;
                    }else{
                        view.stampaMessaggio("password errata");
                    }
                }
            }
            switch(x.getClass().getSimpleName()){
                case "Configuratore":
                    Configuratore config = (Configuratore)x;
                    ControllerConfiguratore cc = new ControllerConfiguratore(config, view);
                    working = cc.start();    
                    break;
                case "Volontario":
                    Volontario vol = (Volontario)x;
                    ControllerVolontario cv = new ControllerVolontario(vol, view);
                    cv.start();
                    break;
                default:
                    break;
            }
        return working;
    }
}