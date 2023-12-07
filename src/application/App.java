package application;

import business_logic.services.MP3Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.scenes.playlistView.PlaylistView;
import presentation.ui_components.playerControls.ControlViewController;

import java.util.HashMap;

/**
 * Startet die Anwendung
 */
public class App extends Application {
    private Stage primaryStage;
    private HashMap<PrimaryViewName, Pane> primaryViews;

    Pane playerView;
    Pane playlistView;
    Pane playerControlsView;

    MP3Player player;

    @Override
    public void init() {

        player = new MP3Player();
        primaryViews = new HashMap<>();

        ControlViewController controller = new ControlViewController(player);

        playerView = controller.getRoot();
        primaryViews.put(PrimaryViewName.PlayerView, playerView);

        playlistView = new PlaylistView(player);
        primaryViews.put(PrimaryViewName.PlaylistView, playlistView);

        playerControlsView = controller.getRoot(); // Hier wird die ControlsView als Root für die Szene zugewiesen
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        VBox root = new VBox();
        root.getChildren().add(playerControlsView); // Hier wird playerControlsView als Root für die Szene verwendet

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
