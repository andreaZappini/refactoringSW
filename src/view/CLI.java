package view;

import java.util.Scanner;

public class CLI implements IView {
    
    private final Scanner in = new Scanner(System.in);

    @Override
    public void chiudiScanner(){
        in.close();
    }

    @Override
    public String[] creaCorpoDati(){

        String[] infoCorpoDati = new String[2];

        try{
        	System.out.println("\n\nFase della creazione del CORPO DATI!\n");
            System.out.printf("Indicare l'ambito territoriale prescelto -> ");
            infoCorpoDati[0] = in.nextLine();
            System.out.printf("Inserire il numero massimo iscritti per ciascun fruitore -> ");
            infoCorpoDati[1] = in.nextLine();
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per la creazione del corpo dati!");
        }

        return infoCorpoDati;
    }

    @Override
    public String[] signin(){
        String[] datiUtente = new String[3];

        try{
        	System.out.println("-----------------------------------");
        	System.out.println("Fase della registrazione utente!\n");
            System.out.printf("Inserisci il tuo username: ");
            datiUtente[0] = in.nextLine();
            System.out.printf("Inserisci la tua password: ");
            datiUtente[1] = in.nextLine();
            System.out.printf("conferma la tua password: ");
            datiUtente[2] = in.nextLine();
            
        	System.out.println("-----------------------------------");
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per il login!");
        }
        return datiUtente;
    }
        
    @Override
    public String[] login(){

        String[] datiUtente = new String[2];

        try{
        	System.out.println("-----------------------------------");
        	System.out.println("Fase del LOGIN utente!\n");
            System.out.printf("Inserisci il tuo username: ");
            datiUtente[0] = in.nextLine();
            System.out.printf("Inserisci la tua password: ");
            datiUtente[1] = in.nextLine();
        	System.out.println("-----------------------------------");
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per il login!");
        }
        return datiUtente;
    }
    
    @Override
    public String[] nuovoInserimento(){

        String[] datiUtente = new String[2];

        try{
            System.out.printf("Inserire l'username: ");
            datiUtente[0] = in.nextLine();
            System.out.printf("Inserire la password: ");
            datiUtente[1] = in.nextLine();
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per il nuovo utente!");
        }
        return datiUtente;
    }

    @Override
    public void stampaMessaggio(String msg){
        System.out.println(msg);
    }

    @Override
    public String cambiaPassword(){
        String password = null;
        try{
            System.out.printf("Indicare la nuova password: ");
            password = in.nextLine();
            return password;
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento della nuova password dell'utente!");
        	return null;
        }
    }
    
    @Override
    public int sceltaInt(String msg){
        
        int scelta = 0;

        try{
            System.out.printf(msg);
            scelta = Integer.parseInt(in.nextLine());
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento della scelta!");
        }
        return scelta;
    }

    @Override
    public String sceltaString(String msg){
        
        String scelta = null;

        try{
            System.out.printf(msg);
            scelta = in.nextLine();
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento della scelta!");
        }
        return scelta;
    }

    @Override
    public String[] messaggioCreazione(String[] msg){
        String[] res = new String[msg.length];
        for(int i = 0; i < msg.length; i++){
            System.out.printf(msg[i]);
            res[i] = in.nextLine();
        }
        return res;
    }

    @Override
    public String[] creaUtente(String str) {
        
        
        String[] datiUtente = new String[2];

        try{
        	System.out.println("-----------------------------------");
        	System.out.println("creazione di un nuovo " + str.toUpperCase());
            System.out.printf("Inserisci username: ");
            datiUtente[0] = in.nextLine();
            System.out.printf("Inserisci password: ");
            datiUtente[1] = in.nextLine();
        	System.out.println("-----------------------------------");
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per il login!");
        }
        return datiUtente;
    }

    @Override    
    public String[] prenotaVisita(){
        String[] datiVisita = new String[2];

        try{
            System.out.printf("Inserire il codice della visita: ");
            datiVisita[0] = in.nextLine();
            System.out.printf("Inserire il numero di persone: ");
            datiVisita[1] = in.nextLine();
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento dei dati per la prenotazione della visita!");
        }
        return datiVisita;
    }
}
