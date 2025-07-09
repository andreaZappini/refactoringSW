package view;

public interface IView {
    void chiudiScanner();
    String[] creaCorpoDati();
    String[] signin();
    String[] login();
    String[] nuovoInserimento();
    void stampaMessaggio(String msg);
    String cambiaPassword();
    int sceltaInt(String msg);
    String sceltaString(String msg);
    String[] messaggioCreazione(String[] msg);
    String[] creaUtente(String str);
    String[] prenotaVisita();
}