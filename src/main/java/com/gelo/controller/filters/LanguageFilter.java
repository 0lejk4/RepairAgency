package com.gelo.controller.filters;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Creates session if it does not exists and sets default
 * language if there is no such attribute in session
 */
public class LanguageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LanguageFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("LanguageFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        if (session.getAttribute("language") == null) {
            session.setAttribute("language", "uk");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("LanguageFilter destroyed");
    }
}
