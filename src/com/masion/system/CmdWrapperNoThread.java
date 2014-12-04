package com.masion.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行外部程序接口
 * @author 
 * 
 */
public class CmdWrapperNoThread {
	/**
	 * 执行外部程序接口
	 * @param execStr
	 * @return String
	 */
	void CmdWappwer(){
		
	}
	
	public static String exec(String execStr) {
		Runtime runtime = Runtime.getRuntime();
		String outInfo = "";

		try {
			String[] args = new String[] { "sh", "-c", execStr };//linux             
			//String[] args = new String[]{"cmd", "-c", execStr}; //windows

			Process proc = runtime.exec(args);
			//InputStream in = proc.getErrorStream();

			InputStream in = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = br.readLine()) != null) {
				outInfo = outInfo + line + "\n";
				System.out.println(outInfo);
			}

			InputStream error = proc.getErrorStream();
//			System.out.println(outInfo);
			try {
				if (proc.waitFor() != 0) {
					System.err.println("exit value = " + proc.exitValue());
				}
			} catch (InterruptedException e) {
				System.err.print(e);
				e.printStackTrace();
				return "exception";
			}
			in.close();
			in = null;
			br.close();
			br = null;
			error.close();
			error = null;
			proc.getOutputStream().close();
			proc = null;
		} catch (IOException e) {
			//System.out.println("exec error: " + e.getMessage());
			e.printStackTrace();
			return "exception";
		} finally {
			//return outInfo;
		}
		return outInfo;
	}
}
