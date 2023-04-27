package com.xm.services.investment.crypto.rs.rest.api.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

/**
 * Requests filter for IP Rate limitations for a specified time.
 *
 * @author Kanstantsin_Klimianok
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Value("${ipRateLimitMillis:0}")
    private Long limitTime;

    private final Map<String, Long> ipDateMap = newHashMap();

    void setLimitTime(Long limitTime) {
        this.limitTime = limitTime;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.contains("swagger");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        long currentTime = currentTimeMillis();
        String ip = request.getRemoteAddr();
        if(ipDateMap.containsKey(ip) && ((ipDateMap.get(ip) + limitTime) > currentTime)) {
            ipDateMap.put(ip, currentTime);
            response.setStatus(TOO_MANY_REQUESTS.value());
        } else {
            ipDateMap.put(ip, currentTime);
            filterChain.doFilter(request, response);
        }
    }
}
