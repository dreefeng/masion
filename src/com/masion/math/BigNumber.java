package com.masion.math;

import java.math.BigInteger;

public class BigNumber {

	public static void add() {
		Integer i = new Integer(Integer.MAX_VALUE);

		BigInteger bi = new BigInteger("2147483647");

		System.out.println(i + 1);
		System.out.println(bi.add(BigInteger.ONE));
	}

	public static void max(){
	    Integer i = 0;
		do {
			i=i+1024;
			if(i % 1024000 == 0)
			System.out.println(i);
		} while (true);

	}

    public static void main(String[] args) {
        float fla = Integer.MAX_VALUE;
        float fl = Integer.MIN_VALUE;
        System.out.println(fl);
    }

}
