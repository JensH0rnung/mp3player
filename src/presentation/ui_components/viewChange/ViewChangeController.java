package presentation.ui_components.viewChange;

import application.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewChangeController implements EventHandler<ActionEvent> {

    private HBox root;
    private App app;

//    @FXML
//    Button playerViewbutton;
//    @FXML
//    Button playlistViewButton;

    public ViewChangeController(App app) {

        this.app = app;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ViewChangeControl.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Ruft die switchView() der App-Klasse auf, um den View zu wechseln
     * @param actionEvent - Klick auf Button
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();

        switch (sourceButton.getId()) {
            case "playerViewButton":
                app.switchView("PlayerView");
                break;
            case "playlistViewButton":
                app.switchView("PlaylistView");
                break;
        }
    }

    public Pane getRoot() {
        return root;
    }
}
