package pt.isel.mpd.v1718.li42d.stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {
        System.out.println("supplier method called");
        return () ->  {
            System.out.println("#supplier function called");
            return new ArrayList<>();
        };
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        //System.out.println("accumulator method called");
        return (l, t) -> {
            //System.out.println("#accumulator function called");
            l.add(t);
        };
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        //System.out.println("combiner method called");
        return (l1, l2) -> {
            //System.out.println("#combiner function called");

            l1.addAll(l2); return l1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        //System.out.println("finisher method  called");
        return l -> {
            //System.out.println("#finisher function called");
            return l;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        //System.out.println("characteristics method  called");
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT, Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH));
    }
}
