package pl.pjwstk.jaz.allezon.login;

import pl.pjwstk.jaz.allezon.webapp.CurrentSession;

import javax.faces.application.ResourceHandler;
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

        String contextPath = req.getContextPath();
        String currentPath = contextPath + req.getServletPath();

        boolean userAdmin = currentSession.isAdmin();
        boolean userLogged = currentSession.isLogged();
        boolean userOnLoginPage = currentPath.contains("/login.xhtml");
        boolean userOnAdminPage = currentPath.contains("/admin_panel.xhtml");
        boolean userOnWelcomePage = currentPath.contains("/welcome.xhtml");
        boolean userOnRegisterPage = currentPath.contains("/register.xhtml");
        boolean userOnSectionsPage = currentPath.contains("/sections.xhtml");
        boolean userOnCategoriesPage = currentPath.contains("/categories.xhtml");

        boolean isResource = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");

        if(isResource) {
            chain.doFilter(req, res);
        }
        else if(userLogged) {
            if(userOnLoginPage || userOnRegisterPage || userOnWelcomePage) {
                res.sendRedirect(contextPath + "/index.xhtml");
            }
            else if(userOnAdminPage || userOnSectionsPage || userOnCategoriesPage) {
                if(userAdmin)
                    chain.doFilter(req, res);
                else
                    res.sendRedirect(contextPath + "/index.xhtml");
            }
            else
                chain.doFilter(req, res);
        }
        else {
            if(userOnLoginPage || userOnRegisterPage || userOnWelcomePage)
                chain.doFilter(req, res);
            else
                res.sendRedirect(contextPath + "/welcome.xhtml");
        }
    }
}
