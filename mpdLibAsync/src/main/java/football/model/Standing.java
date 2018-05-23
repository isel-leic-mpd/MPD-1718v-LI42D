package football.model;

public class Standing {
    private final String teamName;
    private final int points;
    private final String leagueName;

    public Standing(String teamName, int points, String leagueName) {
        this.teamName = teamName;
        this.points = points;
        this.leagueName = leagueName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPoints() {
        return points;
    }

    public String getLeagueName() {
        return leagueName;
    }

    @Override
    public String toString() {
        return "Standing{" +
                "teamName='" + teamName + '\'' +
                ", points=" + points +
                ", leagueName='" + leagueName + '\'' +
                '}';
    }
}
