package application;

import business_logic.services.KeyboardController;

public class App {

    public static void main(String[] args) {
        KeyboardController controller = new KeyboardController();
        controller.start();
    }
}
