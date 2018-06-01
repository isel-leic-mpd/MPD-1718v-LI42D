package football;

import football.data.LeagueDto;
import football.data.LeagueTableDto;
import football.data.StandingDto;
import football.model.FootballDataApi;
import football.model.Standing;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FootballService {
    private FootballDataApi api;

    public FootballService(FootballDataApi api) {
        this.api = api;
    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues() {
        return api.getLeagues()         // CompletableFuture<Stream<LeagueDto>>
                .thenApply(leagueDtoStream ->
                        leagueDtoStream                             // Stream<leagueDto>
                                .map(l -> api.toLeagueTable(l.id))  // Stream<CompletableFuture<LeagueTableDto>>
                                .collect(toList())
                                .stream()
                                .map(CompletableFuture::join)               // Stream<LeagueTableDto>
                                .map(this::convertLeagueTableDtoToStanding) // Stream<Standing>
                ) // CompletableFuture<Stream<Standing>>
        ;


    }

    public CompletableFuture<Stream<Standing>> getFirstPlaceOnALlLeagues1() {
        return api.getLeagues()
                .thenCompose(this::processLeagues);

    }

    private CompletableFuture<Stream<Standing>> processLeagues(Stream<LeagueDto> leagueDtoStream) {

        final CompletableFuture<LeagueTableDto>[] leagueTableFutures =
                leagueDtoStream.map(l -> api.toLeagueTable(l.id))     // Stream<CompletableFuture<LeagueTableDto>
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(leagueTableFutures)      // CompletableFuture<Void>
                .thenApply(v -> convertLeagueTablesToStandings(leagueTableFutures));


    }

    private Stream<Standing>  convertLeagueTablesToStandings(CompletableFuture<LeagueTableDto>[] leagueTableFutures) {
        return Arrays.stream(leagueTableFutures)                // Stream<CompletableFuture<LeagueTableDto>>
                .map(CompletableFuture::join)                   // Stream<LeagueTableDto>
                .map(this::convertLeagueTableDtoToStanding);    // Stream<Standing>
    }

    private Standing convertLeagueTableDtoToStanding(LeagueTableDto leagueTableDto) {
        String teamName = "Dummy";
        int points = 99;
        String leagueName = leagueTableDto.leagueCaption;

        if(leagueTableDto.standing != null) {
            StandingDto standingDto = leagueTableDto.standing[0];
            teamName = standingDto.teamName;
            points = standingDto.points;
        }
        return new Standing(teamName, points, leagueName);
    }
}
