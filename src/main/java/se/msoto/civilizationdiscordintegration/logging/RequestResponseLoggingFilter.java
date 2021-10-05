package se.msoto.civilizationdiscordintegration.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        CachedBodyHttpServletRequestWrapper requestWrapper = new CachedBodyHttpServletRequestWrapper(httpServletRequest);

        String requestBody = new String(requestWrapper.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        logRequest(requestWrapper, requestBody);
        LOGGER.info("Path: {}, Request: {}", requestWrapper.getRequestURI(), requestBody);

        filterChain.doFilter(requestWrapper, httpServletResponse);

    }

    private void logRequest(HttpServletRequest httpServletRequest, String requestBody) {

        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames()).stream()
                .collect(Collectors.toMap(headerName -> headerName, httpServletRequest::getHeader));

        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> {
            sb.append(key);
            sb.append(": ");
            sb.append(headers.get(key));
            sb.append(", ");
        });

        LOGGER.info("Path: {}, Content-Type: {}, Headers: {} Request: {}",
                httpServletRequest.getRequestURI(),
                httpServletRequest.getContentType(),
                sb,
                requestBody);
    }
}
