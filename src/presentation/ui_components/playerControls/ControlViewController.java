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

        // Style setzen über diese Klasse funktioniert iwie net
        root.getStylesheets().add(getClass().getResource("style_playerControls.css").toExternalForm());
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
                if(player.isOnShuffle()) {
                    sourceButton.getStyleClass().add("activated");
                } else {
                    sourceButton.getStyleClass().remove("activated");
                }
                break;
            case "skipBackButton":
                player.skipBack();
                break;
            case "playButton":
                if(player.isPlaying()) {
                    player.pause();
                    sourceButton.setText("Play");
                } else {
                    player.play();
                    sourceButton.setText("Pause");
                }
                break;
            case "skipButton":
                player.skip();
                break;
            case "repeatButton":
                player.repeat();
                if(player.isOnRepeat()) {
                    sourceButton.getStyleClass().add("activated");
                } else {
                    sourceButton.getStyleClass().remove("activated");
                }
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
