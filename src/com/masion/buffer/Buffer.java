package com.masion.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Buffer {

	private static List<String> list = new ArrayList<String>();
	private static Map<String, String> map = new ConcurrentHashMap<String, String>();

	public Buffer() {
	}

	public void print() {
		synchronized (list) {
			System.out.println("print list bgn: " + list);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
			System.out.println("print list end: " + list);
		}
	}

	public void printMap() {
		System.out.println("print map bgn: " + map);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		System.out.println("print map end: " + map);
	}

	public synchronized void modify(String s) {
		//		System.out.println("modify bgn: " + s);
		synchronized (list) {
			if (list.size() > 25) {
				list.clear();
				map.clear();
			}
			list.add(s);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
		map.put(s, s);
		//		System.out.println("modify end: " + s);
	}

	public synchronized void add(String s) {
		synchronized (list) {
			list.add(s);
		}
		map.put(s, s);
	}

	public String getByMap(String key) {
		return map.get(key);
	}

	public String getByList(String key) {
		int index = 0;
		String v = Buffer.getList().get(index);
		while (index < Buffer.getList().size()) {
			if (v.equals(key + "")) {
				break;
			}
			v = Buffer.getList().get(index++);
		}
		return v;
	}

	public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		Buffer.map = map;
	}

	public static List<String> getList() {
		return list;
	}

	public static void setList(List<String> list) {
		Buffer.list = list;
	}
}
