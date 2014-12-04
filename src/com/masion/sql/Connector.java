package com.masion.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class Connector {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "";
	private static String serverIP = "127.0.0.1";
	private static String serverPort = "3316";
	private static String username = "root";
	private static String password = "dawning";
	private static String database = "powerconf_cluster";
	private Connection conn = null;
	private ConnectPool connp = null;
	private static int poolsize = 20;
	private static int connNum = 0;

	public Connector() {
		url = "jdbc:mysql://" + serverIP + ":" + serverPort + "/" + database
				+ "?relaxAutoCommit=true&useUnicode=true&characterEncoding=utf-8";
		ConnectPool.setDbUrl(url);
		ConnectPool.setDbDriver(driver);
		ConnectPool.setUserName(username);
		ConnectPool.setUserPassword(password);
		try {
			connp = ConnectPool.getConnManagerInstance("xxx");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		connNum++;
		if (null == conn) {
			try {
				conn = connp.getConnection();
				conn = DriverManager.getConnection(url, username, password);
				// logger.debug("connect : " + serverIP + ":" + serverPort + "/" + database + " num:" + connNum);
			} catch (Exception e) {
				e.printStackTrace();
				// logger.error(e);
				return false;
			}
		}
		return true;
	}

	public void closeConnect() {
		connNum--;
		if (connNum == 0) {
			try {
				conn.close();
				conn = null;
				ConnectPool.releaseConnection(conn, true);
				// logger.debug("close connection num:" + connNum);
			} catch (Exception e) {
				e.printStackTrace();
				// logger.error(e);
			}
		}
	}

	public Statement getStatement() {
		try {
			return conn.createStatement();
		} catch (Exception e) {
			// logger.error(e);
			e.printStackTrace();
			return null;
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public ConnectPool getConnp() {
		return connp;
	}

	public void setConnp(ConnectPool connp) {
		this.connp = connp;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		Connector.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		Connector.url = url;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		Connector.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		Connector.serverPort = serverPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		Connector.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pwd) {
		Connector.password = pwd;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		Connector.database = database;
	}

	public int getPoolsize() {
		return poolsize;
	}

	public void setPoolsize(int poolsize) {
		Connector.poolsize = poolsize;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connector conn = new Connector();
		conn.setUrl("jdbc:mysql://127.0.0.1:3316/powerconf_cluster");
		boolean tag = conn.connect();
		if (tag) {
			System.out.println("Connect successful");
		} else {
			System.out.println("Connect fail");
		}
		conn.closeConnect();
	}
}
