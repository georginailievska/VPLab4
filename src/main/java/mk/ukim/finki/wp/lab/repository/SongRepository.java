package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class SongRepository {
    private List<Song> songs = new ArrayList<>(Arrays.asList(
            new Song("1", "Sweet Child O' Mine", "Rock", 1987, new Album(1L, "Appetite for Destruction", "Rock", "1987"), new ArrayList<>()),
            new Song("2", "Livin' on a Prayer", "Rock", 1986, new Album(2L, "Slippery When Wet", "Rock", "1986"), new ArrayList<>()),
            new Song("3", "Space Oddity", "Rock", 1969, new Album(3L, "Heroes", "Rock", "1977"), new ArrayList<>()),
            new Song("4", "Bohemian Rhapsody", "Rock", 1975, new Album(4L, "A Night at the Opera", "Rock", "1975"), new ArrayList<>()),
            new Song("5", "Hound Dog", "Rock", 1956, new Album(5L, "Elvis Presley", "Rock", "1956"), new ArrayList<>())
    ));

    public List<Song> findAll() {
        return songs;
    }

    public Optional<Song> findByTrackId(String trackId) {
        return songs.stream().filter(s -> s.getTrackId().equals(trackId)).findFirst();
    }

    public void addArtistToSong(Artist artist, Song song) {
        song.getPerformers().add(artist);
    }

    public Optional<Song> findById(Long id) {
        return songs.stream().filter(song -> song.getId().equals(id)).findFirst();
    }

    public void save(Song song) {
        songs.removeIf(s -> s.getId().equals(song.getId())); // Update if exists
        songs.add(song);
    }

    public void deleteById(Long id) {
        songs.removeIf(song -> song.getId().equals(id));
    }
}
