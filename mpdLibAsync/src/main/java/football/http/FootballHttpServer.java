package football.http;

import football.FootballService;
import football.model.FootballDataApi;
import football.model.League;
import football.model.Standing;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import util.HttpRequest;

import java.io.IOException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static util.Logging.log;

public class FootballHttpServer {
    private static final int PORT = 8080;
    private static final String TEMPLATES_DIR = "templates";
    private static FootballService footballService = new FootballService(new FootballDataApi(new HttpRequest()));
    private static final HandlebarsTemplateEngine handlebarsTemplateEngine = HandlebarsTemplateEngine.create();

    public static void main(String[] args) throws IOException {
        Vertx vertx = Vertx.vertx();

        final HttpServer httpServer = vertx.createHttpServer();


        Router router = Router.router(vertx);

        router.route("/leaders").handler(FootballHttpServer::leaders);
        router.get("/league/:id")
                .handler(FootballHttpServer::leagueDetails);
        router.post("/league/:id")
                .handler(FootballHttpServer::leagueDetailsPost);

        httpServer.requestHandler(router::accept);

        httpServer.listen(PORT);

        log("Server listening on port {0}. Bye!!!", PORT);
    }

    private static void leagueDetails(RoutingContext ctx) {

        ctx.put("title", "League Details");

        final League l = new League(1, "Liga Portuguesa");
        ctx.put("league", l);

        // VERY IMPORTANT: the template name in the next call (/details) must have the leading '/', otherwise it wont work
        renderTemplate(ctx, "/leagueDetails");
    }


    private static void leagueDetailsPost(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.headers().add("Content-Type", "text/plain");

        response.end("Post League requested has id " + routingContext.request().getParam("id"));
    }

    private static void leaders(RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.headers().add("Content-Type", "Content-Type: text/html; charset=utf-8");

        footballService.getFirstPlaceOnALlLeagues()
                .thenAccept(standingsStream -> FootballHttpServer.generateStandingsPage(routingContext, standingsStream));
    }

    private static void generateStandingsPage(RoutingContext ctx, Stream<Standing> standingStream) {
        ctx.put("title", "Leagues Leaders");
        ctx.put("leaders", standingStream.collect(toList()));
        renderTemplate(ctx, "/leagueLeaders");

    }

    private static void renderTemplate(RoutingContext ctx, String templateName) {
        handlebarsTemplateEngine.render(ctx, TEMPLATES_DIR, templateName, res -> sendTemplateResult(ctx, res));
        log("Requested template {0} to render.", templateName);
    }


    private static void sendTemplateResult(RoutingContext ctx, AsyncResult<Buffer> res) {
        if (res.succeeded()) {
            ctx.response().end(res.result());
        } else {
            log("Error executing template: ", res.cause());
            ctx.fail(res.cause());
        }

    }

}
