package printer;

import java.util.Map;
import java.util.HashMap;

public class FormatterRegister {
    
    private static final Map<Class<?>, Printable> printers = new HashMap<>();

    public static void registerPrinter(Class<?> clazz, Printable printer) {
        printers.put(clazz, printer);
    }

    public static String print(Object object) {
        
        Class<?> clazz = object.getClass();
        while(clazz != null){
            Printable printer = printers.get(clazz);
            if(printer != null) {
                return printer.print(object);
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalArgumentException("Nessun printer registrato per la classe: " + object.getClass().getName());
    }

}

