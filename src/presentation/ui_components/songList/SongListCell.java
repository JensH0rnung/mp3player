//package presentation.ui_components.songList;
//
//import business_logic.data.Song;
//import javafx.scene.control.ListCell;
//import javafx.scene.control.ListView;
//import javafx.util.Callback;
//
//public class SongListCell extends ListCell<Song> {
//
//    @Override
//    protected void updateItem(Song song, boolean empty) {
//        super.updateItem(song, empty);
//
//        if (empty || song == null) {
//            setText(null);
//        } else {
//            // Formatierung für Songtitel und Künstler
//            String formattedText = String.format("%s - %s", song.getTitle(), song.getArtist());
//            setText(formattedText);
//        }
//    }
//
//    // Methode, um eine Zelle zu erstellen (wird vom ListView aufgerufen)
//    public static Callback<ListView<String>, ListCell<String>> forListView() {
//        return param -> {
//            SongListCell songListCell = new SongListCell();
//            return songListCell;
//        };
//    }
//}
