package presentation.scenes.playerView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.ui_components.cover.Cover;
import presentation.ui_components.playerControls.ControlView;
import presentation.ui_components.timeControl.TimeControlView;
import presentation.ui_components.title_artist.Title_Artist;
import presentation.ui_components.viewChange.ViewChangeView;


public class PlayerView extends AnchorPane {

    ControlView controlView;
    ViewChangeView viewChangeView;
    Cover cover;

    VBox title_artist;
    VBox vbox_cover_songData;
    HBox timeControl;

    public PlayerView() {

        // View-Wechsel oben & zentriert
        viewChangeView = new ViewChangeView();
        AnchorPane.setTopAnchor(viewChangeView, 0.0);
        AnchorPane.setLeftAnchor(viewChangeView, 0.0);
        AnchorPane.setRightAnchor(viewChangeView, 0.0);

        cover = new Cover();
        title_artist = new Title_Artist();

        vbox_cover_songData = new VBox();
        vbox_cover_songData.getChildren().addAll(
                cover,
                title_artist
        );
        vbox_cover_songData.setId("playerview-main-vbox");

        // VBox im Center der BorderPane
        AnchorPane.setTopAnchor(vbox_cover_songData, 40.0);
        AnchorPane.setLeftAnchor(vbox_cover_songData, 10.0);
        AnchorPane.setRightAnchor(vbox_cover_songData, 10.0);

        // PlayerControls am unteren Rand & zentriert
        controlView = new ControlView();
        AnchorPane.setBottomAnchor(controlView, 0.0);
        AnchorPane.setLeftAnchor(controlView, 0.0);
        AnchorPane.setRightAnchor(controlView, 0.0);

        timeControl = new TimeControlView();
        // getHeight -> 0.0 momentan
        AnchorPane.setBottomAnchor(timeControl, controlView.getHeight() + 60);
        AnchorPane.setLeftAnchor(timeControl, 0.0);
        AnchorPane.setRightAnchor(timeControl, 0.0);

        getChildren().addAll(
                viewChangeView,
                vbox_cover_songData,
                timeControl,
                controlView
        );


        setId("player-view");
        getStyleClass().add("primary-views");
    }
}
