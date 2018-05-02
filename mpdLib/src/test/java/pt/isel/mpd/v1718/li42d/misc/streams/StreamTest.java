package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.stream.Queries;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class StreamTest {

    private List<String> strings = asList("Sport", "Lisboa", "e", "Benfica");

    @Test
    public void internalVsExternalIteration() {

        // External iteration
        for (String s : strings) {
            if(s.length() > 3)
                System.out.println(s.length());
        }

        // Internal iteration
        strings.stream()
                .filter(s -> s.length() > 3)  // Stream<String>
                .mapToInt(s -> s.length())          // Stream<Integer>
                .forEach(System.out::println);


        int size = strings.stream().parallel().reduce(0, (cnt, t) -> ++cnt, this::combiner);

        System.out.println(size);


    }

    private int combiner(int u1, int u2) {
        System.out.println("combiner called");
        return u1 + u2;
    }



    @Test
    public void shouldMergeEqualAdjacentElements() {
        final List<Integer> integers = asList(1, 2, 2, 2, 3, 4, 2, 2, 1, 1);


        final List<Integer> res = Queries.collapse(integers.stream())
                .collect(toList());

        assertIterableEquals(asList(1,2,3,4,2,1), res);

    }

    @Test
    public void shouldMergeEqualAdjacentElementsForASequenceWithAllElementsEqual() {
        final List<Integer> integers = asList(2, 2, 2, 2, 2);


        final List<Integer> res = Queries.collapse(integers.stream())
                .collect(toList());

        assertIterableEquals(asList(2), res);

    }


    @Test
    public void shouldProduceStreamOfDistinctElements() {
        final List<Integer> integers = asList(1, 2, 2, 2, 3, 4, 2, 2, 1, 1);


        final List<Integer> res = Queries.distinct(integers.stream())
                .collect(toList());

        assertIterableEquals(asList(1,2,3,4), res);

    }
}
