package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import mk.ukim.finki.wp.lab.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final AlbumService albumService;

    public SongController(SongService songService, AlbumService albumService) {
        this.songService = songService;
        this.albumService = albumService;
    }

    @GetMapping
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("songs", songService.listSongs());
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
        try {
            Song song = songService.findById(id);
            model.addAttribute("song", song);
            model.addAttribute("albums", albumService.findAll());
            return "add-song";
        } catch (IllegalArgumentException e) {
            return "redirect:/songs?error=Song not found";
        }
    }

    @PostMapping
    public String saveSong(@RequestParam(required = false) Long id, // Optional ID for update
                           @RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam Long albumId,
                           Model model) {
        Album album = albumService.findAll().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        // Check for duplicate trackId when creating a new song
        if (songService.findByTrackId(trackId) != null) {
            model.addAttribute("error", "A song with the same track ID already exists.");
            model.addAttribute("albums", albumService.findAll());
            if (id != null) {
                // Redirect to the edit form with the same ID
                model.addAttribute("song", songService.findById(id));
            }
            return "add-song"; // Redirect to the add form
        }

        if (id != null) {
            // Update existing song
            Song existingSong = songService.findById(id);
            if (existingSong == null) {
                return "redirect:/songs?error=Song not found.";
            }

            // Update fields
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
    public String selectSong(@RequestParam String trackId) {
        if (trackId == null || trackId.isEmpty()) {
            return "redirect:/songs?error=You must select a song.";
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
}
