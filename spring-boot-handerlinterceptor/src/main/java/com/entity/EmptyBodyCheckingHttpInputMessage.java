package com.entity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/12/21
 */
public class EmptyBodyCheckingHttpInputMessage implements HttpInputMessage {
    private final HttpHeaders headers;

    @Nullable
    private final InputStream body;

    public EmptyBodyCheckingHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
        this.headers = inputMessage.getHeaders();
        InputStream inputStream = inputMessage.getBody();
        if (inputStream.markSupported()) {
            inputStream.mark(1);
            this.body = (inputStream.read() != -1 ? inputStream : null);
            inputStream.reset();
        } else {
            PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
            int b = pushbackInputStream.read();
            if (b == -1) {
                this.body = null;
            }
            else {
                this.body = pushbackInputStream;
                pushbackInputStream.unread(b);
            }
        }
    }
    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public InputStream getBody() {
        return (this.body != null ? this.body : StreamUtils.emptyInput());
    }

    public boolean hasBody() {
        return (this.body != null);
    }
}
