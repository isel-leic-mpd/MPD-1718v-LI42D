package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.stream.ListCollector;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CollectorsTest {

    private String loremIpsum = "Lorem ipsum dolor sit amet consectetuer adipiscing elit. Nascetur sit lacus laoreet class metus sodales. Eni iaculis velit pretium et pretium fringilla gravida massa. In neque mollis placerat viverra. Iaculis tortor vestibulum vitae habitasse enim nam taciti hymenaeos condimentum ridiculus fusce eni nunc quam.\n" +
            "\n" +
            "Vel euismod taciti ut vitae duis nibh posuere molestie. Class malesuada elit. Consequat facilisi ornare cum. Commodo. Praesent. Aliquet dapibus diam fusce maecenas ve pellentesque class aliquam cum vivamus interdum. Scelerisque ultricies condimentum vitae. Luctus felis. Ante. Viverra platea adipiscing ultrices. Posuere sodales pede ut eget sapien ve enim at duis dictumst scelerisque tellus. Ipsum nulla penatibus nam velit vivamus sed. Fermentum vehicula fusce est natoque lacus tellus curabitur semper hymenaeos vel leo. Eu felis consectetuer parturient felis malesuada venenatis. Magna nisl sed dictum tristique non dictum. Platea orci primis leo habitasse lorem vitae penatibus fermentum libero.\n" +
            "\n" +
            "Fames leo litora venenatis gravida per id porta dictumst consectetuer leo. Eu suscipit maecenas orci cras aptent class. Scelerisque pretium fusce nunc ut mattis leo augue varius dignissim est ridiculus dui montes eni. Nostra mi tempus sociosqu auctor cubilia tempor ornare scelerisque cras. Ligula ve aliquam egestas montes vestibulum scelerisque platea ad lacinia netus parturient ornare luctus iaculis. Proin torquent integer natoque fames amet blandit est luctus pede fames parturient imperdiet. Odio hac nunc tellus mi pretium sollicitudin torquent eleifend commodo.\n" +
            "\n" +
            "Leo magnis enim rhoncus eni! Cursus nullam? Maecenas risus et odio elit hymenaeos ac ve taciti hymenaeos vestibulum adipiscing erat. Neque nostra eu torquent. Duis auctor id senectus blandit sodales taciti ac ac sit erat ad sollicitudin lobortis.\n" +
            "\n" +
            "Ve tellus mus adipiscing aliquet volutpat hymenaeos duis lorem urna aenean venenatis diam pellentesque sed cubilia. Morbi elit lorem. Hymenaeos donec erat dolor. Nulla elit primis phasellus ante commodo nisi tristique risus felis. Ad arcu tempus nonummy sit habitasse ac tristique tortor dictum lectus aptent! Fames ad nunc mauris placerat conubia adipiscing eget senectus potenti hendrerit congue magna rutrum penatibus. Senectus vivamus tempor dis posuere. Convallis eni convallis. Aenean inceptos per lacus pretium proin felis feugiat eni potenti ipsum euismod ac egestas venenatis. Aliquam velit mi class tellus facilisi sodales potenti nullam vestibulum proin malesuada quis. Sem congue phasellus scelerisque malesuada massa per erat ante.";

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

        final Map<Integer, String> res = strings.stream().collect(
                toMap(String::length, identity(), (s1, s2) -> s1 + s2)
        );


        assertEquals(strings.size(), res.keySet().size());
        assertEquals(strings.size(), res.values().size());


    }

    @Test
    public void shouldCreateAListUsingToColectionCollector() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        List<String> res = strings.stream().collect(toCollection(LinkedList::new));

        assertIterableEquals(strings, res);
    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollector() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining());

        assertEquals("SportLisboaeBenficae", res);
    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollectorWithCustomSeparator() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining("#"));

        assertEquals("Sport#Lisboa#e#Benfica#e", res);
    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollectorWithCustomSeparatorPrefixAndSufix() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining("#", "[", "]"));

        assertEquals("[Sport#Lisboa#e#Benfica#e]", res);
    }

    @Test
    public void shouldGroupByStringSizeAndCount() {
        // String -> Map<Integer, List<String>>>

        final String[] words = loremIpsum.split("[,|\\s|.|!|\\?]");

        Map<Integer, Long> res = Arrays.stream(words).collect(Collectors.groupingBy(String::length, counting()));


        String str = res.entrySet().stream().map(Object::toString).collect(joining(","));
        System.out.println(str);


    }

    @Test
    public void shouldGroupByFirstCharAndThenByWordSizeAndCount() {
        // String -> Map<Char, Map<Integer, Long>>>

        final String[] words = loremIpsum.split("[,|(\\s+)|\\.|!]");

        Map<Character, Map<Integer, Long>> res =   Arrays.stream(words).collect(
                groupingBy(
                        s -> s.length() != 0 ? s.toLowerCase().charAt(0) : '#',
                        groupingBy(String::length, counting())
                )
        );


        String str = res.entrySet().stream().map(Object::toString).collect(joining(","));
        System.out.println(str);


    }
}
