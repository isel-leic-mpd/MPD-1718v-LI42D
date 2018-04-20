package pt.isel.mpd.v1718.li42d.query;

import org.junit.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FountainTest extends Base {

    private final static List<String> strings = asList("Sport", "Lisboa", "e", "Benfica");
    private  Fountain<String> stringFountain;


    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before Class");

    }

    @BeforeClass
    public static void beforeClass1() {
        System.out.println("Before Class1");

    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After Class");
    }

    @Before
    public void beforeTest() {
        System.out.println("Before");
        stringFountain = Fountain.of(strings);
    }

    @After
    public void afterTest() {
        System.out.println("After");
    }


    @Test
    public void shouldMap() {
        // Arrange
        System.out.println("shouldMap");


        // Act
        final Fountain<Integer> res = stringFountain
                .map(String::length)
                .take(2);


        // Assert
        assertIterableEquals(asList(5,6), res.toIterable());
    }

    @Test
    public void shouldSkip() {
        System.out.println("shouldSkip");
        // Arrange
        final int TO_SKIP = 3;

        // Act
        final Fountain<String> res = stringFountain
                .skip(TO_SKIP);


        // Assert

        assertIterableEquals(asList("Benfica"), res.toIterable());
    }
}
