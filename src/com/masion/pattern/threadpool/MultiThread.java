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

	public static int threadNum = 20;
	public static Map<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationContext ctx =  new ClassPathXmlApplicationContext("applicationContext.xml");
		final ThreadPoolTaskExecutor task = (ThreadPoolTaskExecutor)ctx.getBean("threadPoolTaskExecutor");
        task.execute(new JobThread("JobTackThread"));

		final TaskExecutor task2 = new SimpleAsyncTaskExecutor();
        task2.execute(new JobThread("JobTackThread2"));
	}

	public static void timeTack() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int count = 0;

			@Override
			public void run() {
				System.out.println(new java.util.Date(System.currentTimeMillis())+ "  Timer schedule: " +  ++count);
				for (int i = 0; i < threadNum; i++) {
					if(countMap.get(""+i)==null){
						countMap.put(""+i, 0);
					}
					new Thread(new JobThread("" + i), "JobThread" + i).start();
				}
			}
		}, 1000l, 1000l);

	}

}
