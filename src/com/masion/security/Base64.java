package com.masion.security;

import java.util.Arrays;

public class Base64 {
	    static String base64_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	    /**
	     * 编码原理:将3个字节转换成4个字节( (3 X 8) = 24 = (4 X 6) )
	     * 先读入3个字节,每读一个字节,左移8位,再右移四次,每次6位,这样就有4个字节了
	     *
	     * @param data
	     * @return 编码后的Base64字符串
	     */
	    public static String encode(byte[] data) {
	        StringBuilder builder = new StringBuilder();
	        int[] temp = new int[4];
	        int len = data.length - data.length % 3;
	        for (int i = 0; i < len; i += 3) {
	            int goal = 0;
	            for (int j = 0; j < 3; j++) {
	                goal <<= 8;
	                goal |= (data[i + j] & 0xff);
	            }
	            for (int k = 0; k < 4; k++) {
	                temp[k] = goal & 0x3f;
	                goal >>= 6;
	            }
	            for (int k = 3; k >= 0; k--) {
	                builder.append(base64_alphabet.charAt(temp[k]));
	            }
	        }
	        int index;
	        switch (data.length % 3) {
	        case 1:
	            index = data[data.length - 1] >> 2;
	            builder.append(base64_alphabet.charAt(index));
	            index = (data[data.length - 1] & 0x03) << 4;
	            builder.append(base64_alphabet.charAt(index));
	            builder.append("==");
	            break;
	        case 2:
	            index = data[data.length - 1 - 1] >> 2;
	            builder.append(base64_alphabet.charAt(index));
	            index = (data[data.length - 1 - 1] & 0x03) << 4
	                    | data[data.length - 1] >> 4;
	            builder.append(base64_alphabet.charAt(index));
	            index = (data[data.length - 1] & 0x0f) << 2;
	            builder.append(base64_alphabet.charAt(index));
	            builder.append('=');
	            break;
	        }
	        return builder.toString();
	    }

	    /**
	     * 解码原理:将4个字节转换成3个字节. 先读入4个6位(用或运算),每次左移6位,再右移3次,每次8位.
	     *
	     * @param data
	     *            需解码的Base64字符串。
	     * @return byte[]－解码出的字节数组
	     */
	    public static byte[] decode(String data) {
	        char[] chArray = data.toCharArray();
	        int len = chArray.length;
	        byte[] result = new byte[len * 3 / 4];
	        for (int i = 0, res_i = 0; i < len; i += 4, res_i += 3) {
	            int goal = 0;
	            int index = 0;
	            for (int k = 0; k < 4; k++) {
	                index = base64_alphabet.indexOf(chArray[i + k]);
	                goal <<= 6;
	                goal |= index;
	            }
	            for (int j = 2; j >= 0; j--) {
	                result[res_i + j] = (byte) goal;
	                goal >>= 8;
	            }
	        }
	        // 等号=的处理
	        if (chArray[len - 1] != '=')
	            return result;
	        else if (chArray[len - 2] == '=')
	            return Arrays.copyOf(result, result.length - 2);
	        else
	            return Arrays.copyOf(result, result.length - 1);

	    }
}
