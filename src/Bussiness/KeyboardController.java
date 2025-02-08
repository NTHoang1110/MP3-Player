package Bussiness;

import de.hsrm.mi.prog.util.StaticScanner;

public class KeyboardController {
    private MP3Player mp3;
    private final PlaylistManager playlistCollection;
    private Playlist chosenPlaylist;

    public KeyboardController() {
        playlistCollection = new PlaylistManager();
        chosenPlaylist = playlistCollection.getPlaylist("FirstPlaylist");
    }

    public void chooseAPlaylist(){
        do {
            System.out.println("Which Playlist would you like to play? ");
            String input = StaticScanner.nextString();
            chosenPlaylist = playlistCollection.getPlaylist(input);
        } while(chosenPlaylist == null);
    }

    public final void start() {
        mp3 = new MP3Player(chosenPlaylist);
        String input;
        String commands[];
        do {
            input = StaticScanner.nextString();
            commands = input.split(" ");
            if (input.equals("quit")) {
                System.exit(0);
                break;
            }
            switch (commands[0]) {
                case "play" -> mp3.play();
                case "pause" -> mp3.pause();
                case "volume" -> {
                    float vol = Integer.parseInt(commands[1]);
                    mp3.volume(vol);
                }
                case "show" -> mp3.showInfo();
                case "skip" -> mp3.skip();
                case "skipback" -> mp3.skipBack();
                case "shuffle" -> mp3.shuffle(!mp3.isShuffled());
                case "repeat" -> mp3.repeat(!mp3.isRepeated());
                case "changeToPlaylist" -> {
                    mp3.pause();
                    try{
                        chosenPlaylist = playlistCollection.getPlaylist(commands[1]);
                        mp3 = new MP3Player(chosenPlaylist);
                    }
                    catch(IndexOutOfBoundsException iobe){
                        chooseAPlaylist();
                        mp3 = new MP3Player(chosenPlaylist);
                    }
                }
                default -> {
                    System.err.println("Not sure what you meant there. Try again.");
                }
            }
        } while (true);
    }
}
