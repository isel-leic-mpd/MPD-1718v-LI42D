package pt.isel.mpd.v1718.li42d.misc.streams;

import org.junit.Test;
import pt.isel.mpd.v1718.li42d.stream.ListCollector;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CollectorsTest {

    @Test
    public void shouldCollectAStreamToAList() {
        final List<Integer> integers = asList(1, 2, 2, 2, 3, 4, 2, 2, 1, 1);



        final List<Integer> res = integers.stream().parallel()
                .collect(new ListCollector<>());
        assertIterableEquals(asList(1, 2, 2, 2, 3, 4, 2, 2, 1, 1), res);

    }
}
