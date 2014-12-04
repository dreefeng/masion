package com.masion.system;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用以捕捉本地命令输出的线程
 *
 * @author zhang
 *
 */
class StreamGobbler extends Thread {

	public static final String STREAM_TYPE_ERROR = "ERROR";
	public static final String STREAM_TYPE_OUT = "OUT";
	public Integer threadNum = 0;
	private Process proc;
	private String type;
	private String stdOut;
	private String stdError;

	public StreamGobbler(Process proc, String type) {
		super(type + "StreamGobbler");
		threadNum++;
		this.proc = proc;
		this.type = type;
	}

	public void run() {
		try {
			if (type.equalsIgnoreCase(STREAM_TYPE_OUT)) {
				this.stdOut = loadStream(proc);
			} else if (type.equalsIgnoreCase(STREAM_TYPE_ERROR)) {
				this.stdError = loadErrorStream(proc);
			}
		} catch (IOException ioe) {
			this.stdError = ioe.toString();
		}
		threadNum--;
	}

	public Boolean isQuit() {
		return threadNum == 0;
	}

	/**
	 * 静态工具方法，读取命令执行过程中的标准输出流
	 *
	 * @param proc
	 *        命令进程对象
	 * @return 命令的标准输出字符串
	 * @throws IOException
	 */
	private String loadStream(Process proc) throws IOException {
		int ptr = 0;
		InputStream in = new BufferedInputStream(proc.getInputStream());
        InputStreamReader inr = new InputStreamReader(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = inr.read()) != -1) {
			buffer.append((char) ptr);
		}
        inr.close();
        inr = null;
        in.close();
        in = null;
		return buffer.toString();
	}

	/**
	 * 获取进程的错误输出流
	 *
	 * @param proc
	 *        命令进程
	 * @return 命令的错误输出字符串
	 * @throws IOException
	 */
	private String loadErrorStream(Process proc) throws IOException {
		int ptr = 0;
		InputStream in = new BufferedInputStream(proc.getErrorStream());
        InputStreamReader inr = new InputStreamReader(in);
		StringBuffer buffer = new StringBuffer();
		while ((ptr = inr.read()) != -1) {
			buffer.append((char) ptr);
		}
		inr.close();
		inr = null;
		in.close();
		in = null;
		return buffer.toString();
	}

	public String getStdOut() {
		return stdOut;
	}

	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}

	public String getStdError() {
		return stdError;
	}

	public void setStdError(String stdError) {
		this.stdError = stdError;
	}
}
