package football.http;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;

import static util.Logging.log;

public class SimpleHttpServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Vertx vertx = Vertx.vertx();

        //HttpServerOptions options = new HttpServerOptions().setLogActivity(true);

        final HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(SimpleHttpServer::processRequestChunked);

        httpServer.listen(PORT);



        log("Bye!!!");
    }


    private static void processRequest(HttpServerRequest httpServerRequest) {
        final HttpServerResponse response = httpServerRequest.response();
        log("uri:" + httpServerRequest.absoluteURI());
        log("method: " + httpServerRequest.method());
        log("toString: " + httpServerRequest);


        response.setStatusCode(200);
        response.headers().add("Content-Type", "text/plain");
        response.end("SLB!!!");
    }

    private static void processRequestChunked(HttpServerRequest httpServerRequest) {
        final HttpServerResponse response = httpServerRequest.response();
        response.setChunked(true);


        response.setStatusCode(200);
        response.headers().add("Content-Type", "text/plain; charset=utf-8");

        MultiMap trailers = response.trailers();
        trailers.set("X-wibble", "woobble").set("X-quux", "flooble");
        response.write("SLB!!!");
        try {
            Thread.sleep(2000);
            response.write("Tetra campeão!!!");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
