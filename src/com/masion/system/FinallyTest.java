package com.masion.system;

public class FinallyTest {

	public static void fun() throws Exception {
		System.out.println("this is fun.");
		throw new Exception();
	}

	public static void main(String[] args) {

		FinallyTest.funTest();

	}

	public static void funTest() {
		try {
			FinallyTest.fun();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			System.out.println("this is finally.");
		}
		return;
	}

}
