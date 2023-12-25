package application;

import business_logic.services.MP3Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.scenes.playerView.PlayerViewController;
import presentation.scenes.playlistView.PlaylistViewController;

import java.util.HashMap;

/**
 * Startet die Anwendung
 */
public class App extends Application {
    private Stage primaryStage;

    private HashMap<String, Pane> primaryViews;

    Pane playerView;
    Pane playlistView;

    MP3Player player;

    @Override
    public void init() {

        player = new MP3Player();
        primaryViews = new HashMap<>();

        /*
         erzeugt beide Views(UI) mit ihren Controllern (Logik)
         und fügt diese der HashMap hinzu
         */
        PlayerViewController playerViewController = new PlayerViewController(player);
        playerView = playerViewController.getRoot();
        primaryViews.put("PlayerView", playerView);

        PlaylistViewController playlistViewController = new PlaylistViewController(player, primaryStage, primaryViews);
        playlistView = playlistViewController.getRoot();
        primaryViews.put("PlaylistView", playlistView);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage; // Window

        // Standard-View
        Pane root = primaryViews.get("PlaylistView");

        Scene scene = new Scene(root, 475, 600);
        // Einbindung von .css-Datei
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setScene(scene);

        primaryStage.setTitle("MP3Player");
        primaryStage.show();
    }

    public void stop() {

    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Controller für Keyboard-Funktionen
     * -> wird später entfernt
     * Launch für GUI
     */
    public static void main(String[] args) {
//        KeyboardController keyboardController = new KeyboardController();
//        keyboardController.start();
        launch(args);
    }
}
