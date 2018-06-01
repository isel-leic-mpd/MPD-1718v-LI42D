package football.http;

import football.FootballService;
import football.model.FootballDataApi;
import football.model.Standing;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
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


        Router router = Router.router(vertx);

        router.route("/winners").handler(FootballHttpServer::winners);
        router.get("/league/:id")
                .handler(FootballHttpServer::leagueDetails);
        router.post("/league/:id")
                .handler(FootballHttpServer::leagueDetailsPost);

        httpServer.requestHandler(router::accept);

        httpServer.listen(PORT);



        log("Bye!!!");
    }

    private static void leagueDetails(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.headers().add("Content-Type", "text/plain");


        response.end("Get League requested has id " + routingContext.request().getParam("id"));
    }

    private static void leagueDetailsPost(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.headers().add("Content-Type", "text/plain");

        response.end("Post League requested has id " + routingContext.request().getParam("id"));
    }

    private static void winners(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
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
