package io.github.easy4j.httpconn;

import com.alibaba.fastjson.JSONObject;
import io.github.easy4j.httpconn.handler.JSONResponseHandler;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/** Verifies the public Fastjson response type across dependency revisions. */
public class JSONResponseHandlerTest {

    @Test
    public void parsesSuccessfulJsonResponse() throws IOException {
        HttpURLConnection connection = new HttpURLConnection(new URL("http://localhost/health")) {
            @Override
            public int getResponseCode() {
                return HTTP_OK;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream("{\"status\":\"ready\"}".getBytes(StandardCharsets.UTF_8));
            }

            @Override
            public void disconnect() {
            }

            @Override
            public boolean usingProxy() {
                return false;
            }

            @Override
            public void connect() {
            }
        };

        JSONObject response = new JSONResponseHandler().handleResponse(connection, "UTF-8");
        assertEquals("ready", response.getString("status"));
    }
}
