package printer;

import model.Configuratore;

public class ConfiguratorePrinter implements Printable {
    
    @Override
    public String print(Object object) {
        Configuratore c = (Configuratore) object;
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: " + c.getUsername() + "\n");
        sb.append("primo accesso: " + c.getPrimoAccesso() + "\n");
        return sb.toString();
    }
}
