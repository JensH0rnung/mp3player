package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Steuerlogik der PlayerControls, Verknüpfung mit View-Definition in FXML
 */
public class ControlViewController implements EventHandler<ActionEvent> {

    private VBox root;
    private MP3Player player;

//    @FXML
//    Button shuffleButton;
//    @FXML
//    Button skipBackButton;
//    @FXML
//    Button playButton;
//    @FXML
//    Button skipButton;
//    @FXML
//    Button repeatButton;

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

        // ggf. hier Stylesheet setzen, das allgemeinen Style überschreibt
//        root.getStylesheets().add(getClass().getResource("style_playerControls.css").toExternalForm());
    }

    /**
     * Wird vom PlaylistView- & PlayerViewController angesprochen
     *
     * @param actionEvent - Klick auf Button
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();
        switch (sourceButton.getId()) {

            case "shuffleButton":
                player.shuffle();
                // wenn shuffle aktiviert
                if(player.shuffleStateProperty().get()) {
                    player.shuffleStyleProperty().set("activated");

                    sourceButton.getStyleClass().remove("deactivated");
                } else {
                    player.shuffleStyleProperty().set("deactivated");

                    sourceButton.getStyleClass().remove("activated");
                }
                break;
            case "skipBackButton":
                player.skipBack();
                break;
            case "playButton":
                if (player.isPlaying()) {
                    player.pause();
                    player.playButtonTextProperty().set("Play");
                } else if (!player.isPlaying()) {
                    player.play();
                    player.playButtonTextProperty().set("Pause");
                }
                break;
            case "skipButton":
                player.skip();
                break;
            case "repeatButton":
                player.repeat();
                // wenn repeat aktiviert
                if(player.repeatStateProperty().get()) {
                    player.repeatStyleProperty().set("activated");

                    sourceButton.getStyleClass().remove("deactivated");
                } else {
                    player.repeatStyleProperty().set("deactivated");

                    sourceButton.getStyleClass().remove("activated");
                }
                break;
            case "muteButton":
                player.mute();
                if (player.muteStateProperty().get()) {
                    player.muteStyleProperty().set("activated");

                    sourceButton.getStyleClass().remove("deactivated");
                } else {
                    player.muteStyleProperty().set("deactivated");

                    sourceButton.getStyleClass().remove("activated");
                }
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
