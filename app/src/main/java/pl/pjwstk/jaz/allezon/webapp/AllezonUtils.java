package pl.pjwstk.jaz.allezon.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

//https://stackoverflow.com/questions/16776981/response-object-in-jsf

@ApplicationScoped
public class AllezonUtils {

    @Inject
    private HttpServletRequest request;

    public boolean linkContains(String URLparameter) {
        return request.getParameter(URLparameter) != null;
    }

    public Long getLongFromLink(String URLparameter) {
        var value = request.getParameter(URLparameter);
        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException err0) {
            return null;
        }
    }

    public void redirectToPage(String pageURL) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getApplicationContextPath() + pageURL);
        } catch (IOException redirectException) {
            System.out.println("Failed to redirect to " + pageURL);
        }
    }

    /*
        Jeżeli nie ładują się zdjęcia po dodaniu
        WebServlet z linkiem do zdjęcia
        doGet
        nagłówki content type

    */
    public String saveImageToFile(Part file, String newFileName) throws IllegalArgumentException {
        String originalFileName = file.getSubmittedFileName();

        if(!originalFileName.endsWith(".png") && !originalFileName.endsWith(".jpg") && !originalFileName.endsWith(".jpeg"))
            throw new IllegalArgumentException();

        String fileName = (newFileName + System.nanoTime() + originalFileName)
                .replaceAll("\\s","");

        try (InputStream input = file.getInputStream()) {
            File newFile = new File("/home/michaltangri/auction_photos", fileName);
            Files.copy(input, newFile.toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
