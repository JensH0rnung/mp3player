package presentation.ui_components.playerControls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Verwaltet Darstellung & Struktur der PlayerControls
 *
 * Erstellt Container "ControlStrip", der am unteren Rand des Players stehen soll
 * HorizontalBox, da Steuerungs-Buttons nebeneinander angeordnet sein sollen
 */
public class ControlView extends HBox {

    public Button playButton;
    public Button skipButton;
    public Button skipBackButton;
    public Button shuffleButton;
    public Button repeatButton;

    public ControlView() {

        shuffleButton = new Button("Shuffle");
        skipBackButton = new Button("Back");
        playButton = new Button("Play");
        skipButton = new Button("Skip");
        repeatButton = new Button("Repeat");

        // Hinzufügen zur HBox (umhüllender Container)
        getChildren().addAll(
                shuffleButton,
                skipBackButton,
                playButton,
                skipButton,
                repeatButton
        );

        // Styling Shuffle-Button
        Circle circle = new Circle();
        circle.setRadius(10f);
        shuffleButton.setShape(circle);

        // Styling der HBox
        setAlignment(Pos.CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
    }
}
