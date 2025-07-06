package printer;

import model.Elenco;

public class ElencoPrinter implements Printable{

    @Override
    public String print(Object object) {
        try{
            Elenco<?> elenco = (Elenco<?>) object;
            StringBuilder sb = new StringBuilder();
            for(Object o : elenco.getElenco().values()){
                sb.append("- ").append(FormatterRegister.print(o)).append("\n");
            }
            return sb.toString();
        }catch(Exception e){
            throw new UnsupportedOperationException("Unimplemented method 'print'");
        }
    }
    
}
