package football.model;

import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import football.data.LeagueDto;
import football.exception.FootballDataApiException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import util.IRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Class that accesses the Football data API (https://www.football-data.org) ans transforms its
 * responses into DTO entities defined in {@link football.data} package.
 */
public class FootballDataApi {
    private static final String BASE_URL = "https://www.football-data.org/v1/";
    private static final String COMPETITIONS_URL = BASE_URL +  "competitions";
    private static final String API_KEY = "";


    private static Map<String,String> headarsMap = new HashMap<>();


    private final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    private IRequest req;
    final static Gson gson = new Gson();


    static {
     headarsMap.put("X-Auth-Token", API_KEY);
    }

    public FootballDataApi(IRequest req) {
        this.req = req;

    }

    public Future<Stream<LeagueDto>> getLeagues() throws FootballDataApiException {
        Future<String> resp = req.getBody(COMPETITIONS_URL, headarsMap);
        try {
            final String body = resp.get();

            final Stream<LeagueDto> stream = getLeagueDtoStream(body);
            return Futures.immediateFuture(stream);

        } catch (InterruptedException | ExecutionException e) {
            throw new FootballDataApiException(e);
        }


    }

    private Stream<LeagueDto> getLeagueDtoStream(String body) {
        final LeagueDto[] leagueDtos = gson.fromJson(body, LeagueDto[].class);
        return Arrays.stream(leagueDtos);
    }
}
