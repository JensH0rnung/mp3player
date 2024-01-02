package presentation.scenes.playerView;

import application.App;
import business_logic.services.MP3Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

public class PlayerViewController {

    private PlayerView root;

    ControlViewController controlViewController;
    ViewChangeController viewChangeController;

    Button playerViewButton;
    Button playlistViewButton;

    Label currentTimeLabel;
    ProgressBar songProgress;
    Label songLengthLabel;

    Button shuffleButton;
    Button skipBackButton;
    Button playButton;
    Button skipButton;
    Button repeatButton;

    Button muteButton;
    Slider volumeSlider;

    public PlayerViewController(MP3Player player, App app) {

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

        currentTimeLabel = root.currentTimeLabel;
        songLengthLabel = root.songLengthLabel;
        songProgress = root.songProgress;

        // Skalierung der progressBar
//        songProgress.prefWidthProperty().bind(root.widthProperty().subtract(180));

        muteButton = root.muteButton;
        volumeSlider = root.volumeSlider;

        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        shuffleButton.setOnAction(controlViewController);
        skipBackButton.setOnAction(controlViewController);
        playButton.setOnAction(controlViewController);
        skipButton.setOnAction(controlViewController);
        repeatButton.setOnAction(controlViewController);

        muteButton.setOnAction(controlViewController);
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> System.out.println(newValue)
        );

//        playButton.setOnAction(event -> {
//            playButton.setText("Pause - PVC");
//            Task<Void> task = new Task<Void>() {
//                @Override
//                protected Void call() {
//                    player.play();
//                    return null;
//                }
//            };
//            task.setOnSucceeded(event1 -> playButton.setText("Play - PVC"));
//
//            new Thread(task).start();
//        });

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
