package presentation.ui_components.title_artist;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Title_Artist extends VBox {

    Label title;
    Label artist;

    public Title_Artist() {
        title = new Label("Titel");
        artist = new Label("Interpret");

        getChildren().addAll(
                title,
                artist);

        setAlignment(Pos.CENTER_LEFT);
        setId("title-artist");
    }
}
