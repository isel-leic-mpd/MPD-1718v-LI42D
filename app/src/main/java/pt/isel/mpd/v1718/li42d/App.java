package pt.isel.mpd.v1718.li42d;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    Library lib = new Library();
    public String getGreeting() {
        return lib.getGreeting();
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
