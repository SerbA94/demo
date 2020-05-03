package com.demo.web.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class EncodeUtilTest {
	
	private static final String MD5 = "MD5";
	private static final String SHA256 = "SHA-256";

	@Test
	void hashSHA256Test() {
		assertEquals("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08",
				EncodeUtil.hashSHA256("test"));
	}
	
	@Test
	void hashMD5Test() {
		assertEquals("098F6BCD4621D373CADE4E832627B4F6", EncodeUtil.hashMD5("test"));
	}
	
	@Test
	void hashTest() {
		try {
			assertEquals("098F6BCD4621D373CADE4E832627B4F6", EncodeUtil.hash("test", MD5));
			assertEquals("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08",
					EncodeUtil.hash("test", SHA256));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			assertEquals("098F6BCD4621D373CADE4E832627B4F6", EncodeUtil.hash("test", "adad"));
			fail("Method should throw NoSuchAlgorithmException");
		} catch (NoSuchAlgorithmException e) {
		} catch (Exception e) {
			fail("Method should throw NoSuchAlgorithmException");
		}
	}

}
