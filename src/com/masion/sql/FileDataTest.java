package com.masion.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileDataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String url = "jdbc:mysql://10.0.31.222:3307/cloudstor";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long start = System.currentTimeMillis();

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, "root", "root123");

			if (conn != null) {

				System.out.println(sdf.format(start) + " Connect successful");

				Statement stmt = conn.createStatement();

				int DATA_ID, VERSION = 1, SIZE = 0, OWNER_TYPE = 1, OWNER_ID = 10000001, CREATE_BY = 10000001;
				int MODIFY_BY = 10000001, PARENT_ID, ENCYPT_TAG = 0, DELETE_TAG = 0, STATUS = 0, TYPE = 1, CLASSIFY = 0;
				String ATTRIBUTE = "", NAME, PATH, SUMMRAY, HASH_TAG, CREATE_TIME, MODIFY_TIME;
				String STORE_ROOT = "/parastor", DATA_PATH = "/data", META_PATH = "/data";

				long s = System.currentTimeMillis();

				int ID = 170000, max = 1000000;
				for (; ID < max; ID++) {
					if (ID % 1000 == 0) {
						long e = System.currentTimeMillis();
						System.out.println("ID:" + ID + " " + (e - s) + "ms");
						s = e;
					}

					PARENT_ID = ID - 2;
					NAME = "NAME" + ID;
					Date date = new Date();
					MODIFY_TIME = CREATE_TIME = SUMMRAY = sdf.format(date);
					PATH = "/myfolder/" + NAME;

					UUID uuid = UUID.randomUUID();
					HASH_TAG = uuid.toString().replace("-", "");
					DATA_ID = ID + max;

					String sql = "insert cs_file values ('" + ID + "','" + TYPE + "','" + NAME + "','" + PATH + "','"
							+ ATTRIBUTE + "','" + CLASSIFY + "','" + SUMMRAY + "','" + HASH_TAG + "','" + DATA_ID
							+ "','" + VERSION + "','" + SIZE + "','" + CREATE_TIME + "','" + MODIFY_TIME + "','"
							+ CREATE_BY + "','" + MODIFY_BY + "','" + OWNER_TYPE + "','" + OWNER_ID + "','" + PARENT_ID
							+ "','" + STORE_ROOT + "','" + DATA_PATH + "','" + META_PATH + "','" + ENCYPT_TAG + "','"
							+ DELETE_TAG + "','" + STATUS + "')";

					stmt.executeUpdate(sql);

				}
				stmt.close();

			} else {
				System.out.println("Connect fail");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println(" Close connection successful");
		System.out.println("Cost time: " + (end - start));

	}

}
