package football.model;

public class League {
    public int id;
    public String name;

    public League(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
