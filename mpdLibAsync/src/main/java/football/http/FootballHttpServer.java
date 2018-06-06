package football.http;

import football.FootballService;
import football.model.FootballDataApi;
import football.model.League;
import football.model.Standing;
import io.vertx.core.AsyncResult;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import util.HttpRequest;
import util.Logging;

import java.io.IOException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static util.Logging.log;

public class FootballHttpServer {
    private static final int PORT = 8080;
    private static FootballService footballService = new FootballService(new FootballDataApi(new HttpRequest()));
    private static final HandlebarsTemplateEngine handlebarsTemplateEngine = HandlebarsTemplateEngine.create();

    public static void main(String[] args) throws IOException {
        Vertx vertx = Vertx.vertx();

        final HttpServer httpServer = vertx.createHttpServer();


        Router router = Router.router(vertx);

        router.route("/winners").handler(FootballHttpServer::winners);
        router.get("/league/:id")
                .handler(FootballHttpServer::leagueDetails);
        router.post("/league/:id")
                .handler(FootballHttpServer::leagueDetailsPost);

        httpServer.requestHandler(router::accept);

        httpServer.listen(PORT);

        log("Server listening on port {0}. Bye!!!", PORT);
    }

    private static void leagueDetails(RoutingContext ctx) {
        final HttpServerResponse response = ctx.response();
        response.headers().add("Content-Type", "text/html");

        ctx.put("title", "League Details");

        final League l = new League(1, "Liga Portuguesa");
        ctx.put("league", l);

        // VERY IMPORTANT: the template name in the next call (/details) must have the leading '/0, otherwise it wont work
        handlebarsTemplateEngine.render(ctx, "templates", "/leagueDetails", res -> detailsTemplateHandler(ctx, res));
    }

    private static void detailsTemplateHandler(RoutingContext ctx, AsyncResult<Buffer> res) {
        if (res.succeeded()) {
            ctx.response().end(res.result());
        } else {
            log("Error executing template: ", res.cause());
            ctx.fail(res.cause());
        }

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
