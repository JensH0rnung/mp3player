package presentation.scenes.playerView;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class PlayerViewController {

    PlayerView root;
    Button playButton;

    private MP3Player player;

    public PlayerViewController(MP3Player player) {

        root = new PlayerView();
        playButton = root.playerControlsView.playButton;

        this.player = player;

        initialize();

    }

    public void initialize() {

        playButton.addEventHandler(ActionEvent.ACTION,
            new EventHandler<ActionEvent>() {
                boolean playing = false;
                @Override
                public void handle(ActionEvent event) {
                    if (playing) {
                        player.pause();
                        playing = false;
                        playButton.getStyleClass().add("play");
                        playButton.getStyleClass().remove("pause");

                    } else {
                        player.play();
                        playing = true;
                        playButton.getStyleClass().add("pause");
                        playButton.getStyleClass().remove("play");
                    }

                }

            }
        );

        /*
		playButton.setOnAction(event -> play());

		playButton.addEventHandler(ActionEvent.ACTION,
             event -> play()
        );
        */
    }

}
