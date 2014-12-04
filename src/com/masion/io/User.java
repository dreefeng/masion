package com.masion.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class User {
	public static String ask() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			return stdin.readLine();
		} catch (IOException ignore) {
		}
		return null;
	}
}
