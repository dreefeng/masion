package com.masion.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Ping {

	public int timeOut = 3000; // 超时应该在3钞以上

	class PingThread extends Thread {
		private String host = "";

		PingThread(String ip, int timeout) {
			host = ip;
			timeOut = timeout;
		}

		public void run() {
			ping(host, timeOut);
		}
	}

	public void test() {
		try {
			String host = "";
			for (int i = 31; i < 32; i++) {
				for (int j = 201; j < 255; j++) {
					host = "10.0." + i + "." + j;
					PingThread th = new PingThread(host, 3000);
					th.start();
				//	ping(host,3000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ping(String host, int timeOut){
		try {
			//System.out.println("host:" + InetAddress.getByName(host).getHostName());
			boolean status = InetAddress.getByName(host).isReachable(timeOut);
			if(status){
//				System.out.println(new Date() + " " + host + " :" + status);
				System.out.println(host);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect(String host, int port){
        Socket sckt = new Socket();
        try {
            InetAddress hostAddress = InetAddress.getByName(host);
            //Socket sckt = new Socket(hostAddress, port);
            sckt.connect( new InetSocketAddress( hostAddress, port ), 1000);
            System.out.println(sckt);
            sckt.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String host = "10.0.50.96";
		if (args.length > 0) {
			host = args[0];
		}

		int port = 623;
		if (args.length > 1) {
			port = Integer.parseInt(args[1]);
		}
		connect(host,port);


	}
}
