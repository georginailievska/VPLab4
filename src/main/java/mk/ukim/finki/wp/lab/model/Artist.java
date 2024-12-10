package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity // Marks this class as a JPA entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates unique IDs
    private Long id;

    @Column(nullable = false) // Ensures `firstName` is not null
    private String firstName;

    @Column(nullable = false) // Ensures `lastName` is not null
    private String lastName;

    @Column(length = 1000) // Allows a longer bio field
    private String bio;

    @ManyToMany(mappedBy = "performers") // Maps the many-to-many relationship with `Song`
    private List<Song> songs; // Artists can perform in multiple songs

    public Artist() {
        // Default constructor required by JPA
    }

    public Artist(String firstName, String lastName, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
}
