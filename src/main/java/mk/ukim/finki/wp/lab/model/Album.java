package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs
    private Long id;

    @Column(nullable = false) // Ensures `name` is not null
    private String name;

    @Column(nullable = false) // Ensures `genre` is not null
    private String genre;

    @Column(nullable = false) // Ensures `releaseYear` is not null
    private String releaseYear;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    // `mappedBy` indicates that the `album` field in `Song` owns the relationship
    private List<Song> songs;

    public Album() {
        // Default constructor required by JPA
    }

    public Album(String name, String genre, String releaseYear) {
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
}
