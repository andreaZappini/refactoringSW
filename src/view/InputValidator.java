package view;

import java.util.Scanner;

/**
 * Classe per la validazione dell'input dell'utente.
 * Fornisce metodi per leggere interi, numeri decimali e stringhe non vuote.
 *
 * classe creata per il metodo di refactoring extract method/ extract class
 */

public class InputValidator {
    private static final Scanner in = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, riprova.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valore non valido, riprova.");
            }
        }
    }

    public static String readNonEmptyString(String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = in.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Il valore non può essere vuoto, riprova.");
            }
        }
        return input;
    }
}