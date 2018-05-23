package util;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class HttpRequest implements IRequest {
    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public CompletableFuture<String> getBody(String url, Map<String, String> headers) {

        final CompletableFuture<Response> httpClientCf = asyncHttpClient.prepareGet(url).execute().toCompletableFuture();
        CompletableFuture<String> aftrerTransformationCf =
                httpClientCf.thenApply(Response::getResponseBody);
        return aftrerTransformationCf;

    }
}

