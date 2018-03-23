package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilterIterator<T> extends BaseIterator<T, T> {
    private final Predicate<T> pred;

    public FilterIterator(Iterator<T> prevIterator, Predicate<T> pred) {
        super(prevIterator);
        this.pred = pred;
    }

    @Override
    public T getNext() {
        T current = null;
        while (prevIterator.hasNext()) {
            if(pred.test(current = prevIterator.next())) {
                return current;
            }
        }
        return null;
    }
}
