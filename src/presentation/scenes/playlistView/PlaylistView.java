package presentation.scenes.playlistView;

import business_logic.services.MP3Player;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PlaylistView extends BorderPane {

    MP3Player player;

    public PlaylistView(MP3Player player) {
        this.player = player;

        setCenter(new Label("PlaylistView"));
        setStyle("-fx-background-color: grey;");
    }
}
