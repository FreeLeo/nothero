package com.unbelievable.nothero.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			return "";
		}

		return hexString.toString();
	}

	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();

			char str[] = new char[16 * 2];

			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	// 适合大点文件MD5
	public static String checkSum(String fileName) {
		if (fileName == null) {
			return null;
		}
		InputStream fis;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(fileName);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * weichar 签名
	 * @param key
	 * @return
	 */
	public static String getStringMD5(String key) {
		String value = null;
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("MD5");
			currentAlgorithm.reset();
			currentAlgorithm.update(key.getBytes());
			byte[] hash = currentAlgorithm.digest();
			String d = "";
			int usbyte = 0;
			for (int i = 0; i < hash.length; i += 2) {
				usbyte = hash[i] & 0xFF;
				if (usbyte < 16) {
					d = d + "0" + Integer.toHexString(usbyte);
				} else {
					d = d + Integer.toHexString(usbyte);
				}
				usbyte = hash[(i + 1)] & 0xFF;
				if (usbyte < 16) {
					d = d + "0" + Integer.toHexString(usbyte);
				} else {
					d = d + Integer.toHexString(usbyte);
				}
			}
			value = d.trim().toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Logger.i("MD5","MD5 algorithm not available.");
		}
		return value;
	}
}
