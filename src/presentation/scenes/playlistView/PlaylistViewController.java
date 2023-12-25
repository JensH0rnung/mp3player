package presentation.scenes.playlistView;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

import java.io.IOException;
import java.util.HashMap;

public class PlaylistViewController implements EventHandler<ActionEvent> {

    private BorderPane root;
    private MP3Player player;
    private Stage primaryStage;
    private HashMap<String, Pane> primaryViews;

    @FXML
    Button playerViewButton;
    @FXML
    Button playlistViewButton;
    @FXML
    Button shuffleButton;
    @FXML
    Button skipBackButton;

    @FXML
    Button playButton;
    @FXML
    Button skipButton;
    @FXML
    Button repeatButton;

    @FXML
    ListView<String> listView;

    ControlViewController controlViewController;
    ViewChangeController viewChangeController;

    public PlaylistViewController(MP3Player player, Stage primaryStage, HashMap<String, Pane> primaryViews) {

        this.player = player;
        this.primaryStage = primaryStage;
        this.primaryViews = primaryViews;

        controlViewController = new ControlViewController(player);
        viewChangeController = new ViewChangeController(player, primaryStage, primaryViews);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PlaylistView.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         Aufruf könnte wegfallen, wird automatisch vom FXMLLoader aufgerufen
         Dient hier der besseren Lesbarkeit
         */
        initialize();
    }

    public void initialize() {

        // Sollte ViewChangeController sein
        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        // Sollte ControlViewController sein
        shuffleButton.setOnAction(this);
        skipBackButton.setOnAction(this);
        playButton.setOnAction(this);
        skipButton.setOnAction(this);
        repeatButton.setOnAction(this);
    }

    /**
     * Bestimmt, was bei Drücken der einzelnen Buttons passiert
     * - sollte ausgelagert werden, dann funktioniert momentan das Styling allerdings nicht
     *
     * @param actionEvent - Klick auf Button
     */
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();

        switch (sourceButton.getId()) {
            case "player-view-button":

                break;
            case "playlist-view-button":

                break;
            case "shuffle-button":
                player.shuffle();
                if(player.isOnShuffle()) {
                    shuffleButton.getStyleClass().add("activated");
                } else {
                    shuffleButton.getStyleClass().remove("activated");
                }
                break;
            case "skip-back-button":
                player.skipBack();
                break;
            case "play-button":
                if(player.isPlaying()) {
                    player.pause();
                    playButton.setText("Play");
                    playButton.getStyleClass().add("play");
                    playButton.getStyleClass().remove("pause");
                } else {
                    player.play();
                    playButton.setText("Pause");
                    playButton.getStyleClass().add("pause");
                    playButton.getStyleClass().remove("play");
                }
                break;
            case "skip-button":
                player.skip();
                break;
            case "repeat-button":
                player.repeat();
                if (player.isOnRepeat()) {
                    repeatButton.getStyleClass().add("activated");
                } else {
                    repeatButton.getStyleClass().remove("activated");
                }
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
