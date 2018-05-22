package com.gelo.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;


/**
 * Checks if encoding is set for both request and response and if it is not set it does so
 */
public class CharsetFilter implements Filter {
    private static Logger logger = Logger.getLogger(CharsetFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Charset Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.debug("Charset Filter destroyed");
    }
}
