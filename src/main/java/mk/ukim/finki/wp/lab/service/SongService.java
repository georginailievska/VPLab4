package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;

public interface SongService {
    List<Song> listSongs();
    Song findByTrackId(String trackId);
    Song findById(Long id);
    void saveSong(Song song);
    void deleteSong(Long id);
    List<Song> findSongsByAlbumId(Long albumId);
}

