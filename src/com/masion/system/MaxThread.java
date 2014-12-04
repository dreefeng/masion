package com.masion.system;

public class MaxThread {

    public static int count = 0;

    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(count++);

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
