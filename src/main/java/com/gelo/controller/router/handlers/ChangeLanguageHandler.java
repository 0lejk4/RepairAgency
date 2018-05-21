package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Gets language from request and sets it to the session.
 */
@PostMapping
public class ChangeLanguageHandler implements Handler {

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession(false).setAttribute("language", request.getParameter("language"));
        String path = request.getHeader("Referer");
        if (path.endsWith(Paths.LOGIN_PAGE) || path.endsWith(Paths.LOGIN_HANDLER)) {
            return Transport.redirect(Paths.LOGIN_PAGE);
        }
        if (path.endsWith(Paths.REGISTER_PAGE) || path.endsWith(Paths.REGISTER_HANDLER)) {
            return Transport.redirect(Paths.REGISTER_PAGE);
        }
         return Transport.redirect(Paths.HOME_PAGE);
    }
}
