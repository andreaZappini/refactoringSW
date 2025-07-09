package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.*;
import view.*;
import printer.*;

public class ControllerConfiguratore {

    private Configuratore configuratore;
    private boolean chiudiApp = true;

    private final IView view;

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

    private static final String[] CREAZIONE_LUOGO = {
        "- Espressione sintetica identificativa: ",
        "- Descrizione: ",
        "- Collocazione geografica: "
    };

    private static final String[] CREAZIONE_VISITA = {
        "- Titolo: ",
        "- Descrizione: ",
        "- Punto d'incontro: ",
        "- Periodo dell'anno: ",
        "- Ora d'inizio: ",
        "- Durata(minuti): ",
        "- Biglietto necessario(S/N): ",
        "- Numero minimo di partecipanti: ",
        "- Numero massimo di partecipanti: "
    };

    public ControllerConfiguratore(Configuratore conf, IView view) {
        this.configuratore = conf;
        this.view = view;
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
                aggiungiConfiguratore();
                break;
            case 2:
                indicaDatePrecluse();
                break;
            case 3:
                cambiaNumeroMassimoIscritti();
                break;
            case 4:
                visualizzaVolontari();
                break;
            case 5:
                view.stampaMessaggio(FormatterRegister.print(DatiCondivisi.getElencoLuoghi()));
                break;
            case 6:
                visualizzaVisiteLuogo();
                break;
            case 7:
                visualizzaStatoVisite();
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
                chiudiDiponibilitaMese1();
                break;
            case 10:
                creaPianoVisite();
                break;
            case 11:
                apriDipsonibilitaMese2();
                break;
            case 12:
                visualizzaArchivio();
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

    private void visualizzaArchivio(){
        view.stampaMessaggio("Ecco l'archivio delle visite:");
        view.stampaMessaggio(FormatterRegister.print(DatiCondivisi.getArchivio()));
    }

    private void chiudiDiponibilitaMese1(){
        try {
            if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.APERTA){
                
                view.stampaMessaggio("chiusura disponibilita per il mese successivo al prossimo avvenuta con successo");
                DatiCondivisi.setIntervalloPianoVisite(GestoreVisite.getInstance().intervallo());
                DatiCondivisi.chiudiRaccoltaDisponibilitaMese1();
            } else {
                view.stampaMessaggio("disponibilita gia chiusa");
            }
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void creaPianoVisite(){
        try{
            if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA){
                view.stampaMessaggio("creazione piano visite avvenuta con successo");
                LocalDate[] intervallo = DatiCondivisi.getIntervalloPianoVisite();
                GestoreVisite.getInstance().creaPianoViste(intervallo[0], intervallo[1]);
            } else {
                view.stampaMessaggio("ancora possibile la raccolta delle disponibilita da perte dei volontari");
            }
        }catch(Exception e){
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    public void visualizzaStatoVisite() {
        try {
            view.stampaMessaggio("Ecco le visite disponibili:");
            for (ListaVisite lv : DatiCondivisi.getVisite().getElenco().values()) {
                for (Visita v : lv.getVisite().getElenco().values()) {
                    view.stampaMessaggio(FormatterRegister.print(v));
                }
            }
        } catch (IllegalArgumentException e) {
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    private void apriDipsonibilitaMese2(){
        try {
            if(DatiCondivisi.getRaccoltaDisponibilitaMese2() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA){
                view.stampaMessaggio("apertura raccolta disponibilita per il mese successivo al prossimo avvenuta con successo");
                DatiCondivisi.apriRaccoltaDisponibilitaMese2();
            } else {
                view.stampaMessaggio("disponibilita gia aperta");
            }
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void visualizzaVolontari() {
        try {
            view.stampaMessaggio("Ecco i volontari disponibili:");
            for(Volontario v : DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class).getElenco().values()){
                view.stampaMessaggio(FormatterRegister.print(v));
            }
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void indicaDatePrecluse(){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate data = GestioneTempo.getInstance().getDataCorrente();
            view.stampaMessaggio(data.format(formatter));
            LocalDate[] intervallo = GestioneTempo.getInstance().intervalloDate(3);
            view.stampaMessaggio("intervallo da " + intervallo[0].format(formatter) + " a " + intervallo[1].format(formatter));
            view.stampaMessaggio("scegli le date da precludere(solo il numero del giorno per il prossimo mese)");
            String s = "null";
            while(!s.equals("x")){
                s = view.sceltaString("scegli un giorno: (x per uscire)");
                if(!s.equals("x")){
                    int giorno = Integer.parseInt(s);
                    LocalDate dataGiorno = GestioneTempo.contieneGiorno(intervallo[0],intervallo[1],giorno);
                    if(dataGiorno != null){
                        configuratore.aggiungiDatePrecluse(dataGiorno);
                    } else {
                        view.stampaMessaggio("giorno non valido");
                    }
                }
            }
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void aggiungiConfiguratore(){
        boolean ok = false;
        while(!ok){
            try {
                String[] dati = view.creaUtente("configuratore");
                configuratore.creaConfiguratore(dati);
                ok = true;
            } catch (Exception e) {
                view.stampaMessaggio(e.getMessage());
                ok = false;
            }
        }
    }

    private void cambiaNumeroMassimoIscritti(){
        try {
            int numeroMassimoIscrittiFruitore = InputValidator.readInt("nuovo numero massimo di iscritti per fruitore -> ");            configuratore.setNumeroMassimoIscrittiFruitore(numeroMassimoIscrittiFruitore);
        } catch (Exception e) {
            view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void visualizzaVisiteLuogo() {
        try {
            view.stampaMessaggio("Ecco i luoghi disponibili:");
            view.stampaMessaggio(FormatterRegister.print(DatiCondivisi.getElencoLuoghi()));
            String scelta = view.sceltaString("Scegli il luogo -> ");
            view.stampaMessaggio(FormatterRegister.print(DatiCondivisi.getElencoLuoghi().getElementByKey(scelta)));
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    public void primaConfigurazione() {
        try {
            String[] datiCorpoDati = view.creaCorpoDati();
            String ambito = datiCorpoDati[0];
            if(ambito == null || ambito.isEmpty()) {
                ambito = InputValidator.readNonEmptyString("inserisci l'ambito territoriale: ");
            }
            DatiCondivisi.setAmbitoTerritoriale(ambito);

            int numeroMassimo;
            try {
                numeroMassimo = Integer.parseInt(datiCorpoDati[1]);
            } catch (NumberFormatException e) {
                numeroMassimo = InputValidator.readInt("inserisci il numero massimo di fruitori: ");
            }
            DatiCondivisi.setNumeroMassimoIscrittiFruitore(numeroMassimo);
            creaLuogo();
        } catch (Exception e) {
            view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void creaLuogo(){
        try {
            if(DatiCondivisi.getElencoTipiVisita().getElenco().size() == 0){
                view.stampaMessaggio("prima deve esistere almeno un tipo di visita");
                creaTipoVisita();
            }
            view.stampaMessaggio("creazione di un nuovo luogo visitabile\n\n");
            String[] datiLuogo = view.messaggioCreazione(CREAZIONE_LUOGO);
            Luogo l = configuratore.creaLuogo(datiLuogo);
            String s = null;
            do {
                view.stampaMessaggio("scegli un tipo di visita: (x per uscire)");
                s = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoTipiVisita()));
                if(!s.equals("x")){
                    try{
                        l.getElencoVisite().aggiungi(DatiCondivisi.getElencoTipiVisita().getElementByKey(s));
                        DatiCondivisi.getElencoTipiVisita().getElementByKey(s).aggiungiLuogo(l);
                    }catch(IllegalArgumentException e){
                        view.stampaMessaggio(e.getMessage());
                    }
                }
                if(l.getElencoVisite().numeroElementi() == 0){
                    view.stampaMessaggio("deve esserci almeno un tipo di visita");
                    s = "";
                }
            } while(!s.equals("x") || l.getElencoVisite().numeroElementi() == 0);
        } catch (Exception e) {
           view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void creaTipoVisita(){
        try {
            if(DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class).getElenco().size() == 0){
                view.stampaMessaggio("prima deve esserci almeno un volontario");
                creaVolontario();
            }
            view.stampaMessaggio("creazione di un nuovo tipo di visita\n\n");
            String[] datiLuogo = view.messaggioCreazione(CREAZIONE_VISITA);
            ArrayList<Giorni> giorniDisponibili = new ArrayList<>();
            String s = null;
            do {
                s = view.sceltaString("inserisci un giorno: (x per uscire)");
                if(!s.equals("x")){
                    try{
                        giorniDisponibili.add(Giorni.fromString(s.toLowerCase()));
                    } catch (IllegalArgumentException e) {
                        view.stampaMessaggio(e.getMessage());
                    }
                }
                if(giorniDisponibili.isEmpty()){
                    view.stampaMessaggio("deve esserci almeno un giorno");
                    s = "";
                }
            } while(!s.equals("x") || giorniDisponibili.isEmpty());
            Elenco<Volontario> elencoV = new Elenco<>();
            s = null;
            do {
                view.stampaMessaggio("scegli volontario: (x per uscire)");
                s = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class)));
                if(!s.equals("x")){
                    try{
                        elencoV.aggiungi((Volontario)DatiCondivisi.getElencoUtenti().getElementByKey(s));
                    }catch (IllegalArgumentException e) {
                        view.stampaMessaggio(e.getMessage());
                    }
                }
                if(elencoV.numeroElementi() == 0){
                    view.stampaMessaggio("deve esserci almeno un volontario");
                    s = "";
                }
            } while(!s.equals("x") || elencoV.numeroElementi() == 0);

            for(int i = 0; i < datiLuogo.length; i++){
                if(datiLuogo[i] == null || datiLuogo[i].isEmpty()){
                    datiLuogo[i] = InputValidator.readNonEmptyString(CREAZIONE_VISITA[i]);
                }
                if(i == 5 || i == 7 || i == 8){
                    try{
                        Integer.parseInt(datiLuogo[i]);
                    }catch(NumberFormatException e){
                        datiLuogo[i] = String.valueOf(InputValidator.readInt(CREAZIONE_VISITA[i]));
                    }
                }
                if(i == 4){
                    try{
                        Double.parseDouble(datiLuogo[i]);
                    }catch(NumberFormatException e){
                        datiLuogo[i] = String.valueOf(InputValidator.readDouble(CREAZIONE_VISITA[i]));
                    }
                }
            }
            String titolo = datiLuogo[0];
            String descrizione = datiLuogo[1];
            String puntoIncontro = datiLuogo[2];
            String periodoAnno = datiLuogo[3];
            double oraInizio = Double.parseDouble(datiLuogo[4]);
            int durata = Integer.parseInt(datiLuogo[5]);
            String bigliettoNecessario = datiLuogo[6];
            int minPartecipanti = Integer.parseInt(datiLuogo[7]);
            int maxPartecipanti = Integer.parseInt(datiLuogo[8]);

            if(minPartecipanti < 0){
                minPartecipanti = 1;
                view.stampaMessaggio("numero minimo di partecipanti non può essere negativo, impostato a 1");
            }

            if(maxPartecipanti < minPartecipanti){
                maxPartecipanti = minPartecipanti + 1;
                view.stampaMessaggio("numero massimo di partecipanti non può essere minore del minimo, impostato a " + maxPartecipanti);
            }

            if(!bigliettoNecessario.equalsIgnoreCase("s") && !bigliettoNecessario.equalsIgnoreCase("n")){
                bigliettoNecessario = "N";
                view.stampaMessaggio("biglietto necessario deve essere S o N, impostato a N");
            }
           
            configuratore.creaTipoVisita(titolo, descrizione, puntoIncontro, periodoAnno, giorniDisponibili, oraInizio, durata, bigliettoNecessario, minPartecipanti, maxPartecipanti);
            TipoVisita v = DatiCondivisi.getElencoTipiVisita().getElementByKey(titolo);
            for(Volontario volo : elencoV.getElenco().values()){
                volo.aggiungiVisitaVolontario(v);
                v.aggiungiVolontario(volo);
            }
        } catch (Exception e) {
            view.stampaMessaggio(e.toString());
            return;
        }
    }

    private void creaVolontario(){
        boolean ok = false;
        while(!ok){
            try {
                String[] dati = view.creaUtente("volontario");
                configuratore.creaVolontario(dati);
                ok = true;
            } catch (Exception e) {
               view.stampaMessaggio(e.getMessage());
                ok = false;
            }
        }
    }
}
