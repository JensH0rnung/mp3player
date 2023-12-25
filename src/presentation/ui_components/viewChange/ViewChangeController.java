package presentation.ui_components.viewChange;

import business_logic.services.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ViewChangeController implements EventHandler<ActionEvent> {

    private HBox root;
    private MP3Player player;
    private Stage primaryStage;
    private HashMap<String, Pane> primaryViews;

    @FXML
    Button playerViewbutton;
    @FXML
    Button playlistViewButton;

    public ViewChangeController(MP3Player player, Stage primaryStage, HashMap<String, Pane> primaryViews) {

        this.player = player;
        this.primaryStage = primaryStage;
        this.primaryViews = primaryViews;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewChangeButtons.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();

        switch (sourceButton.getId()) {
            case "playerViewButton":
                System.out.println("Wechsel zum Player-View");
                switchView(primaryViews.get("PlayerView"));
                break;
            case "playlistViewButton":
                System.out.println("Wechsel zum Playlist-View");
                switchView(primaryViews.get("PlaylistView"));
                break;
        }
    }

    /**
     * Wechselt zwischen erstellten Views
     *
     * @param viewName - View, der angezeigt werden soll
     */
    public void switchView(Pane viewName) {
        Scene currentScene = primaryStage.getScene();

        Pane nextView = primaryViews.get(viewName);
        if (nextView != null) {
            currentScene.setRoot(nextView);
        }
    }

    public Pane getRoot() {
        return root;
    }
}
