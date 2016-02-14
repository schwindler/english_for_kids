package uz.greenwhite.lib.http;

import java.io.InputStream;
import java.io.OutputStream;

public interface HttpRequest {

    void send(OutputStream os) throws Exception;

    void receive(InputStream is) throws Exception;
}
