/**
 * @Title: Base64Test.java
 * @project Gridview V3.0.0
 * @subproject masion V3.0.0
 *
 * Copyright 2014 sugon, Inc. All rights reserved.
 * SUGON PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.masion.security;

/**
 * @class Base64Test
 * @date 2014-9-5 下午7:09:52
 * @author zhang
 */
public class Base64Encoder {

    public static String encode(String str){
        String result = null;
        try{
            char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
            /** Base64 encode the given data */
            byte[] data = str.getBytes();
            int start = 0;
            int len = data.length;
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);

            int end = len - 3;
            int i = start;
            int n = 0;

            while (i <= end) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8)
                        | (((int) data[i + 2]) & 0x0ff);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append(legalChars[d & 63]);

                i += 3;

                if (n++ >= 14) {
                    n = 0;
                    buf.append(" ");
                }
            }

            if (i == start + len - 2) {
                int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append("=");
            } else if (i == start + len - 1) {
                int d = (((int) data[i]) & 0x0ff) << 16;

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append("==");
            }

            result= buf.toString();
        }catch(Exception e){
        }

        return result;
    }
    public static void main(String[] args) {

        System.out.println(Base64Encoder.encode("2099-12-31"));
        System.out.println(Base64Encoder.encode("50000"));

    }

}
