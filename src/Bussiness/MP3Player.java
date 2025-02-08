package Bussiness;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MP3Player {
    private final SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private Playlist playlistToPlay;
    private Track currentTrack;
    private SimpleIntegerProperty time = new SimpleIntegerProperty();
    private SimpleObjectProperty<Track> track = new SimpleObjectProperty<>();

    private Thread timeThread;

    public boolean isPlaying() {
        return playing;
    }

    public void setTime() {
        time.set(audioPlayer.position());
    }

    public SimpleIntegerProperty timeProperty() {return time;}

    public SimpleObjectProperty<Track> trackProperty() {return track;}

    private boolean playing = false;

    private boolean shuffled = false;
    private boolean repeated;

    public MP3Player() {
        setPlaylist(new PlaylistManager().getPlaylist(0));
        track.set(currentTrack);
        minim = new SimpleMinim();
        audioPlayer = minim.loadMP3File(currentTrack.getMp3FilePath());
    }

    public MP3Player(Playlist playlist) {
        setPlaylist(playlist);
        minim = new SimpleMinim();
        audioPlayer = minim.loadMP3File(currentTrack.getMp3FilePath());
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void play(String fileName) {
        audioPlayer = minim.loadMP3File(fileName);
        audioPlayer.play();
    }

    public void play() {
        playing = true;
        track.set(currentTrack);
        if(audioPlayer != null){
            audioPlayer.pause();
        }
        Thread playThread = new Thread (()-> audioPlayer.play());
        playThread.setDaemon(true);
        playThread.start();

        if (playing) {
            if (timeThread != null && timeThread.isAlive()) {
                timeThread.interrupt();
            }

            timeThread = new Thread(() -> {
//                int temp = time.getValue();
//                while (playing || !Thread.currentThread().isInterrupted()) {
//                    time.setValue(time.getValue() + 1);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                    if (temp == time.getValue() && !Thread.currentThread().isInterrupted()) {
//                        skip();
//                    }
//                    temp = time.getValue();
//                }
                while(audioPlayer.isPlaying()){
                    time.setValue(audioPlayer.position()/1000);
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
                if(playing && !Thread.currentThread().isInterrupted()){
                    skip();
                }

            });
            timeThread.start();
        }
    }

    public void pause() {
        playing = false;
        audioPlayer.pause();
    }

    public synchronized void skip() {
        int nextIndex = playlistToPlay.getTracks().indexOf(currentTrack);
        if (!shuffled) {
            nextIndex++;
            if (nextIndex >= playlistToPlay.getTracks().size()) {
                nextIndex = nextIndex - playlistToPlay.getTracks().size();
            }
        } else {
            int curr = nextIndex;
            while(nextIndex == curr){
                nextIndex = (int) (Math.random() * (playlistToPlay.getTracks().size()));
            }
        }
        currentTrack = playlistToPlay.getTracks().get(nextIndex);
        track.set(currentTrack);
        audioPlayer = minim.loadMP3File(currentTrack.getMp3FilePath());
        time.setValue(0);
        if(playing){
            play();
        }
    }

    public synchronized void skipBack() {
        int nextIndex;
        if (!shuffled) {
            nextIndex = playlistToPlay.getTracks().indexOf(currentTrack) - 1;
            if (nextIndex < 0) {
                nextIndex = nextIndex + playlistToPlay.getTracks().size();
            }
        } else {
            nextIndex = (int) (Math.random() * (playlistToPlay.getTracks().size()));
        }
        currentTrack = playlistToPlay.getTracks().get(nextIndex);
        track.set(currentTrack);
        audioPlayer = minim.loadMP3File(currentTrack.getMp3FilePath());
        time.setValue(0);
        if(playing){
            play();
        }
    }

    public void volume(double value) {
        audioPlayer.setGain(0 + (-60) * (1 - (float)(value) / 100));
    }

    public void setPlaylist(Playlist actPlaylist) {
        if(actPlaylist != null) {
            playlistToPlay = actPlaylist;
            currentTrack = playlistToPlay.getTracks().getFirst();
            track.set(currentTrack);
        }
        else{
            System.err.println();
        }
    }

    public void shuffle(boolean on) {
        shuffled = on;
        System.out.println("Shuffle " + (shuffled ? "On" : "Off"));
    }

    public void repeat(boolean on) {
        repeated = on;
        System.out.println("Repeat " + (repeated ? "On" : "Off"));
    }

    public void showInfo() {
        Mp3File mp3file;
        try {
            mp3file = new Mp3File(currentTrack.getMp3FilePath());
            ID3v1 idTag;
            if(mp3file.hasId3v1Tag()){
                idTag = mp3file.getId3v1Tag();
            }
            else{
                idTag = mp3file.getId3v2Tag();
            }
            System.out.println("Track: " + idTag.getTrack());
            System.out.println("Artist: " + idTag.getArtist());
            System.out.println("Title: " + idTag.getTitle());
            System.out.println("Album: " + idTag.getAlbum());
            System.out.println("Year: " + idTag.getYear());
            System.out.println("Genre: " + idTag.getGenre() + " (" +
                    idTag.getGenreDescription() + ")");
            System.out.println("Comment: " + idTag.getComment());
            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Track getCurrentTrack() {
        return currentTrack;
    }
    public void setCurrentTrack(Track currentTrack){
        this.currentTrack = currentTrack;
        audioPlayer = minim.loadMP3File(currentTrack.getMp3FilePath());
    }

    public Playlist getPlaylist(){
        return playlistToPlay;
    }
}
