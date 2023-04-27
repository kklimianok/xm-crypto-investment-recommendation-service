package com.xm.services.investment.crypto.rs.rest.api.filter;

import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for  {@link RateLimitFilter}.
 */
public class RateLimitFilterTest {

    @Test
    public void testShouldFilter() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/some/test/path");
        assertFalse(new RateLimitFilter().shouldNotFilter(request));
    }

    @Test
    public void testShouldNotFilter() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/swagger-ui/path");
        assertTrue(new RateLimitFilter().shouldNotFilter(request));
    }


    @Test
    public void testDoFilterNotLimited() throws Exception {
        RateLimitFilter filter = new RateLimitFilter();
        filter.setLimitTime(1000L);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        doNothing().when(filterChain).doFilter(servletRequest, servletResponse);

        filter.doFilterInternal(servletRequest, servletResponse, filterChain);

        verify(filterChain).doFilter(servletRequest, servletResponse);
        verifyNoInteractions(servletResponse);
    }

    @Test
    public void testDoFilterLimited() throws Exception {
        RateLimitFilter filter = new RateLimitFilter();
        filter.setLimitTime(1000L);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        doNothing().when(filterChain).doFilter(servletRequest, servletResponse);
        doNothing().when(servletResponse).setStatus(429);

        filter.doFilterInternal(servletRequest, servletResponse, filterChain);
        filter.doFilterInternal(servletRequest, servletResponse, filterChain);

        verify(filterChain).doFilter(servletRequest, servletResponse);
        verify(servletResponse).setStatus(429);
    }

    @Test
    public void testDoFilterDisabledLimit() throws Exception {
        RateLimitFilter filter = new RateLimitFilter();
        filter.setLimitTime(0L);
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        when(servletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        doNothing().when(filterChain).doFilter(servletRequest, servletResponse);

        filter.doFilterInternal(servletRequest, servletResponse, filterChain);
        filter.doFilterInternal(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(2)).doFilter(servletRequest, servletResponse);
        verifyNoInteractions(servletResponse);
    }
}