package com.masion.pattern.threadpool;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MultiThread {

    public static int threadNum = 40;
    public static Map<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();

    public static void printPoolParam(ThreadPoolTaskExecutor executor) {
        int poolSize = executor.getPoolSize();
        int activeCount = executor.getActiveCount();
        int queueSize = executor.getThreadPoolExecutor().getQueue().size();
        long taskCount = executor.getThreadPoolExecutor().getTaskCount();
        System.out.println("count:" + taskCount + "\tpoolSize:" + poolSize + "\tactive:" + activeCount
                + "\tqueueSize:" + queueSize);

    }

    public static void threadPoolTask() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "com/masion/pattern/threadpool/applicationContext.xml");
        final ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) ctx.getBean("threadPoolTaskExecutor");

        for (int i = 0; i < threadNum; i++) {
            String name = "Task-" + i;
            try {
                executor.execute(new TaskThread(name));
            } catch (Exception e) {
                e.printStackTrace();
            }

            printPoolParam(executor);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        while (executor.getActiveCount() > 0) {
            // wait for task end
            printPoolParam(executor);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
        executor.destroy();
    }

    public static void simpleAsyncTask() {

        final TaskExecutor task2 = new SimpleAsyncTaskExecutor();
        task2.execute(new JobThread("Job"));
    }

    public static void timeTask() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                System.out.println(new java.util.Date(System.currentTimeMillis()) + "  Timer schedule: " + ++count);
                for (int i = 0; i < threadNum; i++) {
                    if (countMap.get("" + i) == null) {
                        countMap.put("" + i, 0);
                    }
                    new Thread(new JobThread("" + i), "JobThread" + i).start();
                }
            }
        }, 1000l, 1000l);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        threadPoolTask();
    }

}
