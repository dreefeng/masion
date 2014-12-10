package com.masion.pattern.threadpool;

public class JobThread implements Runnable {

    private String n = null;

    public JobThread() {

    }

    public JobThread(String name) {
        this.n = name;
    }

    @Override
    public void run() {
        int count = MultiThread.countMap.get(this.n);
        MultiThread.countMap.put(this.n, ++count);
        double rand = Math.random() * 5000;
        while (true) {
            try {
                System.out.println("Thread " + this.n + "\trun time: " + count + " sleep:" + rand);
                Thread.sleep((long) rand);
            } catch (InterruptedException e) {
            }
        }
    }

}