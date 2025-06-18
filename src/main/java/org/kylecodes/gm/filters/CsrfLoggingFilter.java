package org.kylecodes.gm.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class CsrfLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String csrfHeader = request.getHeader("X-XSRF-TOKEN");
        Cookie[] cookies = request.getCookies();
        String csrfCookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("XSRF-TOKEN"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("not found");

        System.out.println("📦 XSRF-TOKEN header: " + csrfHeader);
        System.out.println("🍪 XSRF-TOKEN cookie: " + csrfCookie);

        filterChain.doFilter(request, response);
    }
}
