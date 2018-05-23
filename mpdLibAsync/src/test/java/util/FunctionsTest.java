package util;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static util.Functions.memoize;

public class FunctionsTest {

    @Test
    public void shouldMemoize() {
        int [] count = {0};
        Supplier <Integer> sup = () -> count[0]++;
        final Supplier<Integer> memoizedSup = memoize(sup);

        int v = memoizedSup.get();
        assertEquals(0, v);

        v = memoizedSup.get();
        assertEquals(0, v);

        v = memoizedSup.get();

        assertEquals(0, v);
        assertEquals(1, count[0]);

    }
}