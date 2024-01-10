package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
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
        if (actionEvent.getSource() instanceof Button) {
            handleRegularButton((Button) actionEvent.getSource());
        } else {
            handleToggleButton((ToggleButton) actionEvent.getSource());
        }
    }

    private void handleRegularButton(Button button) {
        switch (button.getId()) {
            case "skipBackButton":
                player.skipBack();
                break;
            case "playButton":
                // update Property / boolean oder so, damit der Text demnach gesetzt werden kann
                if (player.isPlaying()) {
                    player.pause();
                    Platform.runLater(() -> button.setText("Play"));
                } else if (!player.isPlaying()) {
                    player.play();
                    Platform.runLater(() -> button.setText("Pause"));
                }
                break;
            case "skipButton":
                player.skip();
                break;
        }
    }

    private void handleToggleButton(ToggleButton toggleButton) {
        switch (toggleButton.getId()) {
            case "shuffleButton":
                player.shuffle();
                if(toggleButton.isSelected()) {
                    toggleButton.getStyleClass().add("activated");
                } else {
                    toggleButton.getStyleClass().remove("activated");
                }
                break;
            case "repeatButton":
                player.repeat();
                if(toggleButton.isSelected()) {
                    toggleButton.getStyleClass().add("activated");
                } else {
                    toggleButton.getStyleClass().remove("activated");
                }
                break;
            case "muteButton":
                if (toggleButton.isSelected()) {
                    toggleButton.getStyleClass().add("activated");
                } else {
                    toggleButton.getStyleClass().remove("activated");
                }
                player.mute();
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
