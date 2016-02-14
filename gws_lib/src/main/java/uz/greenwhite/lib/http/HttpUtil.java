package uz.greenwhite.lib.http;

import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import uz.greenwhite.lib.GWSLIB;
import uz.greenwhite.lib.Setter;
import uz.greenwhite.lib.error.AppError;
import uz.greenwhite.lib.error.HttpError;
import uz.greenwhite.lib.error.UserError;
import uz.greenwhite.lib.mold.MoldActivity;

public final class HttpUtil {

    public static final String CODE_PAGE = "UTF8";

    public static String readToEnd(BufferedReader reader) {
        try {
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String readToEnd(InputStream inputStream, String codePage) {
        try {
            return readToEnd(new BufferedReader(new InputStreamReader(inputStream, codePage), 4096));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readToEnd(InputStream inputStream) {
        return readToEnd(inputStream, CODE_PAGE);
    }

    public static String getMovePermanent(String url) throws Exception {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        InputStream inputStream = null;
        try {
            conn.setInstanceFollowRedirects(false);
            conn.setReadTimeout(10000);
            inputStream = conn.getInputStream();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ||
                    conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP ||
                    conn.getResponseCode() == HttpURLConnection.HTTP_SEE_OTHER) {
                return conn.getHeaderField("Location");
            } else {
                return null;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            conn.disconnect();
        }
    }

    public static String get(String url) throws Exception {
        return get(url, CODE_PAGE);
    }

    public static String get(String url, String codePage) throws Exception {
        return load(url, null, codePage);
    }

    public static String post(String url, String body) throws Exception {
        return post(url, body, CODE_PAGE);
    }

    public static String post(String url, String body, String codePage) throws Exception {
        if (body == null) {
            throw AppError.NullPointer();
        }
        return load(url, body, codePage);
    }

    private static String load(String url, final String body, String codePage) throws Exception {
        final Setter<String> result = new Setter<String>();
        HttpRequest req = new HttpTextRequest(codePage) {
            @Override
            public void send(PrintWriter writer) throws Exception {
                if (body != null) {
                    writer.print(body);
                }
            }

            @Override
            public void receive(BufferedReader reader) throws Exception {
                result.value = readToEnd(reader);
            }
        };
        load(url, req, body != null);
        return result.value;
    }

    public static void get(String url, HttpRequest request) throws Exception {
        load(url, request, false);
    }

    public static void post(String url, HttpRequest request) throws Exception {
        load(url, request, true);
    }

    private static void load(String url, HttpRequest request, boolean isPost) throws Exception {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        try {
            if (isPost) {
                if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                    conn.setRequestProperty("Connection", "close");
                }
                conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                conn.setDoOutput(true);
                conn.setChunkedStreamingMode(0);

                send(conn, request);
            }

            receive(conn, request);
        } catch (ConnectException ex) {
            if (GWSLIB.DEBUG) {
                Log.e("GWS_LIB", "Http error", ex);
            }
            throw new UserError("Нет подключения к Интернету");
        } catch (IOException ex) {
            String err = readError(conn);
            if (err != null) {
                throw new HttpError(err);
            }
            throw ex;

        } finally {
            conn.disconnect();
        }
    }

    private static void send(HttpURLConnection conn, HttpRequest request) throws Exception {
        OutputStream out = new BufferedOutputStream(conn.getOutputStream());
        try {
            request.send(out);
        } finally {
            out.close();
        }
    }

    private static void receive(HttpURLConnection conn, HttpRequest request) throws Exception {
        InputStream in = new BufferedInputStream(conn.getInputStream());
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                request.receive(in);
            } else {
                throw new HttpError("resp.StatusCode=" + String.valueOf(conn.getResponseCode()));
            }
        } finally {
            in.close();
        }
    }

    private static String readError(HttpURLConnection conn) {
        try {
            InputStream in = new BufferedInputStream(conn.getErrorStream());
            try {
                if (in != null) {
                    return readToEnd(in);
                }
                return null;
            } finally {
                in.close();
            }
        } catch (Throwable ex) {
            GWSLIB.log(ex);
        }
        return null;
    }
}
