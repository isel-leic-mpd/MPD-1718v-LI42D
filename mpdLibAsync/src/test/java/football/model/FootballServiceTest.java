package football.model;

import football.FootballService;
import org.junit.BeforeClass;
import org.junit.Test;
import util.HttpRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class FootballServiceTest {


    private static FootballService footballService;

    @BeforeClass
    public static void beforeClass() throws Exception {

        FootballDataApi api = new FootballDataApi(new HttpRequest());
        footballService = new FootballService(api);

    }

    @Test
    public void shouldGetFirstPlaceTeamOnALlLeagues() {

        // Act
        List<Standing> standings = footballService.getFirstPlaceOnALlLeagues()
                .join()
                .peek(System.out::println)
                .collect(toList());

        // Assert

        assertNotNull(standings);
        int NUM_LEAGUES = 17;
        assertEquals(NUM_LEAGUES, standings.size());

        //standings.forEach(System.out::println);

    }
}
