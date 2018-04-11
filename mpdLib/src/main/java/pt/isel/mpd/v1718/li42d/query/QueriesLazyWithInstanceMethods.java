package pt.isel.mpd.v1718.li42d.query;



import pt.isel.mpd.v1718.li42d.query.iterators.FilterIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.MapIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.SkipIterator;
import pt.isel.mpd.v1718.li42d.query.iterators.TakeIterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesLazyWithInstanceMethods<T> implements Queries<T> {
    private final Iterable<T> iter;

    public static <T> Queries<T> of(Iterable<T> iter) {
        return new QueriesLazyWithInstanceMethods<>(iter);
    }

    private QueriesLazyWithInstanceMethods(Iterable<T> iter) {
        this.iter = iter;
    }


    @Override
    public Queries<T> filter(Predicate<T> pred) {
        return new QueriesLazyWithInstanceMethods<>(() ->  new FilterIterator(iter.iterator(), pred ));
    }

    @Override
    public <U> Queries<U> map(Function<T, U> mapper) {
        return new QueriesLazyWithInstanceMethods<U>(() -> new MapIterator(iter.iterator(), mapper));
    }

    @Override
    public Queries<T> take(int count) {
        return new QueriesLazyWithInstanceMethods<T>(() -> new TakeIterator(iter.iterator(), count));
    }

    @Override
    public Queries<T> skip(int count) {
        return new QueriesLazyWithInstanceMethods<T>(() -> new SkipIterator(iter.iterator(), count));
    }

    @Override
    public Iterator<T> iterator() {
        return iter.iterator();

    }
}
