package com.gelo.controller.filters;

import com.gelo.model.domain.User;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilterTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest httpServletRequest;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletResponse httpServletResponse;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private FilterChain filterChain;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void no_session_but_secured() throws IOException, ServletException {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/app/kek");

        Mockito.when(httpServletRequest.getSession(false)).thenReturn(null);
        authenticationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletResponse).sendRedirect("/login.jsp");

    }


    @Test
    public void no_session_not_secured() throws IOException, ServletException {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();

        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/app/login");

        Mockito.when(httpServletRequest.getSession(false)).thenReturn(null);

        authenticationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(httpServletRequest, httpServletResponse);

    }

    @Test
    public void session_and_user_present() throws IOException, ServletException {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();

        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(session.getAttribute("user")).thenReturn(new User.UserBuilder().build());
        Mockito.when(httpServletRequest.getRequestURI()).thenReturn("/app/secured");

        Mockito.when(httpServletRequest.getSession(false)).thenReturn(session);

        authenticationFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(httpServletRequest, httpServletResponse);

    }

}
