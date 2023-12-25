//package presentation.ui_components.playerControls;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ProgressBar;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
///**
// * Verwaltet Darstellung & Struktur der PlayerControls
// *
// * Erstellt Container "ControlStrip", der am unteren Rand des Players stehen soll
// * HorizontalBox, da Steuerungs-Buttons nebeneinander angeordnet sein sollen
// */
//public class ControlView extends VBox {
//
//    public Button playButton;
//    public Button skipButton;
//    public Button skipBackButton;
//    public Button shuffleButton;
//    public Button repeatButton;
//
//    HBox controlButtons;
//    HBox timeControl;
//    HBox volumeControl;
//
//    Image playIcon;
//    ImageView playIconView;
//
//    Label currentTime;
//    Label totalTime;
//    ProgressBar progressBar;
//
//    public ControlView() {
//
//        controlButtons = new HBox();
//        timeControl = new HBox();
//        volumeControl = new HBox();
//
////        try {
////            playIcon = new Image(new FileInputStream("assets/icons/playButton/play-circle-normal.png"));
////        } catch (FileNotFoundException e) {
////            System.out.println("Icon-Datei nicht gefunden");
////        }
//
//        playIconView = new ImageView(playIcon);
//
//        shuffleButton = new Button("Shuffle");
//        skipBackButton = new Button("Back");
//        playButton = new Button();
//        skipButton = new Button("Skip");
//        repeatButton = new Button("Repeat");
//
//        // Hinzufügen zur oberen HBox
//        controlButtons.getChildren().addAll(
//                shuffleButton,
//                skipBackButton,
//                playButton,
//                skipButton,
//                repeatButton
//        );
//
//        controlButtons.getStyleClass().add("user-controls");
//        controlButtons.setId("control-buttons");
//
//        // TimeControl
//        currentTime = new Label("Current_Time");
//        progressBar = new ProgressBar();
//        totalTime = new Label("Total_Time");
//
//        // Hinzufügen zur unteren HBox
//        timeControl.getChildren().addAll(
//                currentTime,
//                progressBar,
//                totalTime
//        );
//
//        timeControl.setId("time-control");
//        timeControl.getStyleClass().add("user-controls");
//
//        // VolumeControl
//
//
//        // Hinzufügen zur umhüllenden VBox
//        getChildren().addAll(
//                timeControl,
//                controlButtons
//        );
//
//        shuffleButton.setId("shuffle-button");
//        skipBackButton.setId("skip-back-button");
//        playButton.setId("play-button");
//        skipButton.setId("skip-button");
//        repeatButton.setId("repeat-button");
//
//        shuffleButton.getStyleClass().add("icon-button");
//        skipBackButton.getStyleClass().add("icon-button");
//        playButton.getStyleClass().add("play");
//        playButton.getStyleClass().add("icon-button");
//        skipButton.getStyleClass().add("icon-button");
//        repeatButton.getStyleClass().add("icon-button");
//
//        // Layouten der VBox
//        setId("control-view");
//    }
//}
