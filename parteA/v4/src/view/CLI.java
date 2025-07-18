package view;

import java.util.Scanner;

public class CLI {
    
    private static Scanner in = new Scanner(System.in);

    public static void chiudiScanner(){
        in.close();
    }

    public static String[] creaCorpoDati(){

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

    public static String[] signin(){
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
        
     //aggiungi metodo sign in
    public static String[] login(){

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
    
    public static String[] nuovoInserimento(){

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

    public static void stampaMessaggio(String msg){
        System.out.println(msg);
    }

    public static String cambiaPassword(){
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

    public static int sceltaInt(String msg){
        
        int scelta = 0;

        try{
            System.out.printf(msg);
            scelta = Integer.parseInt(in.nextLine());
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento della scelta!");
        }
        return scelta;
    }

    public static String sceltaString(String msg){
        
        String scelta = null;

        try{
            System.out.printf(msg);
            scelta = in.nextLine();
        }catch(Exception e){
        	System.out.println("ERRORE durante l'inserimento della scelta!");
        }
        return scelta;
    }

    public static String[] messaggioCreazione(String[] msg){
        String[] res = new String[msg.length];
        for(int i = 0; i < msg.length; i++){
            System.out.printf(msg[i]);
            res[i] = in.nextLine();
        }
        return res;
    }

    public static String[] creaUtente(String str) {
        
        
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

    public static String[] prenotaVisita(){
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