package printer;

public interface Printable {

    String print(Object object);
    default String printCorto(Object object){return print(object);}
}