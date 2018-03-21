package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;
    private final Predicate<T> pred;

    public FilterIterator(Iterator<T> iterator, Predicate<T> pred) {
        this.iterator = iterator;
        this.pred = pred;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }
}
