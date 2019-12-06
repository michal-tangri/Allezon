package pl.pjwstk.jaz.allezon.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
}
