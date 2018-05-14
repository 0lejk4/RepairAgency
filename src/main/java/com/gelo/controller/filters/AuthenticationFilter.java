package com.gelo.controller.filters;

import com.gelo.factory.ServiceFactory;
import com.gelo.model.domain.User;
import com.gelo.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Filter that checks if session exists and if user attribute is present in it.
 * If it is not true filter checks if it is not one of the page that are free to visit
 * without Authentication
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("Authentication Filter Initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        HttpSession session = req.getSession(false);

        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (session != null && user != null) {
            UserService userService = ServiceFactory.getUserServiceInstance();
            session.setAttribute("user", userService.findByEmail(user.getEmail()));
            chain.doFilter(request, response);
        } else if (uri.endsWith("/app/login")
                || uri.endsWith("/app/register")
                || uri.endsWith("/app/language")
                || uri.endsWith("/app/home")) {
            chain.doFilter(request, response);
        } else {
            logger.error("Unauthorized access request for resource::" + uri);
            res.sendRedirect("/login.jsp");
        }


    }

}
