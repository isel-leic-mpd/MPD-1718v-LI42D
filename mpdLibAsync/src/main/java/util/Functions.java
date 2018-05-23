package util;

import java.util.function.Supplier;

public class Functions {

    static class Memoizer<T> implements Supplier<T> {

        private Supplier<T> sup;

        public Memoizer(Supplier<T> supSource) {
            this.sup = () -> { T v = supSource.get(); sup = () -> v; return v; };
        }

        @Override
        public T get() {
            return sup.get();
        }
    }

    public static <T> Supplier<T> memoize(Supplier<T> supSrc) {
//        return new Supplier<T>() {
//            private Supplier<T> sup = () -> { T v = supSrc.get(); sup = () -> v; return v; };
//            @Override
//            public T get() {
//                return sup.get();
//            }
//        };
        return new Memoizer<>(supSrc);
    }

}
