package pt.isel.mpd.v1718.li42d.misc.lambdas;




import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferencesTests {
    @Test
    public void methodReferncesTests() {
        final String slb = "SLB";


        // Using lambda syntax
        Function<String, Integer> sToI = s -> Integer.parseInt(s);

        int i1 = sToI.apply("23");
        int i2 = sToI.apply("abc");

        // 1: Using method reference to static method syntax
        Function<String, Integer> sToi1 = Integer::parseInt;

        i1 = sToI.apply("23");
        i2 = sToI.apply("abc");


        // 2: Using method reference to instance method syntax, for an arbitrary reference
        sToI = s -> s.length();
        i1 = sToI.apply("23");
        i2 = sToI.apply("abc");

        sToI = String::length;

        BiFunction<String, Integer, String> siToS = (s, i) -> s.substring(i);
        siToS = String::substring;

        // With this sample a method reference is not possible, because the first argument is an integer (not a string)
        BiFunction<Integer, String, String> isToS =  (i, s) -> s.substring(i);

        String res = siToS.apply("abcdef", 2);
        i2 = sToI.apply("abc");


        // 3: Using method reference to instance method syntax, for an specific instance§
        Supplier<Integer> supI = () -> slb.length();
        supI = slb::length;

        Point p = new Point(3,4);

        Function<Point, Integer> pToI = point -> point.getX();
        pToI = Point::getX;


        // 4: Using constructor reference
        BiFunction<Integer, Integer, Point> iiToPoint = (x, y) -> new Point(x, y);
        iiToPoint = Point::new;

        p = iiToPoint.apply(2,3);

        Supplier<Point>  supPoint = () -> new Point();
        supPoint = Point::new;


        final Point point = supPoint.get();
        final Point point1 = supPoint.get();


        Function<Integer, String[]> iToAs = (i) -> new String[i];
        iToAs = String[]::new;

        Function<Integer, int[]> iToAi = (i) -> new int[i];
        iToAi = int[]::new;

        Function<Integer, Point[]> iToAp = (i) -> new Point[i];
        iToAp = Point[]::new;

    }
}

class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    Point() {
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

