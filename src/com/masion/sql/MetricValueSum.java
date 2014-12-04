package com.masion.sql;

import java.sql.*;

public class MetricValueSum {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.31.182:3309/gv_local", "root", "root123");
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt
					.executeQuery("SELECT a.ID,a.VALUE,a.PARAM_TEMPL_ID,"
							+ "a.RESOURCE_ID from gv_rm_param_data a where PARAM_TEMPL_ID ='10087'and resource_id='100111740825389';");

			while (resultSet.next()) {

				String result = resultSet.getString("a.VALUE");
				System.out.println("Value = " + result);
				String[] array = result.split(",");
				int sum = 0;
				for (String ar : array) {
					int v = Integer.valueOf(ar);
					System.out.println("   " + v);
					sum += v;
				}
				System.out.println("Sum = " + sum);
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
