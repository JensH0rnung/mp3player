package presentation.scenes.playerView;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;

public class PlayerViewController implements EventHandler<ActionEvent> {

    PlayerView root;
    private MP3Player player;

    ControlViewController controlViewController;

    Button playerViewButton;
    Button playlistViewButton;

    Button shuffleButton;
    Button skipBackButton;
    Button playButton;
    Button skipButton;
    Button repeatButton;

    public PlayerViewController(MP3Player player) {

        root = new PlayerView();

        this.player = player;
        this.controlViewController = new ControlViewController(player);

        initializeButtons();
    }

    /**
     * sollte später vom ControlViewController übernommen werden,
     * um redundanten Code zu vermeiden
     */
    public void initializeButtons() {

//        playerViewButton = root.viewChangeView.playerViewButton;
//        playlistViewButton = root.viewChangeView.playlistViewButton;

//        shuffleButton = controlViewController.shuffleButton;
//        skipBackButton = controlViewController.skipBackButton;
//        playButton = controlViewController.playButton;
//        skipButton = controlViewController.skipButton;
//        repeatButton = controlViewController.repeatButton;

//        playerViewButton.setOnAction(this);
//        playlistViewButton.setOnAction(this);

//        shuffleButton.setOnAction(this);
//        skipBackButton.setOnAction(this);
//        playButton.setOnAction(this);
//        skipButton.setOnAction(this);
//        repeatButton.setOnAction(this);
    }

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
                    playButton.getStyleClass().add("play");
                    playButton.getStyleClass().remove("pause");
                } else {
                    player.play();
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
