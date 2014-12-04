package com.masion.string;

public class EqualTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("foo" == "foo");
		System.out.println("foo" == new String("foo"));
		String a = "hello";
		System.out.println(a.intern());
	}

}
