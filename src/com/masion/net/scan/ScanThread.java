package com.masion.net.scan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ScanThread extends Thread {

	// 最小的端口号
	public static int minPort = 22;
	// 最大的端口号
	public static int maxPort = 23;

	public static int timeOut = 2000;

	// 线程数
	public static int maxThreadNum = 10;

	// 线程编号
	private int threadId;

	public static String ipStart = "";
    public static String ipEnd = "";

    private static int startIp = 0;
    private static int endIp = 0;

	// 起始ip地址前3位
    private static int ipStart1 = 10;
	// 起始ip地址4~6位
    private static int ipStart2 = 0;
	// 起始ip地址7~9位
    private static int ipStart3 = 110;
    // 起始ip地址的最后4位
    private static int ipStart4 = 1;

    // 结束ip地址前3位
    private static int ipEnd1 = 10;
    // 结束ip地址4~6位
    private static int ipEnd2 = 0;
    // 结束ip地址7~9位
    private static int ipEnd3 = 110;
	// 结束ip地址的最后4位
    private static int ipEnd4 = 6;

    private static int ipNum = 0;

	// 完整的ip地址
    public static String threadPrefix = "ScanThread-";

	public static Boolean stop = false;

	public ScanThread(int threadnum) {
		super(threadPrefix + threadnum);
		this.threadId = threadnum;
	}

	public static void setStop(Boolean isStop) {
		stop = isStop;
	}

	public void run() {

	    parseIp();

	    InetAddress hostAddress;

		// 端口号
		int port = 0;
		// 根据ip地址进行扫描
		// ip地址循环扫描
		for (int index = 0; index < ipNum; index+= maxThreadNum) {
			if (stop) {
				break;
			}

			// 组成完整的ip地址
			String ipAll = increaseIp(index, threadId);
			if("".equals(ipAll)){
			    continue;
			}

			try {
				// 在给定主机名的情况下确定主机的 IP 地址
				hostAddress = InetAddress.getByName(ipAll);
			} catch (UnknownHostException e) {
				continue;
			}

			// 不同的端口循环扫描
			for (port = minPort; port <= maxPort; port ++) {
				if (stop) {
					break;
				}
				try {
					System.out.println(this.getName() + "\t" + hostAddress + ":" + port + " start");
					Socket sckt = new Socket();
					sckt.connect( new InetSocketAddress( hostAddress, port ), timeOut);
					sckt.close();
					// 判断端口的类别
					String portType = "0";
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
                    case 161:
                        portType = "(SNMP)";
                        break;
                    case 623:
                        portType = "(IPMI)";
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
						PortScanner.appendResult(" " + portType + "\n");
					}
					System.out.println(this.getName() + "\t" + hostAddress + ":" + port + " " + portType);
				} catch (IOException e) {
					System.out.println(this.getName() + "\t" + hostAddress + ":" + port + " " + e.getMessage());
				}
			}
		}
        System.out.println(this.getName() + " stopped");
	}

    private static String increaseIp(int ipIterator, int threadId) {

        int ip = startIp + ipIterator + threadId;

        String ipStr = "";
        if(ip<endIp){
            int ip1 = ip/(255*255*255);
            int ip1S = ip%(255*255*255);
            ipStr += ip1 + ".";
            int ip2 = ip1S/(255*255);
            int ip2S = ip1S%(255*255);
            ipStr += ip2 + ".";
            int ip3 = ip2S/255;
            int ip3S = ip2S%255;
            ipStr += ip3 + ".";
            ipStr += ip3S;
        }
        return ipStr;
    }

    private static int parseIp() {
        try{
            String[] ips = ipStart.split("\\.");
            ipStart1 = Integer.parseInt("" + ips[0]);
            ipStart2 = Integer.parseInt("" + ips[1]);
            ipStart3 = Integer.parseInt("" + ips[2]);
            ipStart4 = Integer.parseInt("" + ips[3]);

            String[] ipe = ipEnd.split("\\.");
            ipEnd1 = Integer.parseInt("" + ipe[0]);
            ipEnd2 = Integer.parseInt("" + ipe[1]);
            ipEnd3 = Integer.parseInt("" + ipe[2]);
            ipEnd4 = Integer.parseInt("" + ipe[3]);

            endIp = ipEnd1*255*255*255 + ipEnd2*255*255 + ipEnd3*255 + ipEnd4;
            startIp = ipStart1*255*255*255 + ipStart2*255*255 + ipStart3*255 + ipStart4;
            ipNum = endIp < startIp? 0: (endIp - startIp);

        }catch(Exception e){
            e.printStackTrace();
        }
        return ipNum;
    }

    public static String getThreadPrefix() {
        return threadPrefix;
    }

    public static void setThreadPrefix(String threadPrefix) {
        ScanThread.threadPrefix = threadPrefix;
    }

}