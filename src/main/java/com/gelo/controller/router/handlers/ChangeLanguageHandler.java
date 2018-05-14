package com.gelo.controller.router.handlers;

import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Gets language from request and sets it to the session.
 */
public class ChangeLanguageHandler implements Handler {

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession(false).setAttribute("language", request.getParameter("language"));
        String path = (String) request.getAttribute("referer");
        if (Paths.LOGIN_PAGE.equals(path) || Paths.LOGIN_HANDLER.equals(path)) {
            return Transport.redirect(Paths.LOGIN_PAGE);
        }
        if (Paths.REGISTER_PAGE.equals(path) || Paths.REGISTER_HANDLER.equals(path)) {
            return Transport.redirect(Paths.REGISTER_PAGE);
        }
        return Transport.redirect(Paths.HOME_PAGE);
    }
}
