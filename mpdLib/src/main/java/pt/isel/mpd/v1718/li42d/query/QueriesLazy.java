package pt.isel.mpd.v1718.li42d.query;



import pt.isel.mpd.v1718.li42d.query.iterators.FilterIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.MapIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.SkipIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.TakeIterator;

import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesLazy extends QueriesBase {
    public static <T> Iterable<T> filter(Iterable<T> iter, Predicate<T> pred) {
        return () -> new FilterIterator(iter.iterator(), pred );
    }

    public static <T, U> Iterable<U> map(Iterable<T> iter, Function<T, U> mapper) {
        return () -> new MapIterator(iter.iterator(), mapper);
    }

    public static <T> Iterable<T> take(Iterable<T> iter, int count) {
        return () -> new TakeIterator(iter.iterator(), count);
    }


    public static <T> Iterable<T> skip(Iterable<T> iter, int count) {
        return () -> new SkipIterator(iter.iterator(), count);
    }
}
