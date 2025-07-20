package model;

import java.time.LocalDate;
import java.util.ArrayList;

import model.stato.VisitaConfermata;

public class Volontario extends Utente {

    private Elenco<TipoVisita> elencoTipiVisita;
    private Elenco<LocalDate> elencoDisponibilita;
    
    public Volontario(String username, String password, boolean primoAccesso) {
        super(username, password, primoAccesso);

        this.elencoDisponibilita = new Elenco<LocalDate>();
        this.elencoTipiVisita = new Elenco<TipoVisita>();
    }

    public Volontario(String username, String password) {
        super(username, password);

        this.elencoDisponibilita = new Elenco<LocalDate>();
        this.elencoTipiVisita = new Elenco<TipoVisita>();
    }

    public Elenco<LocalDate> getElencoDisponibilita() {
        return elencoDisponibilita;
    }

    public void setElencoDisponibilita(Elenco<LocalDate> elencoDisponibilita) {
        this.elencoDisponibilita = elencoDisponibilita;
    }

    public void setDisponibilita(Elenco<LocalDate> elencoDisponibilita) {
        this.elencoDisponibilita = elencoDisponibilita;
    }

    public void setElencoTipiVisita(Elenco<TipoVisita> elencoTipiVisita) {
        this.elencoTipiVisita = elencoTipiVisita;
    }

    public Elenco<TipoVisita> getElencoTipiVisita() {
        return elencoTipiVisita;
    }

    public void aggiungiVisitaVolontario(TipoVisita visita) {
        this.elencoTipiVisita.aggiungi(visita);
    }

    public void aggiungiDisponibilita(LocalDate data){
        this.elencoDisponibilita.aggiungi(data);
    }
    
    public void rimuoviTipoVisita(TipoVisita t) {
        this.elencoTipiVisita.rimuovi(t);
        if(this.elencoTipiVisita.vuoto()){
            DatiCondivisi.rimuoviVolontario(this);
        }
    }              

    public ArrayList<LocalDate> elencaDisponibilita(LocalDate i, LocalDate f){
        ArrayList<LocalDate> date = new ArrayList<>();
        for(TipoVisita tv : this.elencoTipiVisita.getElenco().values()){
            for(Giorni gg : tv.getGiorniDisponibili()){
                for(LocalDate d = i; d.isBefore(f); d = d.plusDays(1)){
                    for(ListaDate ld : DatiCondivisi.getDatePrecluse().getElenco().values()){
                        if(ld.getDate().contains(d))
                            continue;
                        
                        else if(Giorni.equals(gg, d) && !date.contains(d))
                            date.add(d);
                        
                    }
                }
            }
        }
        return date;
    }

    public Elenco<Visita> visiteConfermate() {
        Elenco<Visita> visiteConfermate = new Elenco<>();
        ListaVisite lv = DatiCondivisi.getVisite().getElementByKey("0");
        for (Visita v : lv.getVisite().getElenco().values()) {
            if(v.getTipo().getElencoVolontari().contiene(this.toString()) && 
            this.elencoDisponibilita.contiene(v.getDataVisita().toString()) &&
             v.getStato() instanceof VisitaConfermata) {
                visiteConfermate.aggiungi(v);
            }
            
        }
        return visiteConfermate;
    }
}