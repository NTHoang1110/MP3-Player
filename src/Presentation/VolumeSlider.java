package Presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class VolumeSlider extends VBox {
    private Slider volumeSlider;
    private ImageView volumeDown;
    private ImageView volumeUp;

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public VolumeSlider() {
        volumeSlider = new Slider();
        volumeSlider.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
        volumeDown = new ImageView();
        volumeDown.getStyleClass().add("button");
        volumeDown.setId("volume-down");
        volumeUp = new ImageView();
        volumeUp.getStyleClass().add("button");
        volumeUp.setId("volume-up");
        this.getChildren().add(volumeDown);
        this.getChildren().add(volumeSlider);
        this.getChildren().add(volumeUp);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
        this.setMaxWidth(300);
    }
}
