package pt.isel.mpd.v1718.li42d.query.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MapIterator<T, U> extends BaseIterator<U, T> {
    private final Function<T, U> mapper;


    public MapIterator(Iterator<T> prevIter, Function<T, U> mapper) {
        super(prevIter);

        this.mapper = mapper;
    }

    @Override
    public U getNext() {
        if(prevIterator.hasNext()) {
            return mapper.apply((prevIterator.next()));
        }
        return null;
    }

}
