package com.masion.io;

import java.io.PrintStream;

public class Logger {

	private static PrintStream out; // debug output stream
	private static PrintStream err;
	private static boolean debug = false;

	public synchronized static void setDebugOut(PrintStream out) {
		Logger.out = out;
	}

	public synchronized static void setDebugError(PrintStream out) {
		Logger.err = out;
	}

	public synchronized static void setDebug(boolean debug) {
		Logger.debug = debug;
		if (debug)
			Logger.pr("DEBUG: setDebug: " + debug);
	}

	public synchronized static PrintStream getDebugOut() {
		if (out == null)
			return System.out;
		else
			return out;
	}

	public synchronized static PrintStream getDebugError() {
		if (err == null)
			return System.err;
		else
			return err;
	}

	private static void pr(String str) {
		getDebugOut().println(str);
	}

	public static void debug(String str) {
		if(debug){
			pr(str);
		}
	}

	public static void info(String str) {
		pr(str);
	}

	public static void error(String str) {
		getDebugError().println(str);
	}

}
