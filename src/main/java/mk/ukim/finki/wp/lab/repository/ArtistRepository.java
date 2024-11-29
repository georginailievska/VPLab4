package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ArtistRepository {
    private List<Artist> artists = Arrays.asList(
            new Artist(1L, "Axl", "Rose", "Lead singer of Guns N' Roses"),
            new Artist(2L, "Jon", "Bon Jovi", "Lead singer of Bon Jovi"),
            new Artist(3L, "David", "Bowie", "Legendary singer and actor"),
            new Artist(4L, "Freddie", "Mercury", "Lead singer of Queen"),
            new Artist(5L, "Elvis", "Presley", "King of Rock n Roll")
    );

    public List<Artist> findAll() {
        return artists;
    }

    public Optional<Artist> findById(Long id) {
        return artists.stream().filter(a -> a.getId().equals(id)).findFirst();
    }
}
