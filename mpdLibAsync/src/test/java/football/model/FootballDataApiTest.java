package football.model;

import football.data.LeagueDto;
import football.exception.FootballDataApiException;
import util.HttpRequest;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class FootballDataApiTest {
    private HttpRequest httpRequest = new HttpRequest();
    FootballDataApi api = new FootballDataApi(httpRequest);

    @Test
    public void shouldGetAllLeaguesFromApi() throws ExecutionException, InterruptedException, FootballDataApiException {
        // Arrange

        // Act
        Future<Stream<LeagueDto>> futureLeagueDtos = api.getLeagues();
        final Stream<LeagueDto> leagueDtoStream = futureLeagueDtos.get();


        // Assert
        final List<LeagueDto> leagues = leagueDtoStream.collect(toList());
        int NUM_LEAGUES = 17;
        assertEquals(NUM_LEAGUES, leagues.size());


    }
}
