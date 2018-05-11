package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.stream.Queries;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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

    @Test
    public void shouldCollpaseStreamsWithNullElements() {
        final List<Integer> integers = asList(1, 2, 2, 2, null, null, null, 1);


        final List<Integer> res = Queries.collapse(integers.stream())
                .collect(toList());

        assertIterableEquals(asList(1,2,null,1), res);

    }

    @Test
    public void shouldJoinStreamsWithAllMergedElements() {

        final List<String> strings = asList("a", "ab", "abcd", "abc");
        final List<Integer> numbers = asList(2, 1, 3, 4);

        shouldJoin(strings, numbers, asList("a(1)", "ab(2)", "abcd(4)", "abc(3)"));
    }


    @Test
    public void shouldJoinStreamsWithNoneMergedElements() {

        final List<String> strings = asList("a", "ab", "abcd", "abc");
        final List<Integer> numbers = asList(5,6,7);

        shouldJoin(strings, numbers, asList("a()", "ab()", "abcd()", "abc()", "#(5)", "#(6)", "#(7)"));
    }

    @Test
    public void shouldJoinStreamsWithSomeMergedElements() {

        final List<String> strings = asList("a", "ab", "abcd", "abc");
        final List<Integer> numbers = asList(3,4,5,6,7);

        shouldJoin(strings, numbers, asList("a()", "ab()", "abcd(4)", "abc(3)", "#(5)", "#(6)", "#(7)"));
    }

    @Test
    public void shouldJoinStreamsWithSomeMergedElementsIgnoringRepeatedElements() {

        final List<String> strings = asList("a", "ab", "a", "abc", "ab");
        final List<Integer> numbers = asList(3,4,5,6,4,3);

        shouldJoin(strings, numbers, asList("a()", "ab()", "abc(3)", "#(4)", "#(5)", "#(6)"));
    }


    private void shouldJoin(List<String> strings, List<Integer> numbers, List<String> expected) {


        final Stream<String> res = Queries.join(
                strings.stream(),
                numbers.stream(),
                this::mapStringWithInteger,
                String::length,
                Function.identity()
        );

        assertIterableEquals(expected, res.collect(toList()));
    }

    private String mapStringWithInteger(String s, Integer i) {
        String str1 = s == null ? "#" : s;
        String str2 = i == null ? "" : i.toString();

        return str1 + "(" + str2 + ")";
    }
}
