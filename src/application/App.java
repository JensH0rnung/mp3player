package application;

import business_logic.services.KeyboardController;

/**
 * Startet die Anwendung
 */
public class App {

    public static void main(String[] args) {
        KeyboardController keyboardController = new KeyboardController();
        keyboardController.start();
    }
}
