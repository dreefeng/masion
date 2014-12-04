package com.masion.net.scan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpThread extends Thread {

	public static InetAddress hostAddress;

	// 最小的端口号
	public static int minPort = 3305;
	// 最大的端口号
	public static int maxPort = 3309;

	// 线程数
	public static int maxThreadNum;

	// 线程编号
	private int threadId;

	// ip地址前3位
	public static int ip1 = 10;
	// ip地址4~6位
	public static int ip2 = 0;
	// ip地址7~9位
	public static int ip3 = 110;
	// 起始ip地址的最后4位
	public static int ipstart = 1;
	// 结束ip地址的最后4位
	public static int ipend = 6;
	// 完整的ip地址
	public String ipAll;
	// 端口的类别
	private String portType = "0";

	public static Boolean stop = false;

	public TcpThread(String name, int threadnum) {
		super(name);
		this.threadId = threadnum;
	}

	public TcpThread(String name, int threadnum, int ipPart1, int ipPart2, int ipPart3) {
		super(name);
		ip1 = ipPart1;
		ip2 = ipPart2;
		ip3 = ipPart3;
		this.threadId = threadnum;
	}

	public static void setStop(Boolean isStop) {
		stop = isStop;
	}

	public void run() {

		// ip地址
		int ipIterator = 0;
		// 端口号
		int port = 0;
		// 根据ip地址进行扫描

		// ip地址循环扫描
		for (ipIterator = ipstart; ipIterator <= ipend; ipIterator++) {
			if (stop) {
				break;
			}

			// 组成完整的ip地址
			ipAll = "" + ip1 + "." + ip2 + "." + ip3 + "." + ipIterator;

			try {
				// 在给定主机名的情况下确定主机的 IP 地址
				hostAddress = InetAddress.getByName(ipAll);
			} catch (UnknownHostException e) {
				continue;
			}

			// 不同的端口循环扫描
			for (port = minPort + threadId; port < maxPort; port += maxThreadNum) {
				if (stop) {
					break;
				}
				try {
					System.out.println(this.getName() + "\t:" + hostAddress + ":" + port + ": start");
					Socket sckt = new Socket();
					//Socket sckt = new Socket(hostAddress, port);
					sckt.connect( new InetSocketAddress( hostAddress, port ), 1000);
					sckt.close();
					// 判断端口的类别
					switch (port) {
					case 21:
						portType = "(FTP)";
						break;
					case 22:
						portType = "(SSH)";
						break;
					case 23:
						portType = "(TELNET)";
						break;
					case 25:
						portType = "(SMTP)";
						break;
					case 80:
						portType = "(HTTP)";
						break;
					case 110:
						portType = "(POP)";
						break;
					case 139:
						portType = "(netBIOS)";
						break;
					case 1433:
						portType = "(SQL Server)";
						break;
					case 3389:
						portType = "(Terminal Service)";
						break;
					case 443:
						portType = "(HTTPS)";
						break;
					case 1521:
						portType = "(Oracle)";
						break;
					}

					PortScanner.appendResult(hostAddress + ":" + port);
					// 端口没有特定类别
					if (portType.equals("0")) {
						PortScanner.appendResult("\n");
					} else {
						PortScanner.appendResult(":" + portType + "\n");
					}
					System.out.println(this.getName() + "\t:" + hostAddress + " port:" + port + " : " + portType);
				} catch (IOException e) {
					System.out.println(this.getName() + "\t:" + hostAddress + " port:" + port + " : " + e.getMessage());
				}
			}
		}
	}
}