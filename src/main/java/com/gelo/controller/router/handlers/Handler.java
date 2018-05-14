package com.gelo.controller.router.handlers;

import com.gelo.util.Transport;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface that should be implemented in order to handle servlet responses
 */
public interface Handler {
    /**
     * Method that does some processing and tells MainServlet where and how it should continue processing
     * @param request request that should be handled
     * @param response response that should be handled
     * @return return wrapper class for information about transporting
     */
    Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
