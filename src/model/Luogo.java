package model;

public class Luogo{
    private String codiceLuogo; //chiave
    private String descrizione;
    private String collocazioneGeografica;
    private Elenco<TipoVisita> visite;

    public Luogo(String codiceLuogo, String descrizione, String collocazioneGeografica) {
        this.codiceLuogo = codiceLuogo;
        this.descrizione = descrizione;
        this.collocazioneGeografica = collocazioneGeografica;
        this.visite = new Elenco<>();
    }

    public String getCodiceLuogo() {
        return this.codiceLuogo;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public String getCollocazioneGeografica() {
        return this.collocazioneGeografica;
    }

    public void aggiungiAElencoVisite(TipoVisita v){
       this.visite.aggiungi(v);
    }

    public String toStringVisite(){
        StringBuffer s = new StringBuffer();
        for (String k : this.visite.getElenco().keySet()){
            s.append(this.visite.getElementByKey(k) + "\n");
        }
        return s.toString();
    }

    public Elenco<TipoVisita> getElencoVisite(){
        return this.visite;
    }

    @Override
    public String toString(){
        return this.codiceLuogo;
    }

    // public String toStringLuogo(){
    //     StringBuffer s = new StringBuffer();
    //     s.append(this.codiceLuogo+" ("+this.collocazioneGeografica+") \n"
    //     		+ "["+this.descrizione+"]\nTipi di visita associati: \n"
    //     		+this.visite.visualizza()+"\n");
    // 	return s.toString();
    // }

    // public String visualizzaVisite(){
    //     return this.visite.visualizza();
    // }

    public void rimuoviDaElencoTipiVisita(TipoVisita t){

        this.visite.rimuovi(t);

        if(this.getElencoVisite().vuoto())
            DatiCondivisi.rimuoviLuogo(this);
    }
}