package com.masion.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

	private ServerSocket ssocket;
	private static int port = 10001;

	public TcpServer() {
	}

	public void startLister() {

		boolean finished = false;
		try {
			ssocket = new ServerSocket(port);
			System.out.println("Listen at port:" + port);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (!finished) {
			try {
				Socket client = ssocket.accept();
				InputStream in = client.getInputStream();
				OutputStream out = client.getOutputStream();

				BufferedReader bin = new BufferedReader(new InputStreamReader(in));

				String res = bin.readLine();
				System.out.println("Received: " + res);

				PrintWriter pout = new PrintWriter(out, true);
				pout.println("success");
				//pout.println("Web server ready");

				client.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TcpServer pws = new TcpServer();
		pws.startLister();
	}

}
