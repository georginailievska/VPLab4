package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlbumRepository {
    private final List<Album> albums = new ArrayList<>();

    public AlbumRepository() {
        albums.add(new Album(1L, "Appetite for Destruction", "Rock", "1987"));
        albums.add(new Album(2L, "Slippery When Wet", "Rock", "1986"));
        albums.add(new Album(3L, "Heroes", "Rock", "1977"));
        albums.add(new Album(4L, "A Night at the Opera", "Rock", "1975"));
        albums.add(new Album(5L, "Elvis Presley", "Rock", "1956"));
    }

    public List<Album> findAll() {
        return albums;
    }
}