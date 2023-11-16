package com.example.Contact.Filter;

import com.example.Contact.Config.UserInfoUserDetailsService;
import com.example.Contact.Constants.Constants;
import com.example.Contact.Service.JwtService;
import com.example.Contact.Wrapper.CharResponseWrapper;
import com.example.Contact.Wrapper.LoggingHttpServletRequestWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserInfoUserDetailsService userDetailsService;
    public static String userNameMatches;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                userNameMatches = username;
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            LoggingHttpServletRequestWrapper requestWrapper = new LoggingHttpServletRequestWrapper(request);

            // Log the incoming request URL, headers, and body
            String requestBody = requestWrapper.getCapturedRequestBody();

            // Continue the filter chain
            CharResponseWrapper responseWrapper = new CharResponseWrapper(response);

            // Continue the filter chain with the response wrapper
            filterChain.doFilter(requestWrapper, responseWrapper);

            log.info("Request URL: {} {}\nHeaders: {}\nBody: {}", request.getMethod(), request.getRequestURI(), LoggingHttpServletRequestWrapper.formatHeaders(LoggingHttpServletRequestWrapper.getHeadersInfo(request)), requestBody);

            // Log the outgoing response along with the response body
            String responseBody = responseWrapper.getCapturedResponse();

            log.info("Response body: {}\n", responseBody);
            response.getWriter().write(responseBody);

        }catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            JSONObject jwtError = new JSONObject();
            jwtError.put(Constants.JWT_Expired,Constants.Log_Out_In);
            response.getWriter().write(jwtError.toString());
        }
    }
}

