package se.msoto.civilizationdiscordintegration.logging;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CachedBodyHttpServletResponseWrapper extends ContentCachingResponseWrapper {

    public CachedBodyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void copyBodyToResponse() {
        try {
            super.copyBodyToResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
