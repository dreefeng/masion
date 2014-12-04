package com.masion.math;

public class IntegerTest {

    public static void main1(String[] args) {
        int coolingLevel = 90;
        String level = "0x" + Integer.toHexString(coolingLevel);

        System.out.println(level);

    }

    public static void main(String[] args) {

        int level = Integer.parseInt("01f3", 16);
        System.out.println(level);

        int id = Integer.parseInt("0d", 16);
        System.out.println(id);
    }

}
