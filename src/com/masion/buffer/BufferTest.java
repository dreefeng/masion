package com.masion.buffer;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BufferTest {

	Buffer buf = new Buffer();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public class ThreadA extends Thread {

		public void run() {
			int i = 0;
			while (true) {
				buf.modify("A" + i++);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}

		}

	}

	public class ThreadB extends Thread {

		public void run() {
			int i = 0;
			while (true) {
				buf.modify("B" + i++);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}

		}

	}

	class ThreadC extends Thread {

		public void run() {
			while (true) {
				buf.printMap();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}

		}

	}

	public void testSyn() {
		new ThreadA().start();
		new ThreadB().start();
		new ThreadC().start();
	}

	public void filledBuff(int num){
		for (int i = 0; i < num; i++) {
			buf.add("" + i);
			Buffer.getMap().put(i + "", i + "");
		}
	}

	@Test
	public void test() {
		int testNum = 500000;
		int testTime = 100;
		this.filledBuff(testNum);
		int avgMap = 0;
		int avgList = 0;
		for (int i = 0; i < testTime; i++) {
			System.out.print(i + ":");
			Random rand = new Random();
			int key = rand.nextInt() % testNum;
			if(key <  0 ){
				key = 0 - key;
			}

			long start = System.currentTimeMillis();
			String a = buf.getByMap(key + "");
			long end = System.currentTimeMillis();
			System.out.print("map cost: " + (end - start) + "ms, result: " + a + ",  " );
			avgMap += end - start;

			long start2 = System.currentTimeMillis();
			String b = buf.getByList(key + "");
			long end2 = System.currentTimeMillis();
			System.out.println("list cost: " + (end2 - start2) + "ms, result: " + b + "ms");
			avgList += end2 - start2;
		}

		avgMap /= testTime;
		avgList /= testTime;
		System.out.println("map average cost: " + avgMap + "ms, list average cost: " + avgList);

	}

}
