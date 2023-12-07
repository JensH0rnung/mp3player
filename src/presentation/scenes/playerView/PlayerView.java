package presentation.scenes.playerView;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import presentation.ui_components.playerControls.ControlView;

public class PlayerView extends BorderPane {

    ControlView playerControlsView;

    public PlayerView() {

        setCenter(new Label("PlayerView"));
        setStyle("-fx-background-color: linear-gradient(#5F88E8, #5FB4E8);");

        playerControlsView = new ControlView();
        setBottom(playerControlsView);
    }
}
