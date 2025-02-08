package Application;

import Bussiness.MP3Player;
import Presentation.PlayerViewController;
import Presentation.PlaylistViewController;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    private static Scene scene;
    private static Pane root;
    private static PlayerViewController playerController;
    private static PlaylistViewController playlistController;

    @Override
    public void start(Stage stage) {
        MP3Player mp3 = new MP3Player();
        playlistController = new PlaylistViewController(mp3);
        playerController = new PlayerViewController(mp3);
        root = playerController.getMainView();
        scene = new Scene(root,700,700);
        switchRoot("PlayerView", mp3);
        scene.getStylesheets().add("Presentation/presentation.css");
        stage.setScene(scene);
        stage.setTitle("Music Player");
        Image icon = new Image("/Assets/icon.png");
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void switchRoot(String name, MP3Player mp3)  {
        switch (name) {
            case "PlayerView":
                playerController = new PlayerViewController(mp3);
                scene.setRoot(playerController.getMainView());
                break;
            case "PlaylistView":
                playlistController = new PlaylistViewController(mp3);
                scene.setRoot(playlistController.getMainView());
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}