package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import mk.ukim.finki.wp.lab.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;
    private String selectedAlbumId = null;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String albumId,
                               @RequestParam(required = false) String error,
                               Model model) {
        List<Song> songs;

        if (albumId == null && selectedAlbumId != null) {
            albumId = selectedAlbumId;
        } else {
            selectedAlbumId = albumId;
        }

        if (albumId == null || albumId.equals("null") || albumId.isEmpty()) {
            songs = songService.listSongs();
        } else {
            songs = songService.findSongsByAlbumId(Long.valueOf(albumId));
        }

        model.addAttribute("songs", songs);
        model.addAttribute("albums", albumService.findAll());
        model.addAttribute("selectedAlbumId", selectedAlbumId);
        model.addAttribute("error", error);
        return "listSongs";
    }

    @GetMapping("/add-form")
    public String getAddSongPage(Model model) {
        model.addAttribute("albums", albumService.findAll());
        return "add-song";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditSongForm(@PathVariable Long id, Model model) {
        Song song = songService.findById(id);
        if (song == null) {
            return "redirect:/songs?error=Song not found.";
        }
        model.addAttribute("song", song);
        model.addAttribute("albums", albumService.findAll());
        return "add-song";
    }

    @PostMapping
    public String saveSong(@RequestParam(required = false) Long id,
                           @RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam(required = false) Long albumId, // Album ID is optional
                           Model model) {
        // Check if albumId is null
        if (albumId == null) {
            model.addAttribute("error", "You must select an album.");
            model.addAttribute("albums", albumService.findAll());
            if (id != null) {
                // Populate the song details in case of update
                model.addAttribute("song", songService.findById(id));
            }
            return "add-song";
        }

        Album album = albumService.findAll().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        // Check for duplicate trackId when creating a new song or updating
        if (songService.findByTrackId(trackId) != null && (id == null || !songService.findByTrackId(trackId).getId().equals(id))) {
            model.addAttribute("error", "A song with the same track ID already exists.");
            model.addAttribute("albums", albumService.findAll());
            if (id != null) {
                model.addAttribute("song", songService.findById(id));
            }
            return "add-song";
        }

        if (id != null) {
            // Update existing song
            Song existingSong = songService.findById(id);
            if (existingSong == null) {
                return "redirect:/songs?error=Song not found.";
            }
            existingSong.setTitle(title);
            existingSong.setTrackId(trackId);
            existingSong.setGenre(genre);
            existingSong.setReleaseYear(releaseYear);
            existingSong.setAlbum(album);
            songService.saveSong(existingSong);
        } else {
            // Create new song
            Song newSong = new Song(trackId, title, genre, releaseYear, album, new ArrayList<>());
            songService.saveSong(newSong);
        }

        return "redirect:/songs";
    }

    @GetMapping("/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return "redirect:/songs";
    }

    @PostMapping("/select")
    public String selectSong(@RequestParam(required = false) String trackId) {
        if (trackId == null || trackId.isEmpty()) {
            return "redirect:/songs?error=You must select a song before proceeding.";
        }
        return "redirect:/artist?trackId=" + trackId;
    }

    @GetMapping("/details")
    public String getSongDetails(@RequestParam String trackId, Model model) {
        if (trackId == null || trackId.isEmpty()) {
            return "redirect:/songs?error=Track ID is required.";
        }

        Song song = songService.findByTrackId(trackId);
        if (song == null) {
            return "redirect:/songs?error=Song not found.";
        }

        model.addAttribute("song", song);
        return "songDetails";
    }

    @GetMapping("/album/{albumId}")
    public String getSongsByAlbum(@PathVariable Long albumId, Model model) {
        List<Song> songs = songService.findSongsByAlbumId(albumId);
        model.addAttribute("songs", songs);
        model.addAttribute("albumId", albumId);
        return "listSongs"; // Ensure your listSongs.html supports displaying songs filtered by album
    }



}

