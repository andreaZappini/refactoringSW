package model.stato;

import model.Visita;

public interface StatiVisita {

    default void gestisciTransizione(Visita visita){/*non fa nulla di default */};
    String toString();
    
    default boolean isPrenotabile() {
        return false; 
    }

    default boolean isDisponibile() {
        return false;
    }
}
