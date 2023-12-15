package presentation.scenes.playlistView;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PlaylistViewController implements EventHandler<ActionEvent> {

    private PlaylistView root;
    private MP3Player player;

    private Button playerViewButton;
    private Button playlistViewButton;

    private Button shuffleButton;
    private Button skipBackButton;
    private Button playButton;
    private Button skipButton;
    private Button repeatButton;

//    private ControlViewController controlViewController;

    public PlaylistViewController(MP3Player player) {

        this.player = player;

        root = new PlaylistView();
//        controlViewController = new ControlViewController(player);

        playerViewButton = root.viewChangeView.playerViewButton;
        playlistViewButton = root.viewChangeView.playlistViewButton;

        shuffleButton = root.controlView.shuffleButton;
        skipBackButton = root.controlView.skipBackButton;
        playButton = root.controlView.playButton;
        skipButton = root.controlView.skipButton;
        repeatButton = root.controlView.repeatButton;

        initialize();
    }

    public void initialize() {

        playerViewButton.setOnAction(this);
        playlistViewButton.setOnAction(this);

        shuffleButton.setOnAction(this);
        skipBackButton.setOnAction(this);
        playButton.setOnAction(this);
        skipButton.setOnAction(this);
        repeatButton.setOnAction(this);
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
