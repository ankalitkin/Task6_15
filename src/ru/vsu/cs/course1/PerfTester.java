package ru.vsu.cs.course1;

import org.jfree.data.xy.DefaultXYDataset;

import java.lang.ref.WeakReference;
import java.util.*;

public class PerfTester {
    private interface Operation {
        void process(Map<String, String> map, String string);
    }

    private static String[] getKeysArray(int keyLength, int count) {
        if (keys == null || keys.length < count || keys[0].length() != keyLength)
            keys = randomStringArray(keyLength, count);
        return keys;
    }

    private static Random random = new Random();
    private static String[] keys;

    public static double[][] performanceTest(int iterations, int timesPerIteration, Map<String, String> map, String[] keys, Operation[] operations) {
        long[] nanoTime = new long[iterations+1];
        gc();
        for (Operation operation : operations) {
            int index = 0;
            long startTime = System.nanoTime();
            for (int i = 1; i <= iterations; i++) {
                for (int j = 0; j < timesPerIteration; j++, index++) {
                    operation.process(map, keys[index]);
                }
                nanoTime[i] += System.nanoTime() - startTime;
            }
        }
        return millisXY(nanoTime, timesPerIteration);
    }

    public static DefaultXYDataset getPerformanceData(int iterations, int timesPerIteration, String[] keys, Operation prepare, Operation[] operations, Map<String, String>[] maps, String[] names) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        for (int i = 0; i < maps.length; i++) {
            if (prepare != null) {
                for (int j = 0; j < iterations * timesPerIteration; j++)
                    prepare.process(maps[i], keys[j]);
            }
            double[][] xy = performanceTest(iterations, timesPerIteration, maps[i], keys, operations);
            dataset.addSeries(names[i], xy);
        }
        return dataset;
    }

    public static DefaultXYDataset testPerformanceMulti(int iterations, int timesPerIteration, int keyLength, Operation prepare, Operation[] operations) {
        Map[] maps = {new TreeMap(), new HashMap(), new LinkedHashMap()};
        String[] names = {"TreeMap", "HashMap", "LinkedHashMap"};
        String[] keys = getKeysArray(keyLength, iterations * timesPerIteration);
        return getPerformanceData(iterations, timesPerIteration, keys, prepare, operations, maps, names);
    }

    public static DefaultXYDataset testInsertPerformance(int iterations, int timesPerIteration, int keyLength) {
        return testPerformanceMulti(iterations, timesPerIteration, keyLength, null, new Operation[]{(map, string) -> map.put(string, string)});
    }

    public static DefaultXYDataset testDeletePerformance(int iterations, int timesPerIteration, int keyLength) {
        return testPerformanceMulti(iterations, timesPerIteration, keyLength, (map, string) -> map.put(string, string), new Operation[]{(map, string) -> map.remove(string)});
    }

    public static DefaultXYDataset testInsertAndDeletePerformance(int iterations, int timesPerIteration, int keyLength) {
        return testPerformanceMulti(iterations, timesPerIteration, keyLength, null, new Operation[]{(map, string) -> map.put(string, string), (map, string) -> map.remove(string)});
    }

    public static DefaultXYDataset testFindPerformance(int iterations, int timesPerIteration, int keyLength) {
        return testPerformanceMulti(iterations, timesPerIteration, keyLength, (map, string) -> map.put(string, string), new Operation[]{(map, string) -> map.get(string)});
    }

    public static String[] randomStringArray(int keyLength, int count) {
        byte[] bytes = new byte[keyLength];
        String[] res = new String[count];
        for (int i = 0; i < count; i++) {
            random.nextBytes(bytes);
            res[i] = new String(bytes);
        }
        return res;
    }

    public static double[][] millisXY(long[] nanoTime, int timesPerIteration) {
        double[][] res = new double[2][nanoTime.length];
        for (int i = 0; i < nanoTime.length; i++) {
            res[0][i] = i * timesPerIteration;
            res[1][i] = nanoTime[i] / 1000000.0;
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
        while (ref.get() != null) {
            System.gc();
        }
    }
}
