package presentation.scenes.playlistView;

import application.App;
import business_logic.services.MP3Player;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

import java.io.IOException;

public class PlaylistViewController {

    @FXML
    private BorderPane root;
    private MP3Player player;

    private ControlViewController controlViewController;
    private ViewChangeController viewChangeController;

    @FXML
    Button playerViewButton;
    @FXML
    Button playlistViewButton;

    @FXML
    ListView<String> listView;

    @FXML
    Label songTitleLabel;
    @FXML
    Label artistLabel;

    @FXML
    Label currentTimeLabel;
    @FXML
    ProgressBar songProgressBar;
    @FXML
    Label songLengthLabel;

    @FXML
    Button shuffleButton;
    @FXML
    Button skipBackButton;
    @FXML
    Button playButton;
    @FXML
    Button skipButton;
    @FXML
    Button repeatButton;

    @FXML
    Button muteButton;
    @FXML
    Slider volumeSlider;


    public PlaylistViewController(MP3Player player, App app) {

        this.player = player;

        controlViewController = new ControlViewController(player);
        viewChangeController = new ViewChangeController(app);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PlaylistView.fxml"));
        loader.setController(this);

        try {
            root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         könnte wegfallen, wird automatisch vom FXMLLoader aufgerufen,
         dient hier der besseren Lesbarkeit
         */
        initialize();
    }

    /**
     * Lagert die Funktionalität der Buttons in die entsprechenden Controller aus
     */
    public void initialize() {

        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        /*
         Listener des aktuellen Songs des Players
         Verteilt Cover, Titel, Artist, Songlänge auf verschiedene GUI-Elemente
            Beobachtet gleichzeitig die Laufzeit des Players und berechnet den SongProgress
         */
        player.currentSongProperty().addListener(
                (observableValue, oldV, newV) -> {
//                    Image cover = (Image) newV.albumCover().get();

                    int songLength = newV.getLength();
                    String songTitle = newV.getTitle();
                    String artist = newV.getArtist();

//                    Platform.runLater(() -> coverPic.setImage(cover));

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

        // anders integrieren
        listView.getItems().addAll(
                "Song 1",
                "Song 2",
                "Song 3"
        );

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
