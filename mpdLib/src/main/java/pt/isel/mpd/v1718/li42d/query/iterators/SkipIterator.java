package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;

public class SkipIterator<T> extends BaseIterator<T, T> {
    private int count;

    public SkipIterator(Iterator<T> prevIter, int count) {
        super(prevIter);
        this.count = count;
    }

    @Override
    public T getNext() {
        while (prevIterator.hasNext()) {
            final T next = prevIterator.next();
            if (count == 0) {
                return next;
            }
            --count;
        }
        return null;
    }
}
