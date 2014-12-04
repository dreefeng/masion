package com.masion.system;

import java.util.ArrayList;
import java.util.List;

public class GCTest {

    public transient String str = "this is a transient string, not be transport by RMI";

    private static long MB = 1024 * 1024;

    public GCTest() {
    }

    public static void gc() {
        Runtime r = Runtime.getRuntime();
        long usedMemory = r.totalMemory() - r.freeMemory();
        System.out.println("Memory: " + (usedMemory) / MB + "M/" + r.totalMemory() / MB + "M");

        for (int i = 0; i < 10000000; ++i) {
            GCTest g = new GCTest();
        }
        usedMemory = r.totalMemory() - r.freeMemory();
        System.out.println("Before gc:");
        System.out.println("Memory: " + (usedMemory) / MB + "M/" + r.totalMemory() / MB + "M");
		System.gc();
        usedMemory = r.totalMemory() - r.freeMemory();
        System.out.println("After gc:");
        System.out.println("Memory: " + (usedMemory) / MB + "M/" + r.totalMemory() / MB + "M");
    }


    public static void outOfMemory() {
        List list = new ArrayList<String>();
        Runtime rt = Runtime.getRuntime();
        long usedMemory = rt.totalMemory() - rt.freeMemory();
        for (int j = 0; j < 100000; ++j) {
            for (int k = 0; k < 1000000; ++k) {
                list.add("12345678");
            }
            usedMemory = rt.totalMemory() - rt.freeMemory();
            System.out.println("Memory: " + (usedMemory) / MB + "M/" + rt.totalMemory() / MB + "M");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        outOfMemory();
    }
}