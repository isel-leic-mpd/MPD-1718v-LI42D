package pt.isel.mpd.v1718.li42d.stream;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Queries {

    public static <T> Stream<T> collapse(Stream<T> src) {
        return StreamSupport.stream(new CollapseSpliterator(src.spliterator()), false);
    }

    public static <T> Stream<T> distinct(Stream<T> src) {
        return StreamSupport.stream(new DistinctSpliterator(src.spliterator()), false);
    }

    private static class CollapseSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
        private Spliterator<T> src;
        Optional<T> prev = Optional.empty();

        public CollapseSpliterator(Spliterator<T> src) {
            super(src.estimateSize(), src.characteristics() & ~Spliterator.SIZED);
            this.src = src;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            Optional<T> oldPrev = prev;
            while (
                    src.tryAdvance(t -> {
                        if (!prev.isPresent() || !Objects.equals(t, prev.get())) {
                            action.accept(t);
                            prev = Optional.ofNullable(t);
                        }
                    }) && oldPrev == prev) ;

            return oldPrev != prev;

        }
    }

    private static class DistinctSpliterator<T> extends Spliterators.AbstractSpliterator<T> {
        private Spliterator<T> src;
        Set<T> distinctElements = new HashSet<>();

        public DistinctSpliterator(Spliterator<T> src) {
            super(src.estimateSize(), src.characteristics() & ~Spliterator.SIZED);
            this.src = src;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            int size = distinctElements.size();
            while (src.tryAdvance(t -> {
                if(distinctElements.add(t)) {
                    action.accept(t);
                }
            }) && size == distinctElements.size());
            return size != distinctElements.size();
        }
    }
}
