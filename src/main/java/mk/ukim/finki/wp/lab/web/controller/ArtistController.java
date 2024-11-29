package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping
    public String getArtistPage(@RequestParam String trackId,
                                @RequestParam(required = false) String error,
                                Model model) {
        Song selectedSong = songService.findByTrackId(trackId);

        if (selectedSong == null) {
            return "redirect:/songs?error=Song not found";
        }

        model.addAttribute("artists", artistService.listArtists());
        model.addAttribute("selectedTrackId", trackId);
        model.addAttribute("error", error);

        return "artistsList";
    }

    @PostMapping
    public String addArtistToSong(@RequestParam String artistId, @RequestParam String trackId, Model model) {
        if (artistId == null || trackId == null) {
            return "redirect:/artist?trackId=" + trackId + "&error=Invalid artist or song selection.";
        }

        Artist artist = artistService.findById(Long.valueOf(artistId));
        Song song = songService.findByTrackId(trackId);

        if (artist == null || song == null) {
            return "redirect:/artist?trackId=" + trackId + "&error=Artist or song not found.";
        }

        if (song.getPerformers().stream().anyMatch(a -> a.getId().equals(artist.getId()))) {
            return "redirect:/artist?trackId=" + trackId + "&error=Artist already added.";
        }

        songService.addArtistToSong(artist, song);
        return "redirect:/songs/details?trackId=" + trackId;
    }
}
