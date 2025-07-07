package model.stato;

import model.Visita;

//classe per implementare il pattern State
// permette di gestire le transizioni tra gli stati delle visite
// ogni stato implementa l'interfaccia StatiVisita e fornisce la logica
// per gestire la transizione a uno stato successivo
// le classi che implementano questa interfaccia sono:
// - VisitaProponibile
// - VisitaProposta     
// - VisitaConfermata
// - VisitaEffettuata
// - VisitaCancellata
// - VisitaCompleta
// le classi che implementano questa interfaccia devono fornire la logica per gestire la
// transizione a uno stato successivo

public interface StatiVisita {

    void gestisciTransizione(Visita visita);
    void setStato(StatiVisita stato);
    String getNomeStato();
}
