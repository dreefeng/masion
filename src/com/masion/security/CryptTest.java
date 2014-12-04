package com.masion.security;

import java.security.NoSuchAlgorithmException;

public class CryptTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String plainText = "root123";

		System.out.println(plainText + " =Hash=> " + plainText.hashCode());

		try {
			String cryptStr = MD5Crypt.crypt(plainText);
			System.out.println(plainText + " =MD5=> " + cryptStr);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			String cryptStr = DESCrypt.crypt(plainText, "sugon123");
			System.out.println(plainText + " =DES=> " + cryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String cryptStr = Base64Sun.encode(plainText.getBytes());
			System.out.println(plainText + " =Base64=> " + cryptStr);
			byte[] decText = Base64Sun.decode(cryptStr);
			System.out.println(cryptStr + " =Base64=> " + new String(decText));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String cryptStr = Base64.encode(plainText.getBytes());
			System.out.println(plainText + " =MyBase64=> " + cryptStr);
			byte[] decText = Base64.decode(cryptStr);
			System.out.println(cryptStr + " =MyBase64=> " + new String(decText));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
