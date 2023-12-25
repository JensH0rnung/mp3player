package presentation.scenes.playerView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.ui_components.cover.ImageViewPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class PlayerView extends BorderPane {

    // Teil-Views
//    ControlView controlView;
//    ViewChangeView viewChangeView;
    ImageView coverView;

    VBox title_artist;
    VBox vbox_cover_songData;

    public PlayerView() {

        // View-Wechsel oben & zentriert
//        viewChangeView = new ViewChangeView();
//        this.setTop(viewChangeView);

        // Cover
        coverView = new ImageView();

        try {
            coverView.setImage(new Image(new FileInputStream("assets/coverView/cover2.jpeg")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageViewPane coverViewPane = new ImageViewPane(coverView);
        this.setCenter(coverViewPane);

        vbox_cover_songData = new VBox();
        vbox_cover_songData.getChildren().addAll(
//                coverViewPane,
//                title_artist
        );
        vbox_cover_songData.setId("playerview-main-vbox");

        // VBox im Center der BorderPane
        this.setCenter(vbox_cover_songData);

        // PlayerControls am unteren Rand & zentriert
//        controlView = new ControlView();
//        this.setBottom(controlView);


        setId("player-view");
        getStyleClass().add("primary-views");
    }
}
