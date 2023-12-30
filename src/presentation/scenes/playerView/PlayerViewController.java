package presentation.scenes.playerView;

import application.App;
import business_logic.services.MP3Player;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

public class PlayerViewController {

    private PlayerView root;
    private MP3Player player;

    ControlViewController controlViewController;
    ViewChangeController viewChangeController;

    Button playerViewButton;
    Button playlistViewButton;

    Button shuffleButton;
    Button skipBackButton;
    Button playButton;
    Button skipButton;
    Button repeatButton;

    public PlayerViewController(MP3Player player, App app) {

        this.player = player;

        root = new PlayerView();

        this.controlViewController = new ControlViewController(player);
        this.viewChangeController = new ViewChangeController(app);

        initializeButtons();
    }

    /**
     * sollte später vom ControlViewController übernommen werden,
     * um redundanten Code zu vermeiden
     */
    public void initializeButtons() {

        playerViewButton = root.playerViewButton;
        playlistViewButton = root.playlistViewButton;

        shuffleButton = root.shuffleButton;
        skipBackButton = root.skipBackButton;
        playButton = root.playButton;
        skipButton = root.skipButton;
        repeatButton = root.repeatButton;

        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        shuffleButton.setOnAction(controlViewController);
        skipBackButton.setOnAction(controlViewController);
        playButton.setOnAction(controlViewController);
        skipButton.setOnAction(controlViewController);
        repeatButton.setOnAction(controlViewController);
    }

    /**
     * Fordert das Cover des aktuellen Songs an und setzt in den PlayerView
     * Parameter-Übergabe vermutlich sinnvoll
     */
//    public Image setCover() {
//        Image coverImage = new Image("");

//        return coverImage;
//    }

    public Pane getRoot() {
        return root;
    }

}
