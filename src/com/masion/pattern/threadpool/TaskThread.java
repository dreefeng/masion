package com.masion.pattern.threadpool;

public class TaskThread implements Runnable {

    private String n = null;

    public TaskThread() {

    }

    public TaskThread(String name) {
        this.n = name;
    }

    @Override
    public void run() {
        double rand = Math.random() * 5000;
        long time = (long) rand;
        System.out.println(this.n + " start:" + time + "ms");
        try {
            //System.out.println("Thread(" + this.n + ")\tsleep:" + time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

}