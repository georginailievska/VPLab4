package mk.ukim.finki.wp.lab.model;

import java.util.List;
import lombok.Data;

@Data
public class Song {
    private Long id;
    private String trackId;
    private String title;
    private String genre;
    private int releaseYear;
    private Album album; // New reference
    private List<Artist> performers;

    private static long idCounter = 1;

    public Song(String trackId, String title, String genre, int releaseYear, Album album, List<Artist> performers) {
        this.id = idCounter++;
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.album = album;
        this.performers = performers;
    }
}
