package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Steuerlogik der PlayerControls, Verknüpfung mit FXML / View
 *
 */
public class ControlViewController implements EventHandler<ActionEvent> {

    private VBox root;
    private MP3Player player;

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

    public ControlViewController(MP3Player player) {

        this.player = player;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ControlView.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Style setzen über diese Klasse funktioniert iwie net
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

//        initialize();
    }

//    private void initialize() {
//
//        shuffleButton.setOnAction(this);
//        skipBackButton.setOnAction(this);
//        playButton.setOnAction(this);
//        skipButton.setOnAction(this);
//        repeatButton.setOnAction(this);
//    }

    /**
     * Soll später das Handling für die Controls in allen Views übernehmen
     * setzen der Style-Klassen funktioniert derzeit allerdings nicht
     *
     * @param actionEvent - Klick auf Button
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();

        switch (sourceButton.getId()) {
            case "shuffle-button":
                player.shuffle();
//                if(player.isOnShuffle()) {
//                    shuffleButton.getStyleClass().add("activated");
//                } else {
//                    shuffleButton.getStyleClass().remove("activated");
//                }
                break;
            case "skip-back-button":
                player.skipBack();
                break;
            case "play-button":
                if(player.isPlaying()) {
                    player.pause();
//                    sourceButton.setText("Play");
                } else {
                    player.play();
//                    sourceButton.setText("Pause");
                }
                break;
            case "skip-button":
                player.skip();
                break;
            case "repeat-button":
                player.repeat();
                if(player.isOnRepeat()) {
                    repeatButton.setStyle("-fx-background-color: green");
                } else {
                    repeatButton.setStyle("-fx-background-color: white");
                }
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
