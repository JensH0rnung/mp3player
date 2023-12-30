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
import presentation.ui_components.cover.ImageViewPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PlayerView extends BorderPane {

    // ViewChangeControls
    HBox viewChangeButtons;
        Button playerViewButton;
        Button playlistViewButton;

    // Cover_SongInfo
    VBox cover_SongInfo;
        ImageView coverPic;
        ImageViewPane coverView;
        VBox songInfo;
            Label songTitle;
            Label artist;

    // ControlView
    VBox controlView;
        HBox timeControl;
            Label currentTimeLabel;
            ProgressBar songProgress;
            Label songLengthLabel;
        HBox controlButtons;
            Button shuffleButton;
            Button skipBackButton;
            Button playButton;
            Button skipButton;
            Button repeatButton;
        HBox volumeControl;
            Button muteButton;
            Slider volumeSlider;


    public PlayerView() {

        // FXML-Dateien der SubViews
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
//        setCenter(cover_SongInfo);
//        setBottom(controlView_fxml);


        viewChangeButtons();
        cover_SongInfo();
        controlView();

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

    public void cover_SongInfo() {
        cover_SongInfo = new VBox();

            coverPic = new ImageView();
            try {
                coverPic.setImage(new Image(new FileInputStream("src/assets/songCover/cover1.jpeg")));
            } catch (FileNotFoundException e) {
                System.out.println("Bild nicht gefunden");
            }
            coverView = new ImageViewPane(coverPic);

            songInfo = new VBox();
                songTitle = new Label("SongTitle");
                artist = new Label("Interpret");

            songInfo.getChildren().addAll(
                    songTitle,
                    artist
            );

        cover_SongInfo.setPrefHeight(200);
        cover_SongInfo.setPrefWidth(100);

        coverView.setPrefHeight(552);
        coverView.setPrefWidth(475);

        VBox.setMargin(coverView, new Insets(0, 20, 0, 20));

        songInfo.setPrefHeight(14);
        songInfo.setPrefWidth(600);
        songInfo.setSpacing(3);

        VBox.setMargin(songInfo, new Insets(5, 10, 0, 20));

        VBox.setMargin(songTitle, new Insets(0, 0, 2, 0));

        VBox.setVgrow(coverView, Priority.ALWAYS);
        VBox.setVgrow(songTitle, Priority.NEVER);
        VBox.setVgrow(artist, Priority.NEVER);

        coverView.setId("coverView");
        songInfo.setId("songInfo");
        songTitle.setId("songTitle");
        artist.setId("artist");

        cover_SongInfo.getChildren().addAll(
                coverView,
                songInfo
        );

        cover_SongInfo.setId("cover_songInfo");
    }

    public VBox controlView() {

        controlView = new VBox();

        controlView.setAlignment(Pos.CENTER);

        // TimeControl
        timeControl = new HBox();
            currentTimeLabel = new Label("Current_Time");
            songProgress = new ProgressBar();
            songLengthLabel = new Label("Total_Time");

        timeControl.setAlignment(Pos.CENTER);
        timeControl.setPrefHeight(13);
        timeControl.setPrefWidth(475);
        timeControl.setSpacing(10);

        VBox.setMargin(timeControl, new Insets(5, 0, 0, 0));

        VBox.setVgrow(timeControl, Priority.NEVER);

        songProgress.setPrefHeight(10);
        songProgress.setPrefWidth(200);

        HBox.setHgrow(currentTimeLabel, Priority.NEVER);
        HBox.setHgrow(songProgress, Priority.ALWAYS);
        HBox.setHgrow(songLengthLabel, Priority.NEVER);

        currentTimeLabel.setId("currentTimeLabel");
        songProgress.setId("songProgress");
        songLengthLabel.setId("songLengthLabel");

        timeControl.getChildren().addAll(
          currentTimeLabel,
          songProgress,
          songLengthLabel
        );

        // ControlButtons
        controlButtons = new HBox();
            shuffleButton = new Button("Shuffle");
            skipBackButton = new Button("Back");
            playButton = new Button("Play");
            skipButton = new Button("Skip");
            repeatButton = new Button("Repeat");

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
            muteButton = new Button("Mute");
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
