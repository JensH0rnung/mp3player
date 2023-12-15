package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Steuerlogik der PlayerControls, Verknüpfung mit FXML / View
 *
 */
public class ControlViewController implements EventHandler<ActionEvent> {

    ControlView root;
    Button shuffleButton;
    Button skipBackButton;
    Button playButton;
    Button skipButton;
    Button repeatButton;

    private MP3Player player;

    public ControlViewController(MP3Player player){

        root = new ControlView();

        this.player = player;

        shuffleButton = root.shuffleButton;
        skipBackButton = root.skipBackButton;
        playButton = root.playButton;
        skipButton = root.skipButton;
        repeatButton = root.repeatButton;

//        initializeEventHandler();
    }

//    private void initializeEventHandler() {
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
                System.out.println("Shuffle-cvc");
                player.shuffle();
                System.out.println(player.isOnShuffle());
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
                    updateButtonText(playButton, "Play");
                } else {
                    player.play();
                    updateButtonText(playButton, "Pause");
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
