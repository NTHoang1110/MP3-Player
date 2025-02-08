package Presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayerView extends BorderPane {
    Button backButton;
    private Label title;
    private Label text;

    private ImageView imageView;
    private TimeSlider slider;
    private VolumeSlider volume;

    private Pane control;

    public VolumeSlider getVolume() {
        return volume;
    }

    public TimeSlider getSlider() {
        return slider;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Pane getControl() {
        return control;
    }

    public Label getTitle() {
        return title;
    }

    public Label getText() {
        return text;
    }

    public PlayerView() {
        HBox back = new HBox();
        backButton = new Button();
        backButton.getStyleClass().add("button");
        backButton.setId("back-button");
        back.getChildren().add(backButton);
        back.setPadding(new Insets(10, 0, 5, 10));
        this.setTop(back);

        title = new Label("Title");
        title.setId("title");
        text = new Label("Artist");
        text.setId("artist");

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(this.widthProperty().multiply(0.7));
        imageView.fitHeightProperty().bind(this.heightProperty().multiply(0.5));

        slider = new TimeSlider();
        slider.prefWidthProperty().bind(this.widthProperty().multiply(0.7));

        control = new ControlView();

        volume = new VolumeSlider();

        VBox main = new VBox(10);
        main.setAlignment(Pos.CENTER);
        main.getChildren().addAll(title, text, imageView, slider, control, volume);
        main.setSpacing(15);
        VBox.setVgrow(imageView, Priority.ALWAYS);

        this.setCenter(main);
        this.setPadding(new Insets(15));
    }
}
