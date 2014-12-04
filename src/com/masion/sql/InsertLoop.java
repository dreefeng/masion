package com.masion.sql;


//import java.text.SimpleDateFormat;
import java.util.*; //import java.util.Date;
import java.sql.*;

public class InsertLoop {
	public static int mcount = 0;
	public int sleepsec = 1000;
	public int threadnum = 10;
	public String debug = "";

	static {
		// Test test = new Test();
		// test.debug = "debug";
		// test.MySqlTest();
	}

	private static String getCallerClassName() {
		return new Exception().getStackTrace()[2].getClassName();
	}

	class TestThread extends Thread {
		private Connector conn;
		private int count = 0;

		TestThread(Connector con) {
			conn = con;
			if (debug.equals("debug") || debug.equals("info"))
				System.out.println(this.getName() + "start");
		}

		public void run() {
			while (true) {
				try {
					if (debug.equals("debug") || debug.equals("info"))
						System.out.println(this.getName() + ":NO." + count++);
					Thread.sleep(sleepsec);
					boolean tag = conn.connect();
					if (tag == true) {
						String insStr = "insert into JobLog values(null, 'name" + mcount++
								+ "', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'node','200','test') ";
						Statement stmt = conn.getStatement();
						if (debug.equals("debug"))
							System.out.println(insStr);
						int res = stmt.executeUpdate(insStr);
						if (debug.equals("debug"))
							System.out.println("insert result:" + res);
						stmt.close();
						conn.closeConnect();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public void func() {
		System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
	}

	public int getCpuNumWind() {
		return Runtime.getRuntime().availableProcessors();
	}

	public void MySqlTest() {
		try {
			for (int i = 0; i < threadnum; i++) {
				Connector con = new Connector();
				TestThread testth = new TestThread(con);
				testth.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getWeekday() {
		Calendar dc = Calendar.getInstance();
		dc.add(Calendar.DAY_OF_MONTH, -1);
		int day = dc.get(Calendar.DAY_OF_WEEK);
		return day;
	}

	public static void main(String[] args) {
		InsertLoop test = new InsertLoop();
		try {
			if (args.length > 0) {
				test.threadnum = Integer.parseInt(args[0]);
			}
			if (args.length > 1) {
				test.sleepsec = Integer.parseInt(args[1]);
			}
			if (args.length > 2) {
				test.debug = args[2];
			}
			if (args.length > 3) {
				// test.num = Integer.parseInt(args[3]);
			}
		} catch (Exception e) {
			System.out.println("Test threadnum sleepsec debug|info");
		}
		// test.MySqlTest();
	}

	public static int getMcount() {
		return mcount;
	}

	public static void setMcount(int mcount) {
		InsertLoop.mcount = mcount;
	}

	public int getSleepsec() {
		return sleepsec;
	}

	public void setSleepsec(int sleepsec) {
		this.sleepsec = sleepsec;
	}

	public int getThreadnum() {
		return threadnum;
	}

	public void setThreadnum(int threadnum) {
		this.threadnum = threadnum;
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

}
