package presentation.ui_components.cover;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cover extends ImageView {

    Image cover;

    public Cover() {

        cover = new Image(getClass().getResourceAsStream("cover1.jpeg"));

        setImage(cover);
        setFitHeight(380);
        setFitWidth(380);
    }
}
