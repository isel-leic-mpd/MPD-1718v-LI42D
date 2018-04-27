package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

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

    public <T> List<T>toList(Iterable<T> iter) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iter.iterator(), 0), false)
        .collect(Collectors.toList());
    }


}
