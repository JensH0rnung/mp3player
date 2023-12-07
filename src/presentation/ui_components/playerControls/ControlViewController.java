package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Steuerlogik der PlayerControls, Verknüpfung mit FXML / View
 *
 */
public class ControlViewController implements EventHandler<ActionEvent> {

    ControlView root;
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

    private MP3Player player;

    public ControlViewController(MP3Player player){

        if(root == null) {
            root = new ControlView();
        }
        this.player = player;

        shuffleButton = root.shuffleButton;
        skipBackButton = root.skipBackButton;
        playButton = root.playButton;
        skipButton = root.skipButton;
        repeatButton = root.repeatButton;

    }

    /**
     * Bestimmt, was passiert, wenn ein Button gedrückt wird
     * @param actionEvent - Klick auf Button
     */
    @Override
    @FXML
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();
        if(sourceButton == shuffleButton) {
            player.shuffle();
            if(player.isOnShuffle()) {
                shuffleButton.setStyle("-fx-background-color: green");
            } else {
                shuffleButton.setStyle("-fx-background-color: darkgrey");
            }
        } else if(sourceButton == skipBackButton) {
            player.skipBack();
        } else if(sourceButton == playButton) {
            if(player.isPlaying()) {
                player.pause();
                updateButtonText(playButton, "Play");
            } else {
                player.play();
                updateButtonText(playButton, "Pause");
            }
        } else if(sourceButton == skipButton) {
            player.skip();
        } else if(sourceButton == repeatButton) {
            player.repeat();
            if(player.isOnRepeat()) {
                repeatButton.setStyle("-fx-background-color: green");
            } else {
                repeatButton.setStyle("-fx-background-color: white");
            }
        }
    }

    /**
     * Sinnvolle Implementierung?
     * Ändert das Icon des übergebenenden Buttons
     *
     * @param buttonToUpdate - Button, dessen Icon geändert werden soll
     * @param textToSet - tempText, anstatt Icon, das gesetzt werden soll
     */
    public void updateButtonText(Button buttonToUpdate, String textToSet) {
        buttonToUpdate.setText(textToSet);
    }

    public Pane getRoot() {
        return root;
    }
}
