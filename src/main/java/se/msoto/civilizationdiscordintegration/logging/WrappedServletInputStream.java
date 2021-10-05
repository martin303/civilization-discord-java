package se.msoto.civilizationdiscordintegration.logging;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WrappedServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream inputStream;

    public WrappedServletInputStream(byte[] bytes) {
        this.inputStream = new ByteArrayInputStream(bytes);
    }


    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() {
        return inputStream.read();
    }
}
