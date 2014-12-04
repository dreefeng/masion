package com.masion.net;

import java.net.UnknownHostException;

public class HostAddress {

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

    public static void main(String[] argv) {
        HostAddress.getHostAddressList();
        HostAddress.getHostName();
    }
}
