package util;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.asynchttpclient.Dsl.get;

public class HttpRequest implements IRequest {
    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public Future<String> getBody(String url, Map<String, String> headers) {
        try {

            // Obtain the Response
            final RequestBuilder requestBuilder = get(url);

            headers.entrySet().stream().forEach(e -> requestBuilder.setHeader(e.getKey(), e.getValue()));
            final Future<Response> responseFuture = asyncHttpClient.executeRequest(requestBuilder.build());
            final Response response = responseFuture.get();

            // Obtain the body content as a String
            final String responseBody = response.getResponseBody();

            return new MyImmediateFuture(responseBody);

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
