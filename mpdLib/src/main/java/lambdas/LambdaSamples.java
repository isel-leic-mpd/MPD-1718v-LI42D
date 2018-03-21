package lambdas;

import java.util.function.ToIntFunction;

public class LambdaSamples {

    void m1() {
        Comparable<String> c = s -> s.length();
        ToIntFunction<String> tif = s -> s.length();

        ToIntFunction<MyType> mt = s -> s.length();
    }


}

class MyType {
    int length() {
        return 0;
    }
}
