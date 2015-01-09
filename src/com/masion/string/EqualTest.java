package com.masion.string;

public class EqualTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    System.out.println("3.0.0:" + "3.0.0".startsWith("3."));
	    System.out.println("30.0.0:" + "30.0.0".startsWith("3."));
	    System.out.println("30.0.0:" + "30.0.0".startsWith("3."));
	}

	public static void equla(){
		System.out.println("foo" == "foo");
		System.out.println("foo" == new String("foo"));
		String a = "hello";
		System.out.println(a.intern());
	}

}
