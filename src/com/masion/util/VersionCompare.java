package com.masion.util;

public class VersionCompare {

    public static int compareVersion(String oldVersion, String newVersion) {
        int result = 0;
        String[] newVersionArray = newVersion.split("\\.");
        String[] oldVersionArray = oldVersion.split("\\.");
        for (int verIndex = 0; verIndex < newVersionArray.length && verIndex < oldVersionArray.length; verIndex++) {
            int verOldInt = Integer.parseInt(newVersionArray[verIndex]);
            int verNewInt = Integer.parseInt(oldVersionArray[verIndex]);
            System.out.println(verIndex + " : " + verOldInt + " <> " + verNewInt);
            if (verNewInt > verOldInt) {
                //context.setVariable("UPGRADE", Boolean.TRUE);
                result = 1;
                break;
            } else if (verNewInt < verOldInt) {
                //context.setVariable("DOWNGRADE", Boolean.TRUE);
                result = -1;
                break;
            }
        }
        return result;

    }

    public static void test() {
        String o = "3.0.0";
        String n = "3.1.0.231";
        int result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "2.6.5";
        n = "3.1.0.231";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "3.1.0";
        n = "3.1.0.231";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "3.1.0";
        n = "3.1";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "3.1.0.231";
        n = "3.1.0.231";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "4.0.0";
        n = "3.1.0.231";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

        o = "4.0.0.0.1";
        n = "3.1.0.231";
        result = compareVersion(o, n);
        System.out.println(o + " vs " + n + ":" + result);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        test();

    }

}
