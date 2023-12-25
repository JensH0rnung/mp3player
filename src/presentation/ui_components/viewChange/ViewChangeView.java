//package presentation.ui_components.viewChange;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//
//public class ViewChangeView extends HBox implements EventHandler<ActionEvent> {
//
//    public Button playerViewButton;
//    public Button playlistViewButton;
//
//    private ViewChangeController viewChangeViewController;
//
//    public ViewChangeView() {
//
//        playerViewButton = new Button("Player");
//        playlistViewButton = new Button("Playlist");
//
//        playerViewButton.setId("player-view-button");
//        playlistViewButton.setId("playlist-view-button");
//
////        viewChangeViewController = new ViewChangeViewController(player);
//
////        playerViewButton.setOnAction();
//        playlistViewButton.setOnAction(this);
//
//        getChildren().addAll(playerViewButton, playlistViewButton);
//
//        setAlignment(Pos.CENTER);
//        setSpacing(10);
//        setPadding(new Insets(2));
//
//        setId("view-change-view");
//    }
//
//
//    @Override
//    public void handle(ActionEvent actionEvent) {
//
//    }
//}
