package Bussiness;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {


    private final List<Playlist> playlists;

    public PlaylistManager() {
        playlists = new ArrayList<>();
        playlists.add(new Playlist("Playlist von Schule", "src/playlist1.m3u"));
        playlists.add(new Playlist("Eigene Playlist", "src/playlist2.m3u"));
    }

    public Playlist getPlaylist(String title) {
        Playlist list = null;
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(title)) {
                list = playlist;
                break;
            }
        }
        if (list == null) {
            System.out.println("Playlist does not exist!!");
        }
        return list;

    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getAlltrack() {
        List<Track> allTracks = new ArrayList<>();
        for (Playlist playlist : playlists) {
            for (Track track : playlist.getTracks()) {
                allTracks.add(track);
            }
        }
        Playlist allTracksPlaylist = new Playlist("All Tracks: ", allTracks);
        return allTracksPlaylist;
    }

    public Playlist getPlaylist(int num){
        Playlist playlist = null;
        return playlists.get(num);
    }

}
