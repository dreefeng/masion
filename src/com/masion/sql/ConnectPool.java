package com.masion.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import java.sql.ResultSet;

/**
 *
 * 数据库连接池
 *
 * @author zhangjinfeng
 */
public class ConnectPool {

	/**
	 * 定时判断连接是否有效的线程， 如果连接不再有效则清除之， 如果空闲连接超出时间限制则清除之
	 *
	 */
	private class ActiveConnThread extends Thread {

		private ConnectPool pool;
		private long sleepTime;

		public ActiveConnThread(ConnectPool pool, long sleepTime) {
			this.pool = pool;
			this.sleepTime = sleepTime;
		}

		public void run() {
			try {
				Connection conn = null;
				long startTime = System.currentTimeMillis();
				long runTime = 0;
				while (true) {
					runTime = System.currentTimeMillis() - startTime;
					// System.out.println(runTime + "no use size " + noUseConnectPool.size());
					// System.out.println(runTime + "now use size " + nowUseConnectPool.size());
					if (noUseConnectPool.size() == 0) {
						startTime = System.currentTimeMillis();
					}
					for (int i = 0; i < noUseConnectPool.size(); i++) {
						synchronized (noUseConnectPool) {
							if (noUseConnectPool.size() > 0) {
								conn = (Connection) noUseConnectPool.get(i);
								try {
									conn.createStatement().executeQuery("select CURRENT_TIMESTAMP ");
								} catch (SQLException e) {
									noUseConnectPool.remove(i);
									conn.close();
									conn = null;
								}
								if (runTime > expirationTime) {
									// System.out.println(runTime + "remove no used " + i);
									noUseConnectPool.remove(i);
									conn.close();
									conn = null;
								}
							}
						}
						//System.out.println(runTime + " no use " + i);
					}
					for (int i = 0; i < nowUseConnectPool.size(); i++) {
						synchronized (nowUseConnectPool) {
							if (nowUseConnectPool.size() > 0) {
								conn = (Connection) nowUseConnectPool.get(i);
								try {
									conn.createStatement().executeQuery("select CURRENT_TIMESTAMP ");
								} catch (SQLException e) {
									nowUseConnectPool.remove(i);
									conn.close();
									conn = null;
								}
							}
						}

						// System.out.println(runTime + " now use " + i);
						// Thread.sleep(1000);
					}
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {

			}
		}
	}

	// 连接池的管理器,首先初始化，仅仅有一个对象，管理连接池
	private static HashMap<String, ConnectPool> connectPoolManager = new HashMap<String, ConnectPool>();
	// 没有用过的连接池，用vector实现同步
	private static Vector<Connection> noUseConnectPool = new Vector<Connection>();
	// 正在使用的连接池
	private static Vector<Connection> nowUseConnectPool = new Vector<Connection>();

	private static String dbDriver = "";
	private static String dbUrl = "";
	private static String userName = "";
	private static String userPassword = "";

	// 默认为连接池大小为100个连接
	private static int max_pool_size = 100;

	// 超时时间 milliseconds
	private static long expirationTime = 3600000;

	// singleTon 设计模式
	private ConnectPool() throws ClassNotFoundException {

		ActiveConnThread act = new ActiveConnThread(this, 100000);
		act.start();
	}

	/**
	 * 获得连接池实例
	 *
	 * @param poolName
	 *            连接池名称
	 * @return 连接池实例
	 * @throws ClassNotFoundException
	 */
	public static ConnectPool getConnManagerInstance(String poolName) throws ClassNotFoundException {
		ConnectPool tempPool = (ConnectPool) connectPoolManager.get(poolName);
		if (tempPool == null) {
			tempPool = new ConnectPool();
			connectPoolManager.put(poolName, tempPool);
		}
		return tempPool;
	}

	// 通过连接池获得真正的链接
	public Connection getConnection() throws java.sql.SQLException {
		Connection conn = null;
		synchronized (noUseConnectPool) {
			if (noUseConnectPool.size() > 0) {
				conn = (Connection) noUseConnectPool.firstElement();
				noUseConnectPool.remove(conn);
				return conn;
			}
		}
		// 如果数据库连接池没有链接了，自己创建一个
		if (conn == null) {
			conn = createConnection(dbDriver, dbUrl, userName, userPassword);
		}

		if (conn.isClosed()) {
			nowUseConnectPool.remove(conn);
			conn = createConnection(dbDriver, dbUrl, userName, userPassword);
		}
		conn.setAutoCommit(false);
		nowUseConnectPool.add(conn);
		return conn;
	}

	// 如果连接池没有链接了，就需要产生一个链接
	private static Connection createConnection(String driver, String url, String user, String password)
			throws java.sql.SQLException {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}

	public static void releaseConnection(Connection conn, boolean isCommit) throws java.sql.SQLException {
		if (isCommit) {
			conn.commit();
		} else {
			conn.rollback();
		}
		nowUseConnectPool.remove(conn);
		if (noUseConnectPool.size() + nowUseConnectPool.size() < max_pool_size) {
			synchronized (noUseConnectPool) {
				noUseConnectPool.add(conn);
			}
		} else {
			conn.close();
			conn = null;
		}
	}

	public static void main(String[] args) {
		ConnectPool.setDbDriver("com.mysql.jdbc.Driver");
		ConnectPool.setDbUrl("jdbc:mysql://127.0.0.1:3316/powerconf_cluster");
		ConnectPool.setUserName("root");
		ConnectPool.setUserPassword("dawning");
		ConnectPool.setMax_pool_size(10);
		// 测试模拟10个客户
		for (int i = 0; i < 10; i++) {
			try {
				ConnectPool pool = ConnectPool.getConnManagerInstance("xxxx");
				Connection conn = pool.getConnection();
				ResultSet rs = conn.createStatement().executeQuery("select CURRENT_TIMESTAMP ");
				if (rs.next())
					System.out.println(i + ":" + rs.getString("CURRENT_TIMESTAMP"));
				ConnectPool.releaseConnection(conn, true);
			} catch (SQLException ex1) {
				ex1.printStackTrace();
				// 处理异常
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				// 处理异常
			}
		}
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		ConnectPool.dbUrl = dbUrl;
	}

	public String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		ConnectPool.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public static void setUserPassword(String userPassword) {
		ConnectPool.userPassword = userPassword;
	}

	public int getMax_pool_size() {
		return max_pool_size;
	}

	public static void setMax_pool_size(int max_pool_size) {
		ConnectPool.max_pool_size = max_pool_size;
	}

	public static String getDbDriver() {
		return dbDriver;
	}

	public static void setDbDriver(String dbDriver) {
		ConnectPool.dbDriver = dbDriver;
	}

	public static long getExpirationTime() {
		return expirationTime;
	}

	public static void setExpirationTime(long expirationTime) {
		ConnectPool.expirationTime = expirationTime;
	}
}
