package uz.greenwhite.lib.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public abstract class HttpTextRequest implements HttpRequest {

    public final String codePage;

    public HttpTextRequest(String codePage) {
        this.codePage = codePage;
    }

    public HttpTextRequest() {
        this(HttpUtil.CODE_PAGE);
    }


    @Override
    public final void send(OutputStream os) throws Exception {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os, codePage)));
        try {
            send(writer);
        } finally {
            writer.flush();
        }
    }

    public abstract void send(PrintWriter writer) throws Exception;

    @Override
    public final void receive(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, codePage), 8129);
        receive(reader);
    }

    public abstract void receive(BufferedReader reader) throws Exception;
}
