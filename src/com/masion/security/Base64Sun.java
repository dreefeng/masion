package com.masion.security;


public abstract class Base64Sun {

	public static String encode(byte[] data) throws Exception {
		return new sun.misc.BASE64Encoder().encode(data);
	}

	public static byte[] decode(String data) throws Exception {
		return new sun.misc.BASE64Decoder().decodeBuffer(data);
	}


}
