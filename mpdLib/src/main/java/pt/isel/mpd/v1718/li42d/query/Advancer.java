package pt.isel.mpd.v1718.li42d.query;

import java.util.function.Consumer;

public interface Advancer<T> {
    boolean tryAdvance(Consumer<T> consumer);

}
