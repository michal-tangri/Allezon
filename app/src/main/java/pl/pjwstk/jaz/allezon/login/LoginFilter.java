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

        if (!userIsLogged())
            //TODO what if I change port/app name?
            res.sendRedirect("/app/login.xhtml");
        else
            chain.doFilter(req, res);

    }

    private boolean userIsLogged() {
        return currentSession.isLogged();
    }
}
