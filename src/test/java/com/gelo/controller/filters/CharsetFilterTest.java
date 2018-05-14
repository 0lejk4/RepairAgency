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
import java.io.IOException;

public class CharsetFilterTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest httpServletRequest;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletResponse httpServletResponse;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private FilterChain filterChain;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void encodingSet() throws IOException, ServletException {
        CharsetFilter charsetFilter = new CharsetFilter();
        Mockito.when(httpServletRequest.getCharacterEncoding()).thenReturn(null);

        charsetFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest).setCharacterEncoding("UTF-8");

        Mockito.verify(httpServletResponse).setCharacterEncoding("UTF-8");

    }

    @Test
    public void encodingNotSet() throws IOException, ServletException {
        CharsetFilter charsetFilter = new CharsetFilter();
        Mockito.when(httpServletRequest.getCharacterEncoding()).thenReturn("UTF-8");

        charsetFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        Mockito.verify(httpServletRequest, Mockito.never()).setCharacterEncoding("UTF-8");

        Mockito.verify(httpServletResponse, Mockito.never()).setCharacterEncoding("UTF-8");

    }
}
