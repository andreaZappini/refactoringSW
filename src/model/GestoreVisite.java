package model;

import java.time.LocalDate;

public class GestoreVisite {

    private static final GestoreVisite instance = new GestoreVisite();

    private GestoreVisite() {
        if(!DatiCondivisi.getVisite().contiene("0"))
            DatiCondivisi.aggiungiVisita(new ListaVisite("0"));
        if(!DatiCondivisi.getVisite().contiene("1"))
            DatiCondivisi.aggiungiVisita(new ListaVisite("1"));
    }
    public static GestoreVisite getInstance() {
        return instance;
    }

    public void creaPianoViste(LocalDate inizio, LocalDate fine) {
        for(LocalDate giorno = inizio; giorno.isBefore(fine); giorno = giorno.plusDays(1)){
            
            boolean continua = false;
            for(ListaDate ld : DatiCondivisi.getDatePrecluse().getElenco().values()){
                if(ld.getDate().contains(giorno)) 
                    continua = true;
            }

            if(continua) continue;
              
            ciclotipoVisita:
            for(TipoVisita tipo : DatiCondivisi.getElencoTipiVisita().getElenco().values()){
                
                for(Volontario volo : tipo.getElencoVolontari().getElenco().values()){


                    boolean cond1 = volo.getElencoDisponibilita().contiene(giorno.toString());
                    boolean cond2 = tipo.getGiorniDisponibili().contains(Giorni.traduci(giorno));
                    boolean cond3 = false;
                    try{
                        cond3 = DatiCondivisi.getVisite().getElementByKey("0").getVisite().contiene(tipo+"-"+giorno.toString());
                    }catch (Exception e) {
                        cond3 = false;
                    }
                    if(cond1 && cond2 && !cond3){
                        Visita visita = new Visita(giorno, tipo);
                        DatiCondivisi.aggiungiVisitaMese1(visita);
                        break ciclotipoVisita; 
                    }
                }
            }
        }
    }

    
    public LocalDate[] intervallo(){
        if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.APERTA &&
            DatiCondivisi.getRaccoltaDisponibilitaMese2() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA)
            return GestioneTempo.getInstance().intervalloDate(1);

        else if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA &&
            DatiCondivisi.getRaccoltaDisponibilitaMese2() == DatiCondivisi.StatiRaccoltaDisponibilita.APERTA)
            return GestioneTempo.getInstance().intervalloDate(2);

        else{
            return null;
        }
    }

    public void aggiornaVisiteMese(int mesi){
        for(int step = 0; step < mesi; step++){
    
            Elenco<Visita> visiteOriginali = DatiCondivisi.getVisite().getElementByKey("1").getVisite();
            Elenco<Visita> copia = new Elenco<>();
    
            for (Visita v : visiteOriginali.getElenco().values()) {
                v.aggiornaStato();
                copia.aggiungi(v);
            }
    
            DatiCondivisi.getVisite().getElementByKey("0").getVisite().pulisciElenco();
            DatiCondivisi.setVisiteConChiave("0", copia);
    
            DatiCondivisi.getVisite().getElementByKey("1").getVisite().pulisciElenco();
        }
    }

    public void aggiornaStati(){
        for(Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values())
            v.aggiornaStato();
    }

    public Elenco<Visita> visiteDisponibili(){
        Elenco<Visita> visiteDisponibili = new Elenco<>();
        for (Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values()) {

            if(v.getStato().isDisponibile()){
                visiteDisponibili.aggiungi(v);
            }
        }
        return visiteDisponibili;
    }

    public Elenco<Visita> visitePrenotabili(){
        Elenco<Visita> visitePrenotabili = new Elenco<>();
        for (Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values()) {

            if(v.getStato().isPrenotabile()){
                visitePrenotabili.aggiungi(v);
            }
        }
        return visitePrenotabili;
    }

    public void rimuoviVisitePassateFruitore(){
        Elenco<Fruitore> fruitori = DatiCondivisi.getElencoUtenti().getClassiUtente(Fruitore.class);
        for(Fruitore f : fruitori.getElenco().values()) {

            for (Visita v : f.getPrenotazioniVisite().getElenco().values()) {
                if(v.getDataVisita().isBefore(GestioneTempo.getInstance().getDataCorrente().plusDays(1))){
                    f.rimuoviPrenotazione(v);
                }
            }
        }
    }
}