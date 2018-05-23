package util;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IRequest {
    CompletableFuture<String> getBody(String url, Map<String, String> headers);
}
