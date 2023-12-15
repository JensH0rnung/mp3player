package application;

import business_logic.services.MP3Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.scenes.playerView.PlayerViewController;
import presentation.scenes.playlistView.PlaylistViewController;

import java.util.HashMap;

/**
 * Startet die Anwendung
 */
public class App extends Application {
    private Stage primaryStage;
    private HashMap<PrimaryViewName, Pane> primaryViews;

    Pane playerView;
    Pane playlistView;

    MP3Player player;

    @Override
    public void init() {

        player = new MP3Player();
        primaryViews = new HashMap<>();

        // erzeugt beide Views(UI) mit ihren Controllern (Logik)
        PlayerViewController playerViewController = new PlayerViewController(player);
        playerView = playerViewController.getRoot();
        primaryViews.put(PrimaryViewName.PlayerView, playerView);

        PlaylistViewController playlistViewController = new PlaylistViewController(player);
        playlistView = playlistViewController.getRoot();
        primaryViews.put(PrimaryViewName.PlaylistView, playlistView);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        VBox root = new VBox();

        Scene scene = new Scene(root, 400, 650);
        // Einbindung von .css-Datei
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);

        // Standard-View -> PlayerView
        switchView(PrimaryViewName.PlayerView);

        primaryStage.setTitle("MP3Player");
        primaryStage.show();
    }

    public void stop() {

    }

    /**
     * Wechselt zwischen erstellten Views
     *
     * @param viewName - View, der angezeigt werden soll
     */
    public void switchView(PrimaryViewName viewName) {
        Scene currentScene = primaryStage.getScene();

        Pane nextView = primaryViews.get(viewName);
        if (nextView != null) {
            currentScene.setRoot(nextView);
        }
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
