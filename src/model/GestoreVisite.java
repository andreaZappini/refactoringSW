package model;

import java.time.LocalDate;

import model.stato.StatiVisita;

public class GestoreVisite {

    private static final GestoreVisite instance = new GestoreVisite();

    private GestoreVisite() {
        DatiCondivisi.aggiungiVisita(new ListaVisite("0"));
        DatiCondivisi.aggiungiVisita(new ListaVisite("1"));
    }
    public static GestoreVisite getInstance() {
        return instance;
    }

    public void creaPianoViste(LocalDate inizio, LocalDate fine) {
        for(LocalDate giorno = inizio; giorno.isBefore(fine); giorno = giorno.plusDays(1)){
            
            if(DatiCondivisi.getDatePrecluse().contiene(giorno.toString())) 
                continue;

            ciclotipoVisita: //label per uscire dal ciclo dei tipi di visita
            for(TipoVisita tipo : DatiCondivisi.getElencoTipiVisita().getElenco().values()){
                
                for(Volontario volo : tipo.getElencoVolontari().getElenco().values()){


                    boolean cond1 = volo.getElencoDisponibilita().contiene(giorno.toString());
                    boolean cond2 = tipo.getGiorniDisponibili().contains(Giorni.traduci(giorno));

                    if(cond1 && cond2){
                        Visita visita = new Visita(giorno, tipo);
                        DatiCondivisi.aggiungiVisitaMese1(visita);
                        break ciclotipoVisita; // Esce dal ciclo dei tipi di visita se trova una visita valida
                    }
                }
            }
        }
    }

    
    public LocalDate[] intervallo(){
        if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.APERTA &&
            DatiCondivisi.getRaccoltaDisponibilitaMese2() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA)
            return GestioneTempo.getInstance().intervalloDate(0);

        else if(DatiCondivisi.getRaccoltaDisponibilitaMese1() == DatiCondivisi.StatiRaccoltaDisponibilita.CHIUSA &&
            DatiCondivisi.getRaccoltaDisponibilitaMese2() == DatiCondivisi.StatiRaccoltaDisponibilita.APERTA)
            return GestioneTempo.getInstance().intervalloDate(1);

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

    public void aggiornaStato(){
        for(Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values())
            v.aggiornaStato();
    }


    // public void aggiornaStato(){

    //     for(Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values()) {
    //         switch(v.getStato()) {

    //             case VISITA_CANCELLATA:
    //                 DatiCondivisi.getVisite().getElementByKey("0").getVisite().rimuovi(v);
    //                 break;

    //             case VISITA_CONFERMATA:
    //                 if(v.getDataVisita().isBefore(GestioneTempo.getInstance().getDataCorrente())){
    //                     v.cambiaStato(St.VISITA_EFFETTUATA);
    //                     DatiCondivisi.aggiungiVisitaArchivio(v);
    //                 }
    //                 break;

    //             case VISITA_COMPLETA:
    //                 if(GestioneTempo.getInstance().getDataCorrente().equals(v.getDataVisita().minusDays(3)))
    //                     v.cambiaStato(St.VISITA_CONFERMATA);
    //                 break;

    //             case VISITA_EFFETTUATA:

    //                 break;
    //             case VISITA_PROPOSTA:
                
    //                 if(GestioneTempo.getInstance().getDataCorrente().equals(v.getDataVisita().minusDays(3))){

    //                     if(v.getIscritti() < v.getMinPartecipanti())
    //                         v.cambiaStato(St.VISITA_CANCELLATA);
                        
    //                     else
    //                         v.cambiaStato(St.VISITA_CONFERMATA);
    //                 }
    //                 if(v.getMaxPartecipanti() == v.getIscritti())
    //                     v.cambiaStato(St.VISITA_COMPLETA);
                    
    //             case VISITA_PROPONIBILE:
                
    //                 // Se la visita è ancora proponibile, non facciamo nulla
    //                 break;
    //             default:
    //                 throw new IllegalStateException("Stato visita non gestito: " + v.getStato());
                
    //         }
    //     }
    // }



    public Elenco<Visita> visiteDisponibili(){
        Elenco<Visita> visiteDisponibili = new Elenco<>();
        for (Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values()) {
            St stato = v.getStato();
            if(stato == St.VISITA_PROPOSTA||
                stato == St.VISITA_CONFERMATA ||
                stato == St.VISITA_CANCELLATA)
                visiteDisponibili.aggiungi(v);
        }
        return visiteDisponibili;
    }

    public Elenco<Visita> visitePrenotabili(){
        Elenco<Visita> visitePrenotabili = new Elenco<>();
        for (Visita v : DatiCondivisi.getVisite().getElementByKey("0").getVisite().getElenco().values()) {
            St stato = v.getStato();
            if(stato == St.VISITA_PROPOSTA){
                visitePrenotabili.aggiungi(v);
            }
        }
        return visitePrenotabili;
    }

    public void rimuoviViistePassateFruitore(){
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