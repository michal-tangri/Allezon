package pl.pjwstk.jaz.allezon.webapp;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

//https://stackoverflow.com/questions/16776981/response-object-in-jsf

public class AllezonUtils {

    public static void redirectToPage(String pageURL) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getApplicationContextPath() + pageURL);
        } catch (IOException redirectException) {
            System.out.println("Failed to redirect to " + pageURL);
        }
    }

}
