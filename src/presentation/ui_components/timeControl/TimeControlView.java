package presentation.ui_components.timeControl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class TimeControlView extends HBox {

    Label currentTime;
    Label totalTime;
    ProgressBar progressBar;

    public TimeControlView() {
        currentTime = new Label("Current_Time");
        progressBar = new ProgressBar();
        totalTime = new Label("Total_Time");

        currentTime.setId("current-time");
        progressBar.setId("progressBar");
        totalTime.setId("total-time");

        getChildren().addAll(
                currentTime,
                progressBar,
                totalTime
        );


        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setId("time-control-view");
    }
}
