package pt.isel.mpd.v1718.li42d.query;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Fountain<T> extends Advancer<T> {
    static <T> Fountain<T> of(Iterable<T> iter) {
        final Iterator<T> it = iter.iterator();
        return consumer -> {
            if(it.hasNext()) {
                consumer.accept(it.next());
                return true;
            }
            return false;
        };
    }
//    Fountain<T> filter(Predicate<T> pred);
    default <U> Fountain<U> map(Function<T, U> mapper) {
        return consumer ->
                this.tryAdvance(t -> consumer.accept(mapper.apply(t)));
    }

    default Fountain<T> take(int count) {
        final int []taken = {0};
        return consumer ->
                taken[0] < count && this.tryAdvance((t) -> {
                    consumer.accept(t);
                    ++taken[0];
                });

    }
    default Fountain<T> skip(int count) {
        final int[] skipped = {0};
        return consumer -> {
            while(skipped[0]++ < count && tryAdvance(__ -> { }));
            return tryAdvance(consumer);
        };
    }

    default void forEach(Consumer<T> consumer) {
        while (this.tryAdvance(consumer));
    }

    default Iterable<T> toIterable() {
        return () -> new Iterator<T>() {

            public Optional<T> current = Optional.empty();

            @Override
            public boolean hasNext() {
                if(current.isPresent()) {
                    return true;
                }

                return tryAdvance(t -> current = Optional.ofNullable(t));
            }

            @Override
            public T next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                final T t = current.get();
                current = Optional.empty();
                return t;
            }
        };
    }

}
