package presentation.scenes.playlistView;

import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import presentation.ui_components.playerControls.ControlView;
import presentation.ui_components.title_artist.Title_Artist;
import presentation.ui_components.viewChange.ViewChangeView;

public class PlaylistView extends AnchorPane {

    ControlView controlView;
    ViewChangeView viewChangeView;
    ListView<String> songsInPlaylist;

    VBox title_artist;
    VBox vbox_songList_songData;

    public PlaylistView() {

        // View-Wechsel oben & zentriert
        viewChangeView = new ViewChangeView();
        AnchorPane.setTopAnchor(viewChangeView, 0.0);
        AnchorPane.setLeftAnchor(viewChangeView, 0.0);
        AnchorPane.setRightAnchor(viewChangeView, 0.0);

        songsInPlaylist = new ListView<>();
        songsInPlaylist.getItems().addAll("Song1", "Song2", "Song3");

        title_artist = new Title_Artist();

        vbox_songList_songData = new VBox();
        vbox_songList_songData.getChildren().addAll(
                songsInPlaylist,
                title_artist
        );

        // VBox im Center der AnchorPane
        AnchorPane.setTopAnchor(vbox_songList_songData, 40.0);
        AnchorPane.setLeftAnchor(vbox_songList_songData, 10.0);
        AnchorPane.setRightAnchor(vbox_songList_songData, 10.0);

        // PlayerControls am unteren Rand
        controlView = new ControlView();
        AnchorPane.setBottomAnchor(controlView, 0.0);
        AnchorPane.setLeftAnchor(controlView, 0.0);
        AnchorPane.setRightAnchor(controlView, 0.0);

        getChildren().addAll(
                viewChangeView,
                vbox_songList_songData,
                controlView);

        setId("playlist-view");
        getStyleClass().add("primary-views");
    }
}
