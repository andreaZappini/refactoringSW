package controller;

import java.util.ArrayList;

import model.Configuratore;
import model.DatiCondivisi;
import model.Giorni;
import model.Luogo;
import model.TipoVisita;
import model.Volontario;
import printer.FormatterRegister;
import view.IView;
import view.InputValidator;

public class ExtraService {
    
    private Configuratore configuratore;
    private IView view;

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
        "- Durata: ",
        "- Biglietto necessario: ",
        "- Numero minimo di partecipanti: ",
        "- Numero massimo di partecipanti: "
    };

    public ExtraService(Configuratore configuratore, IView view) {
        this.configuratore = configuratore;
        this.view = view;
    }
    
    public void rimuoviVolontario(){
        try{            
            view.stampaMessaggio("Volontario da rimuovere: ");
            String username = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class)));
            Volontario v = (Volontario)DatiCondivisi.getElencoUtenti().getElementByKey(username);
            configuratore.rimuoviVolontario(v);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("stato volontario: " + e.getMessage());
            return;
        }
    }

    public void rimuoviLuogo() {
        try{
            view.stampaMessaggio("Luogo da rimuovere: ");
            String codiceLuogo = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoLuoghi()));
            Luogo l = DatiCondivisi.getElencoLuoghi().getElementByKey(codiceLuogo);
            configuratore.rimuoviLuogo(l);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("stato luogo: " + e.getMessage());
            return;
        }
    }
    public void rimuoviTipoVisitaDaLuogo() {
        try{
            view.stampaMessaggio("che luogo scegli a cui rimuovere il tipo di visita: ");
            String codiceLuogo = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoLuoghi()));
            Luogo l = DatiCondivisi.getElencoLuoghi().getElementByKey(codiceLuogo);
            view.stampaMessaggio("che tipo di visita vuoi rimuovere: "); 
            String titolo = view.sceltaString(FormatterRegister.print(l.getElencoVisite()));
            TipoVisita t = l.getElencoVisite().getElementByKey(titolo);
            configuratore.rimuoviTipoVisitaDaLuogo(l, t);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    public void aggiungiTipoVisita(){

        try{
            String[] datiLuogo = view.messaggioCreazione(CREAZIONE_VISITA);
            
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
            int  minPartecipanti = Integer.parseInt(datiLuogo[7]);
            int  maxPartecipanti = Integer.parseInt(datiLuogo[8]);
                        
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
            configuratore.creaTipoVisita(titolo, descrizione, puntoIncontro, periodoAnno, giorniDisponibili,
                    oraInizio, durata, bigliettoNecessario, minPartecipanti, maxPartecipanti);
            TipoVisita t = DatiCondivisi.getElencoTipiVisita().getElementByKey(titolo);
            aggiungiVolontario(t);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    public void aggiungiLuogo() {
        try{
            String[] datiLuogo = view.messaggioCreazione(CREAZIONE_LUOGO);
            Luogo l = configuratore.creaLuogo(datiLuogo);
    
            aggiungiVisita(l);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    public void aggiungiTipoVisitaLuogo(){
        try{
            view.stampaMessaggio("Luogo a cui aggiungere il tipo di visita: ");
            String codiceLuogo = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoLuoghi()));
            Luogo l = DatiCondivisi.getElencoLuoghi().getElementByKey(codiceLuogo);
            aggiungiVisita(l);
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("stato luogo: " + e.getMessage());
            return;
        }
    }

    public void aggiungiVisita(Luogo l){
        try{
            String s = "";
            do{
                view.stampaMessaggio("scegli un tipo di visita: (x per uscire)");
                s = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoTipiVisita()));
                if(s.equals("x"))
                    continue;
                TipoVisita t = DatiCondivisi.getElencoTipiVisita().getElementByKey(s);
                t.aggiungiLuogo(l);
                l.aggiungiAElencoVisite(t);
                aggiungiVolontario(t);
            }while(!s.equals("x"));
        }catch(IllegalArgumentException e){
            view.stampaMessaggio("stato tipo visita: " + e.getMessage());
            return;
        }
    }

    public void aggiungiVolontarioTipoVisita(){
        try{
            view.stampaMessaggio("Titolo del tipo di visita a cui aggiungere il volontario: ");
            String titolo = view.sceltaString(FormatterRegister.print(DatiCondivisi.getElencoTipiVisita()));
            TipoVisita t = DatiCondivisi.getElencoTipiVisita().getElementByKey(titolo);
            aggiungiVolontario(t);
        }catch (IllegalArgumentException e){
            view.stampaMessaggio("stato tipo visita: " + e.getMessage());
            return;
        }

    }

    public void aggiungiVolontario(TipoVisita t){
        String s = "";
        try{
            do{
                Volontario v = null;
                view.stampaMessaggio(FormatterRegister.print(DatiCondivisi.getElencoUtenti().getClassiUtente(Volontario.class)));
                s = view.sceltaString("aggiungi volontario esistente(e) / aggiungi volontario nuovo(n) / esci(x): ");
    
                if(s.equals("e")){
                    String volontario = view.sceltaString("nome del volontasrio da aggiungere: ");
                    v = (Volontario)DatiCondivisi.getElencoUtenti().getElementByKey(volontario);
                }
                else if(s.equals("n"))
                    v = creaVolontario();
    
                else if(s.equals("x"))
                    continue;
                
                else{
                    view.stampaMessaggio("scelta non valida");
                    continue;
                }
                if(v != null){
                    t.aggiungiVolontario(v);
                    v.aggiungiVisitaVolontario(t);
                }
            }while(!s.equals("x"));
        }catch(IllegalArgumentException e){
            view.stampaMessaggio(e.getMessage());
            return;
        }
    }

    public Volontario creaVolontario(){

        try{
            String[] dati = view.creaUtente("volontario");
            configuratore.creaVolontario(dati);
    
            return (Volontario)DatiCondivisi.getElencoUtenti().getElementByKey(dati[0]);
        }catch(Exception e){
            view.stampaMessaggio(e.getMessage());
            return null;
        }
    }
}
