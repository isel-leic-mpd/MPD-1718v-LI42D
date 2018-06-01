package football.http;

import football.FootballService;
import football.model.FootballDataApi;
import football.model.Standing;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import util.HttpRequest;
import util.Logging;

import java.io.IOException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static util.Logging.log;

public class FootballHttpServer {
    private static final int PORT = 8080;
    private static FootballService footballService = new FootballService(new FootballDataApi(new HttpRequest()));

    public static void main(String[] args) throws IOException {
        Vertx vertx = Vertx.vertx();

        //HttpServerOptions options = new HttpServerOptions().setLogActivity(true);

        final HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(FootballHttpServer::processRequest);

        httpServer.listen(PORT);



        log("Bye!!!");
    }


    private static void processRequest(HttpServerRequest httpServerRequest) {
        final HttpServerResponse response = httpServerRequest.response();
        response.setStatusCode(200);
        response.headers().add("Content-Type", "text/plain");

        footballService.getFirstPlaceOnALlLeagues()
                .thenAccept(standingStream ->
                        response.end(generateStandingsString(standingStream))
                );
    }

    private static String generateStandingsString(Stream<Standing> standingStream) {
        return standingStream
                .map(Standing::toString)
                .peek(Logging::log)
                .collect(joining("\n"));
    }

}
