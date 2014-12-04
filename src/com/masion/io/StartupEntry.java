package com.masion.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

public class StartupEntry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			File file = new File("properties.txt");

			Properties sysps = System.getProperties();

			FileWriter fw = new FileWriter(file);
			BufferedWriter br = new BufferedWriter(fw);

			String sysStr = "" + sysps;
			sysStr = sysStr.substring(1, sysStr.length() - 1);
/*
			sysStr.replaceAll(", ", "\r\n");
			br.write(sysStr);
*/
			String[] proplist = sysStr.split(",");
			for (int i = 0; i < proplist.length; i++) {
				br.write(proplist[i].trim() + "\n");
			}

			br.close();

		} catch (Exception e) {

		}

	}

}
