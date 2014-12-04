/*
 * $Id: Crypt.java,v 1.5 2005/09/02 19:00:22 k Exp $
 * Copyright (C) 2005 Klaus Reimer <k@ailis.de>
 * Released into the public domain.
 */

package com.masion.security;

import java.security.NoSuchAlgorithmException;

/**
 * Contains static methods for encrypting passwords.
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 1.5 $
 */

public class Crypt {
	/** Characters which can be used for salts */
	static protected final String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	/**
	 * Encrypts the given password with given salt and returns the crypt string
	 * complete with magic and hash.
	 *
	 * @param password
	 *            Cleartext password
	 * @param salt
	 *            Salt to add entropy to the encryption
	 * @throws java.security.NoSuchAlgorithmException
	 *             If java.security does not support MD5
	 * @return The encrypted password
	 */

	public static String crypt(String password, String salt)
			throws java.security.NoSuchAlgorithmException {
		if (salt.startsWith("$1$")) {
			return MD5Crypt.crypt(password, salt);
		}
		return DESCrypt.crypt(password, salt);
	}

	/**
	 * Encrypts the given password with an automatically generated random salt
	 * and returns the crypt string complete with magic and hash.
	 *
	 * @param password
	 *            Cleartext password
	 * @throws java.security.NoSuchAlgorithmException
	 *             If java.security does not support MD5
	 * @return The encrypted password
	 */

	public static String crypt(String password) throws NoSuchAlgorithmException {
		return MD5Crypt.crypt(password);
	}

	/**
	 * Command line method
	 *
	 * @param args
	 *            Command line arguments
	 */

	public static void main(String[] args) {
		try {
			if (args.length == 2) {
				System.out.println(crypt(args[0], args[1]));
				System.exit(0);
			}
			if (args.length == 1) {
				System.out.println(crypt(args[0]));
				System.exit(0);
			}
		} catch (NoSuchAlgorithmException e) {
			System.err.println("MD5 algorithm is not available");
			System.exit(1);
		}

		System.err.println("Syntax: crypt PASSWORD [SALT]");
		System.exit(111);
	}
}