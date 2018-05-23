package util;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Future;


public class HttpRequest implements IRequest {
    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public Future<String> getBody(String url, Map<String, String> headers) {
        return asyncHttpClient.prepareGet(url).execute(
                new AsyncHandler<String>() {

                    private String result = "";

                    @Override
                    public State onStatusReceived(HttpResponseStatus responseStatus) throws Exception {
                        System.out.println("onStatusReceived");
                        return State.CONTINUE;
                    }

                    @Override
                    public State onHeadersReceived(HttpHeaders headers) throws Exception {
                        System.out.println("onHeadersReceived");
                        return State.CONTINUE;
                    }

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                        System.out.println("onBodyPartReceived");
                        final String part = new String(bodyPart.getBodyPartBytes());
                        this.result += part;
                        return State.CONTINUE;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        System.out.println("onThrowable");
                    }

                    @Override
                    public String onCompleted() throws Exception {
                        System.out.println("onCompleted");
                        return result;
                    }
                }
        );

    }
}

