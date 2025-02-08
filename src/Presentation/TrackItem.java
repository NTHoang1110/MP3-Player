package Presentation;

import Bussiness.Track;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackItem extends ListCell<Track> {
    private Label trackName;
    private Label artist;

    private VBox pane;
    private HBox newPane;
    private ImageView image;

    public TrackItem(){
        newPane = new HBox();
        pane = new VBox();
        trackName = new Label();
        trackName.getStyleClass().add("trackName");
        artist = new Label();

        pane.getChildren().addAll(trackName, artist);
        pane.setAlignment(Pos.CENTER_LEFT);

        image = new ImageView();
        image.setFitHeight(50);
        image.setFitWidth(50);

        newPane.getChildren().addAll(image,pane);
        newPane.setPadding(new Insets(5,5,5,5));
        newPane.setSpacing(10);
        newPane.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public void updateItem(Track item, boolean empty) {
        super.updateItem(item, empty);

        if(!empty){
            trackName.setText(item.getTitle());
            artist.setText(item.getArtist());
            image.setImage(item.getImage());

            this.setGraphic(newPane);
        }else{
            this.setGraphic(null);
        }
    }
}
