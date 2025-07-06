package printer;

import model.Luogo;

public class LuogoPrinter implements Printable{

    @Override
    public String print(Object object) {
        Luogo luogo = (Luogo) object;
        StringBuilder sb = new StringBuilder();
        sb.append(luogo.getCodiceLuogo()+" ("+luogo.getCollocazioneGeografica()+") \n"
        		+ "["+luogo.getDescrizione()+"]\nTipi di visita associati: \n"
        		+FormatterRegister.print(luogo.getElencoVisite())+"\n");

        return sb.toString();
    }   
}