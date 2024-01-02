package presentation.ui_components.playerControls;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;


/**
 * Steuerlogik der PlayerControls, Verknüpfung mit View-Definition in FXML
 */
public class ControlViewController implements EventHandler<ActionEvent> {

    private VBox root;
    private MP3Player player;

//    @FXML
//    Button shuffleButton;
//    @FXML
//    Button skipBackButton;
//    @FXML
//    Button playButton;
//    @FXML
//    Button skipButton;
//    @FXML
//    Button repeatButton;

    public ControlViewController(MP3Player player) {

        this.player = player;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ControlView.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ggf. hier Stylesheet setzen, das allgemeinen Style überschreibt
//        root.getStylesheets().add(getClass().getResource("style_playerControls.css").toExternalForm());
    }

    /**
     * Wird vom PlaylistView- & PlayerViewController angesprochen
     *
     * @param actionEvent - Klick auf Button
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();

        switch (sourceButton.getId()) {
            case "shuffleButton":
                player.shuffle();
                if(player.isOnShuffle()) {
                    sourceButton.getStyleClass().add("activated");
                } else {
                    sourceButton.getStyleClass().remove("activated");
                }
                break;
            case "skipBackButton":
                player.skipBack();
                break;
            case "playButton":
                if(player.isPlaying()) {
                    player.pause();
                    sourceButton.setText("Play");
                } else {
                    player.play();
                    sourceButton.setText("Pause");
//                    Platform.runLater(() -> sourceButton.setText("Pause"));
                    // Starten des Tasks
//                    playTask(sourceButton);
                }
                break;
            case "skipButton":
                player.skip();
                break;
            case "repeatButton":
                player.repeat();
                if(player.isOnRepeat()) {
                    sourceButton.getStyleClass().add("activated");
                } else {
                    sourceButton.getStyleClass().remove("activated");
                }
                break;
            case "muteButton":
                if (!player.isMuted()) {
                    System.out.println("mute");
                    sourceButton.getStyleClass().add("activated");
                } else {
                    System.out.println("unmute");
                    sourceButton.getStyleClass().remove("activated");
                }
                player.mute();
                break;
        }
    }

//    // Methode zur Ausführung der PlayTask
////    private void playTask(Button playButton) {
////        Task<Void> task = new Task<Void>() {
////            @Override
////            protected Void call() {
////                player.play();
////
//////                while (player.isPlaying()) {
//////                    try {
//////                        Thread.sleep(10000);
//////                    } catch (InterruptedException e) {
//////                        throw new RuntimeException(e);
//////                    }
//////                }
////                return null;
////            }
////        };
////
////        task.setOnSucceeded(workerStateEvent -> {
//////            Platform.runLater(() -> playButton.setText("Play"));
////            playButton.setText("Play - CVC");
////
////
////            // Füge hier weitere Aktionen hinzu, die nach dem Abspielen ausgeführt werden sollen
////        });
////
////        new Thread(task).start();
////    }

    public Pane getRoot() {
        return root;
    }
}
