package com.demo.web.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {

	private static final String MD5 = "MD5";
	private static final String SHA256 = "SHA-256";

	public static String hashSHA256(String input){
		String hash = null;
		try {
			hash = hash(input,SHA256);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static String hashMD5(String input){
		String hash = null;
		try {
			hash = hash(input,MD5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static String hash(String input, String algorithm) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.update(input.getBytes(Charset.forName("Cp1251")));
		byte[] hash = digest.digest();
		for (int i = 0; i < hash.length; i++) {
			if (hash[i] < 0) {
				String x = Integer.toHexString(hash[i]);
				String y = x.substring(x.length() - 2, x.length());
				sb.append(y);
				continue;
			}
			if(Integer.toHexString(hash[i]).length()<2) {
				sb.append("0"+Integer.toHexString(hash[i]));
			}else {
				sb.append(Integer.toHexString(hash[i]));
			}
		}
		return caseUp(sb.toString());
	}

	private static String caseUp(String str) {
		StringBuilder sb = new StringBuilder();
		char[] ch = str.toCharArray();
		for (char c : ch) {
			sb.append(Character.toUpperCase(c));
		}
		return sb.toString();
	}

}
