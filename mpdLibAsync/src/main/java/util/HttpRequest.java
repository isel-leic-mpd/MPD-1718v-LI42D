package util;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.RequestBuilder;

import java.util.Map;
import java.util.concurrent.Future;
import static org.asynchttpclient.Dsl.get;

public class HttpRequest implements IRequest {
    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public Future<String> getBody(String url, Map<String, String> headers) {
        final RequestBuilder requestBuilder = get(url);

        headers.entrySet().stream().forEach(e -> requestBuilder.setHeader(e.getKey(), e.getValue()));
        asyncHttpClient.executeRequest(requestBuilder.build());
        return null;
    }
}
