package pl.pjwstk.jaz.allezon.login;

import pl.pjwstk.jaz.allezon.CurrentSession;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("*")
public class LoginFilter extends HttpFilter {
    @Inject
    CurrentSession currentSession;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String currentPath = req.getContextPath() + req.getServletPath();

        //https://stackoverflow.com/questions/44702494/servlet-filter-prevents-css-from-working
        boolean isCSS = req.getRequestURI().contains(".css");
        boolean isImage = req.getRequestURI().contains(".png");

        if (userIsLogged() || currentPath.equals("/app/register.xhtml") || currentPath.equals("/app/login.xhtml")  || isCSS || isImage) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }

    private boolean userIsLogged() {
        return currentSession.isLogged();
    }
}
