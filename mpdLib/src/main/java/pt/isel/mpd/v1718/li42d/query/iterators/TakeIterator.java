package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;

public class TakeIterator<T> extends BaseIterator<T, T> {
    private int count;

    public TakeIterator(Iterator<T> prevIter, int count) {
        super(prevIter);
        this.count = count;
    }

    @Override
    public T getNext() {
        if(count > 0 && prevIterator.hasNext()) {
            --count;
            return prevIterator.next();
        }
        return null;
    }
}
