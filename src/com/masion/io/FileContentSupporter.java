package com.masion.io;

import java.io.*;

public class FileContentSupporter {

	public static String readFile(String file, String charset) {
		if (charset == null) {
			charset = "gb2312";
		}
		String content = "";
		File dFile = new File(file);
		if (dFile.exists()) {
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(dFile), charset);
				BufferedReader bd = new BufferedReader(read);
				String line = bd.readLine();
				while (line != null) {
					content += line;
					line = bd.readLine();
					System.out.println(line);
				}
				read.close();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

		return content;
	}

	public static void writeFile(String file, String content, String charset) {
		if (charset == null) {
			charset = "UTF-8";
		}
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), charset);
			BufferedWriter writer = new BufferedWriter(write);
			//PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePathAndName)));
			//PrintWriter writer = new PrintWriter(new FileWriter(filePathAndName));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
