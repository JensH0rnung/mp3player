package presentation.scenes.playlistView;

import application.App;
import business_logic.services.MP3Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import presentation.ui_components.playerControls.ControlViewController;
import presentation.ui_components.viewChange.ViewChangeController;

import java.io.IOException;

public class PlaylistViewController {

    private BorderPane root;

    @FXML
    Button playerViewButton;
    @FXML
    Button playlistViewButton;
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
    ListView<String> listView;

    private ControlViewController controlViewController;
    private ViewChangeController viewChangeController;

    public PlaylistViewController(MP3Player player, App app) {

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
         Aufruf könnte wegfallen, wird automatisch vom FXMLLoader aufgerufen
         Dient hier der besseren Lesbarkeit
         */
        initialize();
    }

    /**
     * Lagert die Funktionalität der Buttons in die entsprechenden Controller aus
     */
    public void initialize() {

        playerViewButton.setOnAction(viewChangeController);
        playlistViewButton.setOnAction(viewChangeController);

        shuffleButton.setOnAction(controlViewController);
        skipBackButton.setOnAction(controlViewController);
        playButton.setOnAction(controlViewController);
        skipButton.setOnAction(controlViewController);
        repeatButton.setOnAction(controlViewController);

        listView.getItems().addAll(
                "Song 1",
                "Song 2",
                "Song 3"
        );
    }

    public Pane getRoot() {
        return root;
    }
}
