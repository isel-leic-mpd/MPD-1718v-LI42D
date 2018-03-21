package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class MapIterator<T, U> implements Iterator<U> {
    private final Iterator<T> prevIter;
    private final Function<T, U> mapper;

    public MapIterator(Iterator<T> prevIter, Function<T, U> mapper) {
        this.prevIter = prevIter;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return prevIter.hasNext();
    }

    @Override
    public U next() {
        return mapper.apply(prevIter.next());
    }
}
