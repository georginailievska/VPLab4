/*
package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "SongListServlet", urlPatterns = "/listSongs")
public class SongListServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final SongService songService;

    public SongListServlet(SpringTemplateEngine springTemplateEngine, SongService songService) {
        this.springTemplateEngine = springTemplateEngine;
        this.songService = songService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        context.setVariable("songs", songService.listSongs());
        springTemplateEngine.process("listSongs.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String selectedTrackId = req.getParameter("trackId");
        if (selectedTrackId == null || selectedTrackId.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "You must select a song.");
            return;
        }
        req.getSession().setAttribute("selectedTrackId", selectedTrackId);
        System.out.println("Selected Track ID: " + selectedTrackId); // Debug log
        resp.sendRedirect("/artist");
    }

}
*/
