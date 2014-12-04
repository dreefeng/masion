package com.masion.pattern.callback;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Server implements MessageAdmin {

	private static Map callbackHandler = new HashMap<String, Object>();
	private static int callbackId = 0;

	public class DealMsgThread extends Thread {

		private MessageHandle caller;

		public DealMsgThread() {
		}

		public DealMsgThread(String name) {
			super(name);
		}

		public DealMsgThread(String name, MessageHandle c) {
			super(name);
			this.caller = c;
			System.out.println("Thread no=" + callbackId++);
			//System.out.println("Thread name=" + name);
			//System.out.println("Thread caller=" + caller);
		}

		public void run() {

			System.out.println("Start deal with msg:");
			System.out.println("to:" + this.getName());
			System.out.println("context:");

			try {
				int sleep = 50;
				int i = 0;
				while (i++ < sleep) {
					System.out.print(".");
					Thread.sleep(100);
				}
				System.out.println("");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Done and invoke the client execute method");

			//System.out.println("caller=" + caller);
			caller.execute(new String("Result Data of " + caller));
			//caller.execute();

			try {
				caller.getClass().getMethod("callback").invoke(caller, null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public String sendMsg(String to, String msg, MessageHandle result, Map opt) {

		System.out.println("Call in sendMsg ");
		callbackHandler.put(to, result);

		DealMsgThread dmt = new DealMsgThread(to, result);
		dmt.start();

		System.out.println("Return, waiting call back...");

		return null;
	}

}
