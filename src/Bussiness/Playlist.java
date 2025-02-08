package Bussiness;

import javafx.scene.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Playlist  {
    private String title;
    private List<Track> tracks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Playlist(String title, List<Track> tracks) {
        this.title = title;
        this.tracks = tracks;
    }

    public Playlist(String title, String filename) {
        this.title = title;
        tracks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                addTrack(line);
            }
        } catch (IOException ex) {
            System.out.println("error while reading the file");
        }
    }

    private void addTrack(String line) {
        if (line == null)
            return;
        if (!Character.isUpperCase(line.charAt(0)))
            return;
        if (line.indexOf(":\\") != 1)
            return;
        if (line.indexOf(".mp3", line.length() - 4) == -1)
            return;
        Track newTrack = new Track(line);
        tracks.add(newTrack);
    }

}
