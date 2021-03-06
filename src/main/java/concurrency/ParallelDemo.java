package concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ParallelDemo {

    public static int doubleIt(int n) {
        System.out.println(Thread.currentThread().getName() + " with n=" + n);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
        }
        return n * 2;
    }

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(3, 1, 4, 1, 5, 9);

        // Non-functional, with shared mutable state
        int total = 0;
        for (int i : ints) {
            total += i;
        }
        System.out.println("Total = " + total);

        // Use Java 8 Streams
//        total = 0; // local variable; not an attribute
//        IntStream.of(3, 1, 4, 1, 5, 9)
//                .forEach(n -> total += n); // not legal; total needs to be effectively final
//        System.out.println("Total = " + total);

        total = IntStream.of(3, 1, 4, 1, 5, 9)
                .sum();
        System.out.println("Total = " + total);

        long before = System.nanoTime();
        total = IntStream.of(3, 1, 4, 1, 5, 9)
                .parallel()
                .map(ParallelDemo::doubleIt)
                .sum();
        long after = System.nanoTime();
        System.out.println("Total of doubles = " + total);
        System.out.println("time = " + (after - before)/1e9 + " sec");
    }
}
