package printer;

import model.Fruitore;

public class FruitorePrinter implements Printable {

    @Override
    public String print(Object object) {

        Fruitore fruitore = (Fruitore) object;

        return fruitore.getUsername(); 
    }
}
