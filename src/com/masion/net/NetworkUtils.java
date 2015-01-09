package com.masion.net;

import static java.lang.System.out;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.StringTokenizer;

/*
 * 我们在写程序的过程中,有些时候需要知道一些电脑的硬件信息,比如我们写一些需要注册的程序的时候,
 * 就需要得到某个电脑特定的信息,一般来说,网卡的物理地址是不会重复的,
 * 我们正好可以用它来做为我们识别一台电脑的标志.那如何得到网卡的物理地址呢?
 * 我们可以借助于ProcessBuilder这个类,这个类是JDK1.5新加的,以前也可以用Runtime.exce这个类.
 * 在此我们将演示一下如何在Windows和Linux环境下得到网卡的物理地址.
 */

public final class NetworkUtils {

    private final static int MACADDR_LENGTH = 17;
    private final static String WIN_OSNAME = "Windows";
    private final static String WIN_MACADDR_REG_EXP = "^[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}$";
    private final static String WIN_MACADDR_EXEC = "ipconfig /all";

    public final static String getMacAddress() throws IOException {
        String os = System.getProperty("os.name");
        try {
            if (os.startsWith(WIN_OSNAME)) {
                return winMacAddress(winIpConfigCommand());
            }
            // 下面是其它的操作系统的代码,省略了!
            //   LINUX -->    else if (os.startsWith("Linux")) {
            //
            //                Process p = Runtime.getRuntime().exec("ifconfig");
            //   MAC OSX -->  else if(os.startsWith("Mac OS X")) {
            //
            //                Process p = Runtime.getRuntime().exec("ifconfig");
            else {
                throw new IOException("OS not supported : " + os);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    private final static String winMacAddress(String ipConfigOutput) throws ParseException {
        String localHost = null;
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            ex.printStackTrace();
            throw new ParseException(ex.getMessage(), 0);
        }

        StringTokenizer tokenizer = new StringTokenizer(ipConfigOutput, "\n");
        String lastMacAddress = null;

        while (tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();

            // see if line contains IP address
            //      if (line.endsWith(localHost) && lastMacAddress != null) {
            if (line.contains(localHost) && lastMacAddress != null) {
                return lastMacAddress;
            }

            // see if line contains MAC address
            int macAddressPosition = line.indexOf(":");
            if (macAddressPosition <= 0)
                continue;

            String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
            if (winIsMacAddress(macAddressCandidate)) {
                lastMacAddress = macAddressCandidate;
                continue;
            }
        }

        ParseException ex = new ParseException("cannot read MAC address from [" + ipConfigOutput + "]", 0);
        ex.printStackTrace();
        throw ex;
    }

    private final static boolean winIsMacAddress(String macAddressCandidate) {
        if (macAddressCandidate.length() != MACADDR_LENGTH)
            return false;
        if (!macAddressCandidate.matches(WIN_MACADDR_REG_EXP))
            return false;
        return true;
    }

    private final static String winIpConfigCommand() throws IOException {
        Process p = Runtime.getRuntime().exec(WIN_MACADDR_EXEC);
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

        StringBuffer buffer = new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1)
                break;
            buffer.append((char) c);
        }
        String outputText = buffer.toString();
        stdoutStream.close();
        return outputText;
    }

    public static void showIPbyIneterface() throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        //    while(nets.hasMoreElements()){
        //        displayInterfaceInformation((NetworkInterface)nets.nextElement());
        //    }
        for (NetworkInterface netint : Collections.list(nets)) {
            displayInterfaceInformation((NetworkInterface) netint);
        }
    }

    public static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        if (inetAddresses.hasMoreElements()) {
            out.printf("Display name: %s\n", netint.getDisplayName());
            out.printf("Name: %s\n", netint.getName());
            for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                InetAddress add = (InetAddress) inetAddress;
                //        if (!add.isLoopbackAddress()) {
                if (add instanceof Inet4Address) {
                    out.printf("IPv4");
                }
                if (add instanceof Inet6Address) {
                    out.printf("IPv6");
                }
                out.printf("InetAddress: %s\n", add.getHostAddress());
                //        }
            }
            out.printf("\n");
        }
    }

    /**
     * @param args
     */
    public static void getLocalAddress(String[] args) {
        try {

            InetAddress localhost = InetAddress.getLocalHost();

            String hostname = localhost.getHostName();

            System.out.println("hostname: " + hostname);

            InetAddress[] all = InetAddress.getAllByName(hostname);

            for (InetAddress a : all) {
                System.out.println("address: " + a.getHostAddress());
            }

        } catch (UnknownHostException uhe) {

            System.err.println("Localhost not seeable.Something is odd. ");

        }
    }

    public static java.util.List getHostAddressList() {
        java.util.List list = new java.util.ArrayList<java.net.Inet4Address>();
        try {
            java.util.Enumeration nets = java.net.NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                java.net.NetworkInterface netint = (java.net.NetworkInterface) nets.nextElement();
                java.util.Enumeration<java.net.InetAddress> inetAddresses = netint.getInetAddresses();
                for (java.net.InetAddress inetAddress : java.util.Collections.list(inetAddresses)) {
                    if (inetAddress instanceof java.net.Inet4Address) {
                        list.add(inetAddress.getHostAddress());
                    }
                }
            }
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String getHostName() {
        String hostname = "localhost";
        try {
            java.net.InetAddress host = java.net.InetAddress.getLocalHost();
            if (null != host) {
                hostname = host.getHostName();
            }
        } catch (UnknownHostException e) {
            String h = e.getMessage(); // host = "hostname: hostname"
            if (h != null) {
                int colon = h.indexOf(':');
                if (colon > 0) {
                    hostname = h.substring(0, colon);
                }
            }
            e.printStackTrace();
        }
        System.out.println(hostname);
        return hostname;
    }

    public final static void main(String[] args) {
        try {
            System.out.println("MAC ADDRESS");
            System.out.println("  OS          : " + System.getProperty("os.name"));
            System.out.println("  IP/Localhost: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("  MAC Address : " + getMacAddress());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
