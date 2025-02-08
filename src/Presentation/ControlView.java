package Presentation;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {

    Button play = new Button("");
    Button skip = new Button("");
    Button skipback = new Button("");
    Button shuffle = new Button("");
    Button repeat = new Button("");

    public ControlView() {
        play.getStyleClass().add("button");
        skip.getStyleClass().add("button");
        skipback.getStyleClass().add("button");
        shuffle.getStyleClass().add("button");
        repeat.getStyleClass().add("button");

        play.setId("play-button");
        skip.setId("skip-button");
        skipback.setId("skipBack-button");
        shuffle.setId("shuffle-button");
        repeat.setId("repeat-button");


        this.getChildren().addAll(repeat, skipback, play, skip, shuffle);
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);

    }
}
