package pt.isel.mpd.v1718.li42d.query;


import java.util.function.Function;
import java.util.function.Predicate;

public interface Queries<T> extends Iterable<T> {
    Queries<T> filter(Predicate<T> pred);
    <U> Queries<U> map(Function<T, U> mapper);
    Queries<T> take(int count);
    Queries<T> skip(int count);
}
