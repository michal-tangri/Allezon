package pl.pjwstk.jaz.allezon.webapp.servlets;

import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "ImageServlet", urlPatterns = "/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String img = req.getParameter("img");

        File image = new File(AllezonUtils.getPhotosDirectoryPath(), img);

        String contentType = getServletContext().getMimeType(image.getName());

        resp.reset();
        resp.setContentType(contentType);
        resp.setHeader("Content-Length", String.valueOf(image.length()));
        Files.copy(image.toPath(), resp.getOutputStream());
    }

}