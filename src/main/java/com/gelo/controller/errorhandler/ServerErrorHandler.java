package com.gelo.controller.errorhandler;

import com.gelo.util.constants.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handler for server errors, it should usually not appear when everything
 * is programed well but when developing it should happen a lot.
 */
@WebServlet("/ServerErrorHandler")
public class ServerErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    /**
     * Simply shows exception occurred on 500.jsp page
     */
    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {

        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }

        request.setAttribute("error", String.format("Servlet %s has thrown an exception %s : %s",
                servletName,
                throwable.getClass().getName(),
                throwable.getMessage()));

        request.setAttribute("statusCode", statusCode);
        request.getRequestDispatcher(Paths.SERVER_ERROR)
                .forward(request, response);
    }
}
