package Bussiness;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class Track {
    private String title, artist, album, mp3FilePath, trackID;
    private byte[] imageData;

    private long length;

    public Track(String title, String artist, long length, String album, String mp3FilePath, String trackID) {
        this.title = title;
        this.artist = artist;
        this.length = length;
        this.album = album;
        this.mp3FilePath = mp3FilePath;
        this.trackID = trackID;
    }

    public Track(String mp3FilePath) {
        this.mp3FilePath = mp3FilePath;
        try {
            Mp3File mp3file = new Mp3File(mp3FilePath);
            ID3v1 idTag;
            if(mp3file.hasId3v1Tag()){
                idTag = mp3file.getId3v1Tag();
            }
            else{
                idTag = mp3file.getId3v2Tag();
            }
            trackID = idTag.getTrack();
            artist = idTag.getArtist();
            title = idTag.getTitle();
            album = idTag.getAlbum();
            length = mp3file.getLengthInSeconds();

            ID3v2 idTag2 = mp3file.getId3v2Tag();
            imageData = idTag2.getAlbumImage();


        } catch (Exception e) {
            System.err.println("Not able to do that.");
        }
    }

    public String gettrackID() {
        return trackID;
    }

    public void settrackID(String trackID) {
        this.trackID = trackID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMp3FilePath() {
        return mp3FilePath;
    }

    public void setMp3FilePath(String mp3FilePath) {
        this.mp3FilePath = mp3FilePath;
    }

    public long getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Image getImage(){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
        Image image = new Image(byteArrayInputStream);
        return image;
    }

}
