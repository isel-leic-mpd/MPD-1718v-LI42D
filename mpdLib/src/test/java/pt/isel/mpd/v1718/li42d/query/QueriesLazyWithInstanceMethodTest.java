package pt.isel.mpd.v1718.li42d.query;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class QueriesLazyWithInstanceMethodTest {

    private List<String> strings = asList("Sport", "Lisboa", "e", "Benfica");
    private Iterable<String> iter;




    @Test
    public void shouldGetSecondPageAndMap() {
        // Arrange


        List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica");

        // Act
        final Queries<Integer> res = QueriesLazyWithInstanceMethods.of(strings)
                .skip(3)
                .take(3)
                .map(String::length);



        // Assert
        assertIterableEquals(asList(7,5,6), res);
    }
}
