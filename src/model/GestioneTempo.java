package model;

import java.time.*;

public class GestioneTempo {

    private YearMonth mesePartenza;

    private static final LocalDate DATA_INIZIALE = LocalDate.of(2025, 5, 11);
    private static final Instant TEMPO_REALE_INIZIALE = Instant.parse("2025-05-11T00:00:00Z");
    private static final long SECONDI_PER_GIORNO_SIMULATO = 5;

    // Singleton
    private static final GestioneTempo instance = new GestioneTempo();

    private GestioneTempo() {
        this.mesePartenza = YearMonth.from(getDataCorrente());
        for(int i = 0; i < 4; i++) {
            String key = String.valueOf(i);
            if(!DatiCondivisi.getDatePrecluse().contiene(key))
                DatiCondivisi.aggiungiListaDate(new ListaDate(key));
        }
    }

    public void buchiTemporali() { 
        LocalDate ultimaEsecuzione = DatiCondivisi.getDataUltimaEsecuzione();
        LocalDate oggi = getDataCorrente();
    
        LocalDate inizio = ultimaEsecuzione.getDayOfMonth() < 16
            ? ultimaEsecuzione.withDayOfMonth(16)
            : ultimaEsecuzione.withDayOfMonth(16).plusMonths(1);
    
        int conteggio = 0;
        while (!oggi.isBefore(inizio.plusMonths(1).withDayOfMonth(15))) {
            conteggio++;
            inizio = inizio.plusMonths(1);
        }
        if (conteggio > 0) {
            DatiCondivisi.apriRaccoltaDisponibilitaMese1();
            DatiCondivisi.chiudiRaccoltaDisponibilitaMese2();
        }
        System.out.println("Conteggio mesi trascorsi: " + conteggio);
        aggiornaDatePrecluseMese(conteggio);
        GestoreVisite.getInstance().aggiornaVisiteMese(conteggio);
        DatiCondivisi.setDataUltimaEsecuzione(getDataCorrente());
        passaggioTempo();
    }
    
    public static GestioneTempo getInstance() {
        return instance;
    }

    public LocalDate getDataCorrente() {
        long secondiTrascorsi = Duration.between(TEMPO_REALE_INIZIALE, Instant.now()).getSeconds();
        long giorniSimulati = secondiTrascorsi / SECONDI_PER_GIORNO_SIMULATO;
        return DATA_INIZIALE.plusDays(giorniSimulati);
    }

    private void aggiornaDatePrecluseMese(int mesi){

        System.out.println("zero" + DatiCondivisi.getDatePrecluse().getElementByKey("0").getDate().size());
        System.out.println("uno" + DatiCondivisi.getDatePrecluse().getElementByKey("1").getDate().size());
        System.out.println("due" + DatiCondivisi.getDatePrecluse().getElementByKey("2").getDate().size());
        System.out.println("tre" + DatiCondivisi.getDatePrecluse().getElementByKey("3").getDate().size());

        if (mesi > 5) mesi = 5;
    
        for (int step = 0; step < mesi; step++) {
            System.out.println("Aggiornamento date precluse per " + (step + 1) + " mese/i.");
            for (int i = 1; i <= 3; i++) {
                ListaDate sorgente = DatiCondivisi.getDatePrecluse().getElementByKey(String.valueOf(i));
                ListaDate destinazione = DatiCondivisi.getDatePrecluse().getElementByKey(String.valueOf(i - 1));
    
                destinazione.getDate().clear();
                destinazione.getDate().addAll(sorgente.getDate());
            }

            // ListaDate meseZero = DatiCondivisi.getDatePrecluse().getElementByKey("0");
            // ListaDate meseUno = DatiCondivisi.getDatePrecluse().getElementByKey("1");
            // ListaDate meseDue = DatiCondivisi.getDatePrecluse().getElementByKey("2");
            // ListaDate meseTre = DatiCondivisi.getDatePrecluse().getElementByKey("3");

            // meseZero.getDate().addAll(meseUno.getDate());
            // meseUno.getDate().addAll(meseDue.getDate());
            // meseDue.getDate().addAll(meseTre.getDate());
            // meseTre.getDate().clear();
    

            ListaDate meseTre = DatiCondivisi.getDatePrecluse().getElementByKey("3");
            if (meseTre != null) meseTre.getDate().clear();
        }

        System.out.println("zero" + DatiCondivisi.getDatePrecluse().getElementByKey("0").getDate().size());
        System.out.println("uno" + DatiCondivisi.getDatePrecluse().getElementByKey("1").getDate().size());
        System.out.println("due" + DatiCondivisi.getDatePrecluse().getElementByKey("2").getDate().size());
        System.out.println("tre" + DatiCondivisi.getDatePrecluse().getElementByKey("3").getDate().size());
    }

    public void passaggioTempo() {

        LocalDate dataCorrente = getDataCorrente();
        LocalDate inizioUltimoPeriodo = mesePartenza.atDay(16);
    
        int mesiTrascorsi = 0;
        while (!dataCorrente.isBefore(inizioUltimoPeriodo.plusMonths(mesiTrascorsi + 1))) {
            mesiTrascorsi++;
        }

        if (mesiTrascorsi > 0) {

            mesePartenza = mesePartenza.plusMonths(mesiTrascorsi);
            aggiornaDatePrecluseMese(mesiTrascorsi);
            GestoreVisite.getInstance().aggiornaVisiteMese(mesiTrascorsi);

            DatiCondivisi.apriRaccoltaDisponibilitaMese1();
            DatiCondivisi.chiudiRaccoltaDisponibilitaMese2();
        }
    }

    
    public LocalDate[] intervalloDate(int n) {
        LocalDate[] res = new LocalDate[2];
        LocalDate oggi = getDataCorrente();
    
        if (oggi.getDayOfMonth() < 15) {
            res[0] = oggi.plusMonths(n).withDayOfMonth(16);
            res[1] = oggi.plusMonths(n + 1).withDayOfMonth(15);
        } else {
            res[0] = oggi.plusMonths(n + 1).withDayOfMonth(16);
            res[1] = oggi.plusMonths(n + 2).withDayOfMonth(15);
        }
    
        return res;
    }
    
    public static LocalDate contieneGiorno(LocalDate inizio, LocalDate fine, int giorno) {
        LocalDate current = inizio;
        while (!current.isAfter(fine)) {
            if (current.getDayOfMonth() == giorno) {
                return current;
            }
            current = current.plusDays(1);
        }
        return null;
    }
}