/*
package mk.ukim.finki.wp.lab.web.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "SongDetailsServlet", urlPatterns = "/songDetails")
public class SongDetailsServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final SongService songService;

    public SongDetailsServlet(SpringTemplateEngine springTemplateEngine, SongService songService) {
        this.springTemplateEngine = springTemplateEngine;
        this.songService = songService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        String trackId = req.getParameter("trackId");

        if (trackId == null || trackId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Track ID is required.");
            return;
        }

        Song song = songService.findByTrackId(trackId);

        if (song == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Song not found.");
            return;
        }

        WebContext context = new WebContext(webExchange);
        context.setVariable("song", song);
        springTemplateEngine.process("songDetails.html", context, resp.getWriter());
    }
}
*/
