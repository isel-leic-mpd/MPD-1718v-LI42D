package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class BaseIterator<T, P> implements Iterator<T>{
    protected final Iterator<P> prevIterator;
    private T current;

    public BaseIterator(Iterator<P> prevIterator) {
        if(prevIterator == null) {
            throw new IllegalArgumentException("prevIterator");
        }
        this.prevIterator = prevIterator;
    }

    @Override
    public boolean hasNext() {
        current = getNext();
        return current != null;
    }

    protected abstract T getNext();

    @Override
    public T next() {
        if(current == null) {
            throw  new NoSuchElementException();
        }
        return current;
    }
}
