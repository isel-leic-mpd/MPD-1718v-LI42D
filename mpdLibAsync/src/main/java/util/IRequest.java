package util;

import java.util.Map;
import java.util.concurrent.Future;

public interface IRequest {
    Future<String> getBody(String url, Map<String, String> headers);
}
