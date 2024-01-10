package presentation.scenes.playerView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import presentation.ui_components.coverImageWrapperClass.ImageViewPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PlayerView extends BorderPane {

    private Image defaultCover;

    // ViewChangeControls
    HBox viewChangeButtons;
        Button playerViewButton;
        Button playlistViewButton;

    // Cover_SongInfo
    VBox cover_SongInfo;
        ImageView coverPic;
        ImageViewPane coverView;
        VBox songInfo;
            Label songTitleLabel;
            Label artistLabel;

    // ControlView
    VBox controlView;
        HBox timeControl;
            Label currentTimeLabel;
            ProgressBar songProgressBar;
            Label songLengthLabel;
        HBox controlButtons;
            ToggleButton shuffleButton;
            Button skipBackButton;
            Button playButton;
            Button skipButton;
            ToggleButton repeatButton;
        HBox volumeControl;
            ToggleButton muteButton;
            Slider volumeSlider;


    public PlayerView() {

        /*
         FXML-Dateien der SubViews laden - entspricht dem modularen Prinzip
         Nach meinem Verständnis sollte mind. 1 "Haupt-View" programmatisch erstellt werden, ohne FXML, daher sind
         hier alle UI_Elemente aufgeführt. Zur besseren Übersicht dienen Methoden.
         */

//        loader = new FXMLLoader(getClass().getResource("/presentation/ui_components/viewChange/ViewChangeControl.fxml"));
//        try {
//            viewChangeView_fxml = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        loader = new FXMLLoader(getClass().getResource("/presentation/ui_components/playerControls/ControlView.fxml"));
//        try {
//            controlView_fxml = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        setTop(viewChangeView_fxml);
//        setBottom(controlView_fxml);

        try {
            defaultCover = new Image(new FileInputStream("src/assets/songCover/dummy_cover.jpeg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        viewChangeButtons = viewChangeButtons();
        cover_SongInfo = cover_SongInfo();
        controlView = controlView();

        setTop(viewChangeButtons);
        setCenter(cover_SongInfo);
        setBottom(controlView);


        setId("player-view");
        getStyleClass().add("primary-views");
    }

    public HBox viewChangeButtons() {

        viewChangeButtons = new HBox();
            playerViewButton = new Button("Player");
            playlistViewButton = new Button("Playlist");

        viewChangeButtons.setAlignment(Pos.CENTER);
        viewChangeButtons.setPrefHeight(40);
        viewChangeButtons.setPrefWidth(600);
        viewChangeButtons.setSpacing(10);

        playerViewButton.setPrefHeight(40);
        playerViewButton.setMinHeight(40);
        playerViewButton.setPrefWidth(100);
        playlistViewButton.setPrefHeight(40);
        playlistViewButton.setMinHeight(40);
        playlistViewButton.setPrefWidth(100);

        HBox.setMargin(playerViewButton, new Insets(3, 0, 5, 0));
        HBox.setMargin(playlistViewButton, new Insets(3, 0, 5, 0));

        HBox.setHgrow(playerViewButton, Priority.NEVER);
        HBox.setHgrow(playlistViewButton, Priority.NEVER);

        playerViewButton.setId("playerViewButton");
        playlistViewButton.setId("playlistViewButton");

        viewChangeButtons.setId("viewChangeControl");

        viewChangeButtons.getChildren().addAll(
                playerViewButton,
                playlistViewButton
        );

        return viewChangeButtons;
    }

    public VBox cover_SongInfo() {
        cover_SongInfo = new VBox();

            coverPic = new ImageView();
            coverPic.setImage(defaultCover);
            coverView = new ImageViewPane(coverPic);

            songInfo = new VBox();
                songTitleLabel = new Label("SongTitle");
                artistLabel = new Label("Artist");

            songInfo.getChildren().addAll(
                    songTitleLabel,
                    artistLabel
            );

        cover_SongInfo.setPrefHeight(200);
        cover_SongInfo.setPrefWidth(100);

        coverView.setPrefHeight(552);
        coverView.setPrefWidth(475);

        VBox.setMargin(coverView, new Insets(5, 20, 2, 20));

        songInfo.setPrefHeight(14);
        songInfo.setPrefWidth(600);
        songInfo.setSpacing(3);

        VBox.setMargin(songInfo, new Insets(0, 10, 2, 20));

        VBox.setMargin(songTitleLabel, new Insets(2, 0, 2, 0));

        VBox.setVgrow(coverView, Priority.ALWAYS);
        VBox.setVgrow(songTitleLabel, Priority.NEVER);
        VBox.setVgrow(artistLabel, Priority.NEVER);

        coverView.setId("coverView");
        songInfo.setId("songInfo");
        songTitleLabel.setId("songTitleLabel");
        artistLabel.setId("artist");

        cover_SongInfo.getChildren().addAll(
                coverView,
                songInfo
        );

        cover_SongInfo.setId("cover_songInfo");

        return cover_SongInfo;
    }

    public VBox controlView() {

        controlView = new VBox();

        controlView.setAlignment(Pos.CENTER);

        // TimeControl
        timeControl = new HBox();
            currentTimeLabel = new Label("00:00");
            songProgressBar = new ProgressBar();
            songLengthLabel = new Label("00:00");

        timeControl.setAlignment(Pos.CENTER);
        timeControl.setPrefHeight(13);
        timeControl.setPrefWidth(475);
        timeControl.setSpacing(10);

        VBox.setMargin(timeControl, new Insets(5, 0, 0, 0));

        VBox.setVgrow(timeControl, Priority.NEVER);

        songProgressBar.setPrefHeight(10);
        songProgressBar.setPrefWidth(200);

        songProgressBar.getStyleClass().add("songProgressBar");

        HBox.setHgrow(currentTimeLabel, Priority.NEVER);
        HBox.setHgrow(songProgressBar, Priority.ALWAYS);
        HBox.setHgrow(songLengthLabel, Priority.NEVER);

        currentTimeLabel.setId("currentTimeLabel");
        songProgressBar.setId("songProgressBar");
        songLengthLabel.setId("songLengthLabel");

        timeControl.getChildren().addAll(
            currentTimeLabel,
            songProgressBar,
            songLengthLabel
        );                                                       

        // ControlButtons
        controlButtons = new HBox();
            shuffleButton = new ToggleButton("Shuffle");
            skipBackButton = new Button("Back");
            playButton = new Button("Play");
            skipButton = new Button("Skip");
            repeatButton = new ToggleButton("Repeat");

        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.prefHeight(52);
        controlButtons.prefWidth(476);
        controlButtons.setSpacing(10);
        controlButtons.setPadding(new Insets(12.5));    // keine Ahnung warum das hier 2.5 mehr sein muss als beim PlaylistView.fxml

        VBox.setVgrow(controlButtons, Priority.NEVER);

        HBox.setHgrow(shuffleButton, Priority.NEVER);
        HBox.setHgrow(skipBackButton, Priority.NEVER);
        HBox.setHgrow(playButton, Priority.NEVER);
        HBox.setHgrow(skipButton, Priority.NEVER);
        HBox.setHgrow(repeatButton, Priority.NEVER);

        shuffleButton.setId("shuffleButton");
        skipBackButton.setId("skipBackButton");
        playButton.setId("playButton");
        skipButton.setId("skipButton");
        repeatButton.setId("repeatButton");

        controlButtons.getChildren().addAll(
          shuffleButton,
          skipBackButton,
          playButton,
          skipButton,
          repeatButton
        );

        // VolumeControl
        volumeControl = new HBox();
            muteButton = new ToggleButton("Mute");
            volumeSlider = new Slider();

        volumeControl.setAlignment(Pos.CENTER);
        volumeControl.setPrefHeight(9);
        volumeControl.setPrefWidth(475);
        volumeControl.setSpacing(5);

        VBox.setVgrow(volumeControl, Priority.NEVER);

        muteButton.setId("muteButton");
        volumeSlider.setId("volumeSlider");

        volumeSlider.setPrefHeight(16);
        volumeSlider.setPrefWidth(108);
        volumeSlider.setValue(100);

        HBox.setHgrow(muteButton, Priority.NEVER);
        HBox.setHgrow(volumeSlider, Priority.NEVER);

        volumeControl.getChildren().addAll(
                muteButton,
                volumeSlider
        );

        VBox.setMargin(volumeControl, new Insets(0, 0, 5, 0));

        controlView.getChildren().addAll(
                timeControl,
                controlButtons,
                volumeControl
        );

        controlView.setId("controlView");

        return controlView;
    }

}
