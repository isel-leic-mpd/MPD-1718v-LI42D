package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.stream.ListCollector;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static junit.framework.TestCase.assertEquals;

public class CollectorsTest {

    @Test
    public void shouldCollectAStreamToAListWithACollector() {
        final int MAX = 10;

        final List<Integer> res = IntStream.range(0, MAX).mapToObj(i -> i)
                .parallel()
                .collect(new ListCollector<>());

        assertEquals(MAX, res.size());

    }

    @Test
    public void shouldCollectAStreamToAListUsingCollectTreeArgumentsOverload() {

        final int MAX = 1_00;

        final ArrayList<Integer> identity = new ArrayList<>();
        System.out.println("Identity hashcode: " + identity.hashCode());
        final List<Integer> res = IntStream.range(0, MAX).mapToObj(i -> i).parallel()
                .collect(
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                );

        assertEquals(MAX, res.size());
    }


    @Test
    public void shouldCollectAStreamToAListUsingReduce() {

        // This implementation is wrong because is not immutable and unpredicted behaviour can
        // occur on parallel streams
        final int MAX = 2;

        final ArrayList<Integer> identity = new ArrayList<>();
        System.out.println("Identity hashcode: " + identity.hashCode());
        final List<Integer> res = IntStream.range(0, MAX).mapToObj(i -> i).parallel()
                .reduce(
                        identity,
                        (l, t) -> {
                            l.add(t);
                            System.out.println(MessageFormat.format("accumulator called with list {0} ({2}) and element {1} ", l, t, l.hashCode()));
                            return l;
                        },
                        (l1, l2) -> {
                            System.out.println(MessageFormat.format("combiner called with list1 {0} ({2}) and list2 ({3}) {1} ", l1, l2, l1.hashCode(), l2.hashCode()));
                            final ArrayList<Integer> elems = new ArrayList<>();
                            elems.addAll(l1);
                            elems.addAll(l2);
                            return elems;
                        }
                );

        assertEquals(MAX, res.size());
    }

    @Test
    public void shouldReduceAStreamToAMap() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        final Map<Integer, String> res = strings.stream().collect(toMap(String::length, identity(), (s1, s2) -> s1 + s2));


        assertEquals(strings.size(), res.keySet().size());
        assertEquals(strings.size(), res.values().size());


    }
}
