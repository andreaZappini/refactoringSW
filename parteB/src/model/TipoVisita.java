package model;
import java.util.ArrayList;

public class TipoVisita {

    private String titolo;
    private String descrizione;
    private String puntoIncontro;
    private String periodoAnno;
    private ArrayList<Giorni> giorniDisponibili;
    private double oraInizio;
    private int durata;
    private String bigliettoNecessario;
    private int minPartecipanti;
    private int maxPartecipanti;
    private Elenco<Volontario> elencoVolontari;
    private Elenco<Luogo> elencoLuoghi;
  

    public TipoVisita(String titolo, 
                        String descrizione, 
                        String puntoIncontro, 
                        String periodoAnno, 
                        ArrayList<Giorni> giorniDisponibili, 
                        double oraInizio, 
                        int durata, 
                        String bigliettoNecessario, 
                        int minPartecipanti, 
                        int maxPartecipanti) {
                            
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.puntoIncontro = puntoIncontro;
        this.periodoAnno = periodoAnno;
        this.giorniDisponibili = giorniDisponibili;
        this.oraInizio = oraInizio;
        this.durata = durata;
        this.bigliettoNecessario = bigliettoNecessario;
        this.minPartecipanti = minPartecipanti;
        this.maxPartecipanti = maxPartecipanti;
        this.elencoVolontari = new Elenco<Volontario>();
        this.elencoLuoghi = new Elenco<Luogo>();
    }

    public Elenco<Luogo> getElencoLuoghi() {
        return this.elencoLuoghi;
    }

    public void aggiungiLuogo(Luogo l) {
        this.elencoLuoghi.aggiungi(l);
    }

    public String getTitolo(){
        return this.titolo;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public String getPuntoIncontro() {
        return this.puntoIncontro;
    }

    public String getPeriodoAnno() {
        return this.periodoAnno;
    }

    public ArrayList<Giorni> getGiorniDisponibili() {
        return this.giorniDisponibili;
    }

    public double getOraInizio() {
        return this.oraInizio;
    }

    public int getDurata() {
        return this.durata;
    }

    public String getBigliettoNecessario() {
        return this.bigliettoNecessario;
    }

    public int getMinPartecipanti() {
        return this.minPartecipanti;
    }

    public int getMaxPartecipanti() {
        return this.maxPartecipanti;
    }

    public Elenco<Volontario> getElencoVolontari() {
        return this.elencoVolontari;
    }

    @Override
    public String toString(){
        return this.titolo;
    }

    public void aggiungiVolontario(Volontario v) {
        this.elencoVolontari.aggiungi(v);
    }

    public void rimuoviVolontario(Volontario v){
        this.elencoVolontari.rimuovi(v);
        if(this.elencoVolontari.getElenco().isEmpty()){

            for(Luogo l : this.elencoLuoghi.getElenco().values())
                l.rimuoviDaElencoTipiVisita(this);
            
            DatiCondivisi.rimuoviTipoVisita(this);
        }
    }

    public void rimuoviLuogo(Luogo l) {
        this.elencoLuoghi.rimuovi(l);
        if(this.elencoLuoghi.vuoto())
            DatiCondivisi.rimuoviTipoVisita(this);
    }
}
