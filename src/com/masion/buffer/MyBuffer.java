package com.masion.buffer;

import java.util.concurrent.ConcurrentHashMap;

public class MyBuffer<O> {

	private ConcurrentHashMap<String, O> dataMap = new ConcurrentHashMap<String, O>();

	private ConcurrentHashMap<String, Integer> keyMap = new ConcurrentHashMap<String, Integer>();

	private ConcurrentHashMap<String, Long> retireMap = new ConcurrentHashMap<String, Long>();

//	private ConcurrentHashMap<Integer, ArrayList<String>> timeMap = new ConcurrentHashMap<Integer, ArrayList<String>>();

	private MyBuffer instance = null;

	private int curLen = 0;
	private int maxLen = 1000;
	private int increaseNum = maxLen / 10;
	private int deleteNum = maxLen / 50;

	private int overTime = 0;
	private int overMaxTime = 10;

	private long retiredTime = 20000;

	private MyBuffer() {
	}

	public MyBuffer getInstance() {
		if (instance == null) {
			instance = new MyBuffer();
		}
		return instance;
	}

	public O get(String key) {
		O value = dataMap.get(key);
		Long time = retireMap.get(key);
		if (value == null || time != null && System.currentTimeMillis() - time > retiredTime) {
			// XXX return null ?   remove outside below?
			value = queryData(key);
			if (value != null) {
				this.put(key, value);
			}
		}
		return value;
	}

	private O queryData(String key) {
		O result = null;
		return result;
	}

	/**
	 * 基于LRU(Least Recently Used)算法自动删除
	 */
	public void delete() {
		for (int i = 0; i < deleteNum; i++) {
			keyMap.elements();
		}
		//TODO
	}

	public void init() {
	    new Thread(new RetireThread()).start();
	}

	public void put(String key, O value) {
		if (curLen++ > maxLen) {
			if (overTime++ > overMaxTime) {
				overTime = 0;
				maxLen += increaseNum;
			}
			delete();
		}
		dataMap.put(key, value);

		Integer times = keyMap.get(key) + 1;
		keyMap.put(key, times);//How to count retired
		retireMap.put(key, System.currentTimeMillis());
//		timeMap.get(timeMap).add(key);
//		timeMap.put(times, timeMap.get(timeMap));
	}

	private class RetireThread implements Runnable {

		@Override
		public void run() {

		    while(true){

		        if(dataMap.size() > maxLen){
		            delete();
		        }

		        try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

		    }

		}

	}

}
