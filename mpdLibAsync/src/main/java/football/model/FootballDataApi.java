package football.model;

import com.google.gson.Gson;
import football.data.LeagueDto;
import football.data.LeagueTableDto;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import util.IRequest;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Class that accesses the Football data API (https://www.football-data.org/v1/) ans transforms its
 * responses into DTO entities defined in {@link football.data} package.
 */
public class FootballDataApi {
    private static final String BASE_URL = "https://www.football-data.org/v1/";
    private static final String COMPETITIONS_URL = BASE_URL +  "competitions";
    private static final String LEAGUE_TABLE_URL = BASE_URL +  "competitions/{0}/leagueTable";
    private static final String API_KEY = "f8bc89b8024f459e8a2855df9b934469";




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

    public CompletableFuture<Stream<LeagueDto>> getLeagues()  {
        CompletableFuture<String> resp = req.getBody(COMPETITIONS_URL, headarsMap);
         return resp.thenApply(this::toDtoStream);
    }

    public CompletableFuture<LeagueTableDto> toLeagueTable(int leagueId) {
        return req.getBody(MessageFormat.format(LEAGUE_TABLE_URL, leagueId), headarsMap)  // CompletableFuture<String>
                .thenApply(this::toLeagueTable);        // CompletableFuture<LeagueTableDto>
    }


    private LeagueTableDto toLeagueTable(String body) {
        System.out.println("toLeagueTable");
        final LeagueTableDto leagueTableDto = gson.fromJson(body, LeagueTableDto.class);
        return leagueTableDto;

    }

    private Stream<LeagueDto> toDtoStream(String body) {
        System.out.println("toDtoStream");
        final LeagueDto[] leagueDtos = gson.fromJson(body, LeagueDto[].class);
        return Arrays.stream(leagueDtos);
    }


}
