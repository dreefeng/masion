package com.masion.string;

public class StringConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public void IntegerToString(){
		Integer a = 12435;

		String b = "";

		try {
			b = a.toString();
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			b = "" + a;
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
