package presentation.scenes.playlistView;

import application.App;
import business_logic.data.Playlist;
import business_logic.data.Song;
import business_logic.services.MP3Player;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;
//import presentation.ui_components.songList.SongListCell;
import presentation.ui_components.viewChange.ViewChangeController;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistViewController {

    @FXML
    private BorderPane root;
    private MP3Player player;
    private Playlist actPlaylist;
    private ArrayList<Song> songsInPlaylist;

    private ControlViewController controlViewController;
    private ViewChangeController viewChangeController;

    @FXML
    Button playerViewButton;
    @FXML
    Button playlistViewButton;

    @FXML
    Label playlistNameLabel;
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
    ToggleButton shuffleButton;
    @FXML
    Button skipBackButton;
    @FXML
    Button playButton;
    @FXML
    Button skipButton;
    @FXML
    ToggleButton repeatButton;

    @FXML
    ToggleButton muteButton;
    @FXML
    Slider volumeSlider;


    public PlaylistViewController(MP3Player player, App app) {

        this.player = player;
        this.actPlaylist = player.getActPlaylist();
        this.songsInPlaylist = actPlaylist.getSongs();

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
         fällt weg, wird automatisch vom FXMLLoader aufgerufen,
         */
//        initialize();
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

                    int songLength = newV.getLength();
                    String songTitle = newV.getTitle();
                    String artist = newV.getArtist();

                    Platform.runLater(() -> songLengthLabel.setText(formatTime(songLength)));
                    Platform.runLater(() -> songTitleLabel.setText(songTitle));
                    Platform.runLater(() -> artistLabel.setText(artist));
//                    listView.getSelectionModel().select(String.valueOf(newV));

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



        playlistNameLabel.setText(player.getActPlaylist().getName());

        // Formatierung
//        listView.setCellFactory(SongListCell.forListView());

        listView.getItems().clear();
        for(Song song: songsInPlaylist) {
            listView.getItems().add(song.getTitle());
        }
        // spielt ausgewählten Song ab
        listView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldV, newV) -> {
                    if(newV != null) {

                        Song selectedSong = getSongByTitle(newV);
                        String selectedSongFilePath = selectedSong.getFilePath();

                        player.incActPositionInPlayedSongs();
                        player.play(selectedSongFilePath);
                        player.countTime();
                    }
                })
        );


        songProgressBar.prefWidthProperty().bind(root.widthProperty().divide(2));

        shuffleButton.setOnAction(controlViewController);
        skipBackButton.setOnAction(controlViewController);
        playButton.setOnAction(controlViewController);
        skipButton.setOnAction(controlViewController);
        repeatButton.setOnAction(controlViewController);

//        playButton.textProperty().bind(playButton.textProperty());

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
     * sucht Song in Playlist
     * @param title - Titel des Songs, der gesucht wird
     * @return - Song mit angegebenem Titel
     */
    private Song getSongByTitle(String title) {
        for (Song song : songsInPlaylist) {
            if (song.getTitle().equals(title)) {
                return song;
            }
        }
        return null;
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
