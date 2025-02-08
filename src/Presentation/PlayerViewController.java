package Presentation;

import Bussiness.MP3Player;
import Application.HelloApplication;
import Bussiness.Track;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayerViewController {
    Pane mainView;

    private Label title;
    private Label text;

    private Button back;
    private Button play;
    private Button skip;
    private Button skipback;
    private Button repeat;
    private Button shuffle;

    private ImageView imageView;

    private Slider slider;
    private Long value;
    private Label end;
    private SimpleIntegerProperty time;
    private MP3Player mp3;

    private Slider volumeSlider;

    public Pane getMainView() {
        return mainView;
    }

    public PlayerViewController(MP3Player mp3) {
        this.mp3 = mp3;
        mainView = new PlayerView();
        mainView.setId("mainPlayerView");

        PlayerView playerView = (PlayerView) mainView;

        this.back = playerView.backButton;

        this.title = playerView.getTitle();
        this.text = playerView.getText();
        this.imageView = playerView.getImageView();

        TimeSlider timeSlider = (TimeSlider) playerView.getSlider();

        this.slider = timeSlider.getSlider();
        this.end = timeSlider.getEnd();
        slider.setValue(0);

        this.time = new SimpleIntegerProperty(0);

        Track currentTrack = mp3.trackProperty().get();

        imageView.setImage(currentTrack.getImage());
        title.setText(currentTrack.getTitle());
        text.setText(currentTrack.getArtist());
        value = currentTrack.getLength();
        end.setText(String.format("%02d:%02d", value/60, value%60));
        slider.setMax(value);
        slider.setValue(0);
        time.setValue(0);

        ControlView controlView = (ControlView) playerView.getControl();
        this.play = controlView.play;
        this.skip = controlView.skip;
        this.skipback = controlView.skipback;
        this.repeat = controlView.repeat;
        this.shuffle = controlView.shuffle;

        VolumeSlider volume = (VolumeSlider) playerView.getVolume();
        this.volumeSlider = volume.getVolumeSlider();
        initialize();
    }

    public void initialize() {
        play.setOnAction(e -> {
                if(mp3.isPlaying()){
                    Platform.runLater(() -> play.setId("play-button"));
                    mp3.pause();
                }else{
                    Platform.runLater(() -> play.setId("pause-button"));
                    mp3.play();
                }
        });


        mp3.timeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {
                    slider.setValue(newValue.intValue());
                });
            }
        });

        mp3.trackProperty().addListener((observableValue, track, currentTrack) -> {
                Platform.runLater(() -> {
                    imageView.setImage(currentTrack.getImage());
                    title.setText(currentTrack.getTitle());
                    text.setText(currentTrack.getArtist());
                    value = currentTrack.getLength();
                    end.setText(String.format("%02d:%02d", value/60, value%60));
                    slider.setMax(value);
                    slider.setValue(0);
                    time.setValue(0);
                });
            }
        );

        skip.setOnAction(e -> {
            mp3.skip();
        });

        skipback.setOnAction(e -> {
            mp3.skipBack();
        });

        repeat.setOnAction(e -> {
            mp3.repeat(!mp3.isRepeated());
            System.out.println(Thread.activeCount());
        });

        shuffle.setOnAction(e -> {
            mp3.shuffle(!mp3.isShuffled());
        });

        back.setOnAction(e -> {
            mp3.pause();
            HelloApplication.switchRoot("PlaylistView",mp3);
        });

        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(50);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mp3.volume(volumeSlider.getValue());
            }
        });
    }
}
