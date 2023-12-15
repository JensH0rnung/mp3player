package presentation.ui_components.viewChange;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class ViewChangeView extends HBox {

    public Button playerViewButton;
    public Button playlistViewButton;

    public ViewChangeView () {

        playerViewButton = new Button("Player");
        playlistViewButton = new Button("Playlist");

        playerViewButton.setId("player-view-button");
        playlistViewButton.setId("playlist-view-button");

        getChildren().addAll(playerViewButton, playlistViewButton);

        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(2));


        setId("view-change-view");
    }
}
