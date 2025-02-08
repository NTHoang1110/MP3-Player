package Presentation;

import Application.HelloApplication;
import Bussiness.MP3Player;
import Bussiness.Playlist;
import Bussiness.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class PlaylistViewController {
    private MP3Player mp3Player;
    private ListView<Track> trackListView;
    private PlaylistView mainView;

    public PlaylistViewController(MP3Player mp3Player) {
        this.mp3Player = mp3Player;
        mainView = new PlaylistView();

        this.trackListView = mainView.getTrackListView();

        initialize();
    }

    public void initialize() {
        Playlist playlist = mp3Player.getPlaylist();
        ObservableList<Track> tracks = FXCollections.observableList(playlist.getTracks());
        trackListView.setItems(tracks);

        trackListView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
            @Override
            public ListCell<Track> call(ListView<Track> trackListView) {
                return new TrackItem();
            }
        });

        trackListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Track>() {
            @Override
            public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
                mp3Player.setCurrentTrack(newValue);
                HelloApplication.switchRoot("PlayerView",mp3Player);
            }
        });
    }

    public Pane getMainView() {
        return mainView;
    }
}
