package printer;

import model.Elenco;

public class ElencoPrinter implements Printable{

    @Override
    public String print(Object object) {
        if (!(object instanceof Elenco<?>)) {
            throw new IllegalArgumentException("Oggetto passato non è un Elenco. Tipo ricevuto: " + object.getClass().getName());
        }

        Elenco<?> elenco = (Elenco<?>) object;

        StringBuilder sb = new StringBuilder();
        try {
            for (Object o : elenco.getElenco().values()) {
                sb.append("- ").append(FormatterRegister.print(o)).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la formattazione dell'elenco", e);
        }

        return sb.toString();
    }

}
