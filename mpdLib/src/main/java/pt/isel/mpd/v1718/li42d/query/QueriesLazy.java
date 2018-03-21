package pt.isel.mpd.v1718.li42d.query;



import pt.isel.mpd.v1718.li42d.query.iterators.FilterIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.MapIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesLazy {
    public static <T> Iterable<T> filter(Iterable<T> iter, Predicate<T> pred) {
        return () -> new FilterIterator(iter.iterator(), pred );
    }

    public static <T, U> Iterable<U> map(Iterable<T> iter, Function<T, U> mapper) {
        return () -> new MapIterator(iter.iterator(), mapper);
    }

    public static <T> Iterable<T> take(Iterable<T> coll, int count) {
        Collection<T> result = new ArrayList<>();
        if(count < 0) {
            throw new IllegalArgumentException("count");
        }

        for (T curr : coll) {
            if(count-- == 0) {
                break;
            }
            result.add(curr);
        }
        return result;
    }


    public static <T> Iterable<T> skip(Iterable<T> coll, int count) {
        Collection<T> result = new ArrayList<>();
        if(count < 0) {
            throw new IllegalArgumentException("count");
        }

        for (T curr : coll) {
            if(count == 0) {
                result.add(curr);
            } else {
                --count;
            }
        }
        return result;
    }
}
