package com.gelo.controller.filters;

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

public class LanguageFilterTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest httpServletRequest;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletResponse httpServletResponse;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private FilterChain filterChain;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testNoLanguage() throws IOException, ServletException {
        LanguageFilter languageFilter = new LanguageFilter();

        HttpSession httpSession = Mockito.mock(HttpSession.class);

        Mockito.when(httpSession.getAttribute("language")).thenReturn(null);

        Mockito.when(httpServletRequest.getSession(true)).thenReturn(httpSession);

        languageFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpSession).setAttribute("language", "uk");

    }
}
