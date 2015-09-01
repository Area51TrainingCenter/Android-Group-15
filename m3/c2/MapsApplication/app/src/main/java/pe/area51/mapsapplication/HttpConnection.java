package pe.area51.mapsapplication;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class HttpConnection {

    public static String doJsonHttpGet(final String url) throws IOException {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        return httpClient.execute(httpGet, new BasicResponseHandler());
    }


}
