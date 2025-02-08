package Presentation;

import Bussiness.Track;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;


public class PlaylistView extends BorderPane {
    private ListView<Track> trackListView;

    public PlaylistView() {
        trackListView = new ListView<>();
        this.setCenter(trackListView);
    }

    public ListView<Track> getTrackListView() {
        return trackListView;
    }
}
