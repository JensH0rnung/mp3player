package presentation.scenes.playerView;

import application.App;
import business_logic.services.MP3Player;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import presentation.ui_components.cover_SongInfo.ImageViewPane;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

public class PlayerViewController {

    private MP3Player player;
    private PlayerView root;

    private ControlViewController controlViewController;
    private ViewChangeController viewChangeController;

    Button playerViewButton;
    Button playlistViewButton;

    ImageView coverPic;

    Label songTitleLabel;
    Label artistLabel;

    Label currentTimeLabel;
    ProgressBar songProgressBar;
    Label songLengthLabel;

    Button shuffleButton;
    Button skipBackButton;
    Button playButton;
    Button skipButton;
    Button repeatButton;

    Button muteButton;
    Slider volumeSlider;

    public PlayerViewController(MP3Player player, App app) {

        this.player = player;

        root = new PlayerView();

        this.controlViewController = new ControlViewController(player);
        this.viewChangeController = new ViewChangeController(app);

        initialize();
    }

    /**
     * sollte später vom ControlViewController übernommen werden,
     * um redundanten Code zu vermeiden
     */
    public void initialize() {

        playerViewButton = root.playerViewButton;
        playlistViewButton = root.playlistViewButton;

        coverPic = root.coverPic;

        songTitleLabel = root.songTitleLabel;
        artistLabel = root.artistLabel;

        currentTimeLabel = root.currentTimeLabel;
        songProgressBar = root.songProgressBar;
        songLengthLabel = root.songLengthLabel;

        shuffleButton = root.shuffleButton;
        skipBackButton = root.skipBackButton;
        playButton = root.playButton;
        skipButton = root.skipButton;
        repeatButton = root.repeatButton;

        muteButton = root.muteButton;
        volumeSlider = root.volumeSlider;

        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        /*
         Listener des aktuellen Songs des Players
         Verteilt Cover, Titel, Artist, Songlänge auf verschiedene GUI-Elemente
            Beobachtet gleichzeitig die Laufzeit des Players und berechnet den SongProgress
         */
        player.currentSongProperty().addListener(
                (observableValue, oldV, newV) -> {
                    Image cover = (Image) newV.albumCover().get();

                    int songLength = newV.getLength();
                    String songTitle = newV.getTitle();
                    String artist = newV.getArtist();

                    Platform.runLater(() -> coverPic.setImage(cover));

                    Platform.runLater(() -> songLengthLabel.setText(formatTime(songLength)));
                    Platform.runLater(() -> songTitleLabel.setText(songTitle));
                    Platform.runLater(() -> artistLabel.setText(artist));

                    player.currentTimeProperty().addListener(
                            (currentTimeObservable, oldTime, newTime) -> {
                                int currentTime = newTime.intValue();
                                double songProgress = (double) currentTime / songLength;

                                Platform.runLater(() -> currentTimeLabel.setText(formatTime(currentTime)));
                                Platform.runLater(() -> songProgressBar.setProgress(songProgress));
                            }
                    );
                }
        );

        // Skalierung der progressBar
        songProgressBar.prefWidthProperty().bind(root.widthProperty().divide(2));

        shuffleButton.setOnAction(controlViewController);
        skipBackButton.setOnAction(controlViewController);
        playButton.setOnAction(controlViewController);
        skipButton.setOnAction(controlViewController);
        repeatButton.setOnAction(controlViewController);

        muteButton.setOnAction(controlViewController);
        volumeSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    float volume;
                    volume = newValue.floatValue() / 100;
                    System.out.println(volume);
                    player.setVolume(volume);
                }
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
     * Formatiert die Zeitangaben
     *
     * @param seconds - Zeit in Sekunden
     * @return - formatierte Zeitangabe
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public Pane getRoot() {
        return root;
    }

}
