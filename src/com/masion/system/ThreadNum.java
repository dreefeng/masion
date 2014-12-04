package com.masion.system;

public class ThreadNum {

    public static Thread[] findAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // traverse the ThreadGroup tree to the top
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // Create a destination array that is about
        // twice as big as needed to be very confident
        // that none are clipped.
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // Load the thread references into the oversized
        // array. The actual number of threads loaded
        // is returned.
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return list;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Thread thd = new Thread("aa"){
            public void run(){
                System.out.println("Thread aa start.");
                while(true){
                }
            }
        };

        thd.start();

        Thread[] allThreads = findAllThreads();
        for (Thread th : allThreads) {
            System.out.println(th);
        }

    }

}
