/*
package mk.ukim.finki.wp.lab.web.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "ArtistServlet", urlPatterns = "/artist")
public class ArtistServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistServlet(SpringTemplateEngine springTemplateEngine, ArtistService artistService, SongService songService) {
        this.springTemplateEngine = springTemplateEngine;
        this.artistService = artistService;
        this.songService = songService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String selectedTrackId = (String) req.getSession().getAttribute("selectedTrackId");
        String errorMessage = (String) req.getSession().getAttribute("error");

        // Clear the error message from the session after retrieving it
        req.getSession().removeAttribute("error");

        WebContext context = new WebContext(JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp));
        context.setVariable("artists", artistService.listArtists());
        context.setVariable("selectedTrackId", selectedTrackId);
        context.setVariable("error", errorMessage); // Pass the error to the template

        springTemplateEngine.process("artistsList.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String selectedArtistId = req.getParameter("artistId");
        String selectedTrackId = req.getParameter("trackId");

        if (selectedArtistId == null || selectedTrackId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid artist or song selection.");
            return;
        }

        Artist selectedArtist = artistService.findById(Long.valueOf(selectedArtistId));
        Song selectedSong = songService.findByTrackId(selectedTrackId);

        if (selectedArtist == null || selectedSong == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Artist or song not found.");
            return;
        }

        // Check if the artist is already a performer for the song
        if (selectedSong.getPerformers().stream().anyMatch(artist -> artist.getId().equals(selectedArtist.getId()))) {
            // Redirect back to the artist selection page with an error message
            req.getSession().setAttribute("error", "Artist already added to this song.");
            resp.sendRedirect("/artist?trackId=" + selectedTrackId);
            return;
        }

        // Add the artist to the song
        songService.addArtistToSong(selectedArtist, selectedSong);
        resp.sendRedirect("/songDetails?trackId=" + selectedTrackId);
    }

}
*/