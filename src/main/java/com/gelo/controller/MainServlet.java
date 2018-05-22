package com.gelo.controller;

import com.gelo.controller.router.AuthorizedRouter;
import com.gelo.controller.router.Router;
import com.gelo.util.Transport;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The only wev servlet in application it uses router
 * to handle request`s in service method and uses router`s
 * return to choose how to call next action.
 */
@WebServlet(name = "Main", urlPatterns = {"/app/*"})
public class MainServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(MainServlet.class);

    private Router router;

    /**
     * Delegates router to pass processing to handlers.
     * Then uses returned Transport from it and decides what to do next.
     *
     * @param req  servlet request
     * @param resp servlet response
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Transport transport = router.handle(req, resp);
        switch (transport.getType()) {
            case REDIRECT:
                resp.sendRedirect(transport.getUrl());
                break;
            case ABSOLUTE_FORWARD:
                getServletContext().getRequestDispatcher(transport.getUrl()).forward(req, resp);
                break;
            case RELATIVE_FORWARD:
                req.getRequestDispatcher(transport.getUrl()).forward(req, resp);
                break;
        }
    }

    /**
     * On init gets router instance from servlet context
     */
    @Override
    public void init() {
        logger.info("Main controller started");
        router = (AuthorizedRouter) getServletContext().getAttribute("router");
    }
}
