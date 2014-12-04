package com.masion.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端类：发送控制命令
 *
 * @author zhangjinfeng
 *
 */
public class TcpSender {
	protected static String hostIP = "127.0.0.1";
	protected static int hostPort = 8619;
	protected BufferedReader socketReader;
	protected PrintWriter socketWriter;
	protected Socket client;

	public TcpSender() {
	}

	public TcpSender(String IP, int port) {
		this.setHostIP(IP);
		this.setHostPort(port);
	}

	public boolean setUpConnection() {
		boolean tag = false;
		try {
			client = new Socket(hostIP, hostPort);
			client.setSoTimeout(10000);
			System.out.println("connecting: " + hostIP + ":" + hostPort);
			socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			socketWriter = new PrintWriter(client.getOutputStream());
			tag = true;
		} catch (UnknownHostException e) {
			System.err.println("unknown host: " + hostIP + ":" + hostPort);
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();

		}
		return tag;
	}

	public String sendMsg(String msg) {
		String received = "";
		if (this.setUpConnection()) {
		try {
			socketWriter.println(msg);
			System.out.println("send: " + msg);
			socketWriter.flush();
			// char [] cbuf = new char[1024];
			// System.out.println("received: " + cbuf);
			for (int i = 0; i < 255; i++) {
				received += "" + socketReader.read();
			}
			System.out.println("received: " + received);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		this.tearDownConnection();
		}
		return received;
	}

	public void tearDownConnection() {
		try {
			socketWriter.close();
			socketReader.close();
			client.close();
			socketWriter = null;
			socketReader = null;
			client = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		TcpSender.hostIP = hostIP;
	}

	public int getHostPort() {
		return hostPort;
	}

	public void setHostPort(int hostPort) {
		TcpSender.hostPort = hostPort;
	}

	public static void main(String[] args) {
		TcpSender rsc = new TcpSender("192.168.1.253", 10001);
		boolean tag = true;

		int count = 0;
		//rsc.setUpConnection();
		while (tag) {
			try {
				Thread.sleep(2000);
				System.out.print(++count + " ");
				rsc.sendMsg("12345678");
			} catch (Exception e) {
				// System.err.println(e);
				e.printStackTrace();
			}
		}
		//rsc.tearDownConnection();
	}
}
