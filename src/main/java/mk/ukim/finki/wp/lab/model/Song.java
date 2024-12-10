package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs
    private Long id;

    @Column(nullable = false, unique = true) // Enforces uniqueness for `trackId`
    private String trackId;

    @Column(nullable = false) // Ensures `title` is not null
    private String title;

    @Column(nullable = false) // Ensures `genre` is not null
    private String genre;

    @Column(nullable = false) // Ensures `releaseYear` is not null
    private int releaseYear;

    @ManyToOne // Many songs can belong to one album
    @JoinColumn(name = "album_id") // Creates a foreign key column `album_id` in the Song table
    private Album album;

    @ManyToMany // Many songs can have many performers
    @JoinTable(
            name = "song_performer", // Name of the join table
            joinColumns = @JoinColumn(name = "song_id"), // Foreign key to the Song table
            inverseJoinColumns = @JoinColumn(name = "artist_id") // Foreign key to the Artist table
    )
    private List<Artist> performers;

    public Song() {
        // Default constructor required by JPA
    }

    public Song(String trackId, String title, String genre, int releaseYear, Album album, List<Artist> performers) {
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.album = album;
        this.performers = performers;
    }
}
