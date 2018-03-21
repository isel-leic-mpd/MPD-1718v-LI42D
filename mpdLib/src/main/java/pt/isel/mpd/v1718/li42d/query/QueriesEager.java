package pt.isel.mpd.v1718.li42d.query;



import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesEager {
    public static <T> Iterable<T> filter(Iterable<T> coll, Predicate<T> f) {
        Collection<T> result = new ArrayList<>();
        for (T curr : coll) {
            if(f.test(curr))
                result.add(curr);
        }
        return result;
    }

    public static <T, U> Iterable<U> map(Iterable<T> coll, Function<T, U> mapper) {
        Collection<U> result = new ArrayList<>();
        for (T curr : coll) {
            result.add(mapper.apply(curr));
        }
        return result;
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
