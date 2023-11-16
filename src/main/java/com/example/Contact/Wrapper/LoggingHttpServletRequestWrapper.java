package com.example.Contact.Wrapper;

import com.example.Contact.Constants.Constants;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.log4j.Log4j2;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Log4j2
public class LoggingHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream requestCapture = new ByteArrayOutputStream();
    private final ServletInputStreamWrapper inputStreamWrapper;

    public LoggingHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            InputStream inputStream = request.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                requestCapture.write(buffer, 0, bytesRead);
            }
            inputStreamWrapper = new ServletInputStreamWrapper(new ByteArrayInputStream(requestCapture.toByteArray()));
        } catch (IOException e) {
            log.error(Constants.Wrapper_Error, e);
            throw new RuntimeException(Constants.Wrapper_Error, e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(inputStreamWrapper));
    }

    public String getCapturedRequestBody() {
        return requestCapture.toString(StandardCharsets.UTF_8);
    }

    private static class ServletInputStreamWrapper extends ServletInputStream {
        private final InputStream delegate;

        ServletInputStreamWrapper(InputStream delegate) {
            this.delegate = delegate;
        }

        @Override
        public int read() throws IOException {
            return delegate.read();
        }

        @Override
        public int available() throws IOException {
            return delegate.available();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // Not implemented for simplicity
        }
    }
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> headersInfo = new HashMap<>();
        Collections.list(request.getHeaderNames())
                .forEach(headerName -> headersInfo.put(headerName, request.getHeader(headerName)));
        return headersInfo;
    }
    public static String formatHeaders(Map<String, String> headers) {
        StringBuilder formattedHeaders = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            formattedHeaders.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return formattedHeaders.toString();
    }
}
