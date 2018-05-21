package com.gelo.controller.router;


import com.gelo.util.Transport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface of class that should be used in MainServlet
 * to choose what handler to use and how everything should happen.
 */
public interface Router {
    Transport handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
