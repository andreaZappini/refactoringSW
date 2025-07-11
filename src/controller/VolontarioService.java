package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.GestoreVisite;
import model.Volontario;
import printer.FormatterRegister;
import view.IView;

public class VolontarioService {
    
    private Volontario volontario;
    private IView view;

    public VolontarioService(Volontario volontario, IView view) {
        this.volontario = volontario;
        this.view = view;
    }

    public void visualizzaTipiVisita() {
        view.stampaMessaggio(FormatterRegister.print(volontario));
    }

    public void esprimiDisponibilita() {

        LocalDate[] intervallo = GestoreVisite.getInstance().intervallo();
        if(intervallo != null){
            view.stampaMessaggio("intervallo: " + intervallo[0] + " - " + intervallo[1]);
            view.stampaMessaggio("indica le disponibilita' nell'intervallo da " 
                + intervallo[0] + " a " + intervallo[1]);

            String s = "";
            while (!s.equals("x")) {

                ArrayList<LocalDate> date = new ArrayList<>(volontario.elencaDisponibilita(intervallo[0], intervallo[1]));
                
                for(LocalDate d : date){
                    view.stampaMessaggio("giorno disponibile -> " + d.toString());;
                }
                
                s = view.sceltaString("inserisci la data oppure 'x' per terminare");
                if (!s.equals("x")) {
                    try {
                        LocalDate dataGiorno = LocalDate.parse(s);
                        if(dataGiorno != null){
                            volontario.aggiungiDisponibilita(dataGiorno);
                        }else{
                            view.stampaMessaggio("giorno non valido");
                        }
                    } catch (Exception e) {
                        view.stampaMessaggio("Formato data non valido");
                    }
                }
            }
        }else
            view.stampaMessaggio("Non e' possibile esprimere disponibilita' in questo momento");
    }
}
