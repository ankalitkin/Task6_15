package ru.vsu.cs.course1;
import java.lang.ref.WeakReference;

public class PerfTester {
    public static long[] testDummy(int iterations, int timesPerIteration) {
        long[] nanoTime = new long[iterations + 1];
        gc();
        nanoTime[0] = System.nanoTime();
        for (int i = 1; i <= iterations; i++) {
            for (int j = 0; j < timesPerIteration; j++) {
                //Dummy
            }
            nanoTime[i] = System.nanoTime();
        }
        return nanoTime;
    }

    public static double[][] millisXY(long[] nanoTime, int timesPerIteration) {
        double[][] res = new double[2][nanoTime.length];
        for (int i = 0; i < nanoTime.length; i++) {
            res[0][i] = i * timesPerIteration;
            res[1][i] = (nanoTime[i] - nanoTime[0]) / 1000000.0;
        }
        return res;
    }

    /**
     * This method guarantees that garbage collection is
     * done unlike <code>{@link System#gc()}</code>
     */
    public static void gc() {
        Object obj = new Object();
        WeakReference ref = new WeakReference<Object>(obj);
        obj = null;
        while(ref.get() != null) {
            System.gc();
        }
    }
}
