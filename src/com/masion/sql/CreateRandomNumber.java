package com.masion.sql;

import java.sql.*;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection; //import java.text.SimpleDateFormat;
import com.mysql.jdbc.Statement;

public class CreateRandomNumber {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public void aa(String as, String tablename) {

		try {
			// Class.forName("com.mysql.jdbc.Driver");

			Connection con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://192.168.1.217:3309/gv_local", "root",
					"root123");
			Statement stmt = (Statement) con.createStatement();

			// String tablename = "gv_local.gv_alarm_policy_info";

			String insertsql = "insert into "
					+ ""
					+ tablename
					+ ""
					+ " values ("
					+ ""
					+ as
					+ ""
					+ ", 'name4', 'shiliang create', '2010-11-12 09:27:24', 'root', '2010-11-12 09:27:24', 'root', '1', 'ledShine', '10001', '1', 'disk partition usage too high', 'CommunicationAlarm', 'Major', '1', '2', '{\"schedulerType\":\"once\",\"startTime\":\"2010-11-12 00:00:00\",\"endTime\":\"2010-11-13 00:00:00\"}', 'admin', '');";
			stmt.executeUpdate(insertsql);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String tablename = JOptionPane.showInputDialog("���������");
		String s1 = JOptionPane.showInputDialog("������������");
		try {
			Connection con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://192.168.1.217:3309/gv_local", "root",
					"root123");
			Statement stmt = (Statement) con.createStatement();

			// TODO Auto-generated method stub

			String sql1 = "SELECT count(*) FROM gv_local.gv_alarm_policy_info";

			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				JOptionPane
						.showMessageDialog(null, "ִ��ǰ��¼��:" + rs.getString(1));
//				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int a1 = Integer.parseInt(s1);
		for (int t = 0; t < a1; t++) {
			long i = (long) (Math.random() * 10000000);
			String ss = null;
			ss = String.valueOf(i);
			// JOptionPane.showMessageDialog(null, "SS="+ss);
			long j = (long) (Math.random() * 100000000);
			String sb = null;
			sb = String.valueOf(j);
			// JOptionPane.showMessageDialog(null, "SB="+sb);
			ss = ss.concat(sb);
			// long a =20101122;
			// int j =0;
			// JOptionPane.showMessageDialog(null, ss);
			// System.out.print(ss + '\n');
			CreateRandomNumber aa = new CreateRandomNumber();
			aa.aa(ss, tablename);

		}
		// System.out.print(ss);
		try {
			Connection con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://192.168.1.217:3309/gv_local", "root",
					"root123");
			Statement stmt = (Statement) con.createStatement();

			// TODO Auto-generated method stub

			String sql1 = "SELECT count(*) FROM gv_local.gv_alarm_policy_info";

			ResultSet rs = stmt.executeQuery(sql1);

			while (rs.next()) {
				JOptionPane
						.showMessageDialog(null, "ִ�к��¼��:" + rs.getString(1));

			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
