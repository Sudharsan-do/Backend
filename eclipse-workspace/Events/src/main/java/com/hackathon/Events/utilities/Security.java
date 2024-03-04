package com.hackathon.Events.utilities;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//Default MD5
public class Security {
	
	private static final String key = "qwertyuiopasdfgh";
	
	/*public static void main(String args[]) throws Exception {
		byte[] s = encrypt("qwerty");
		System.out.println(new String(Base64.getEncoder().encode(s)));
		System.out.println(decrypt(s));
	}*/
	
	public static String encrypt(String pass) throws Exception {
		SecretKey originalKey = new SecretKeySpec(key.getBytes(), 0, key.length(), "AES");
		Cipher desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.ENCRYPT_MODE, originalKey);
		byte e[] = desCipher.doFinal(pass.getBytes("UTF8"));
		return new String(Base64.getEncoder().encode(e));
	}
	
	public static String decrypt(String pass) throws Exception {
		byte[] arr = Base64.getDecoder().decode(pass);
		SecretKey originalKey = new SecretKeySpec(key.getBytes(), 0, key.length(), "AES");
		Cipher desCipher = Cipher.getInstance("AES");
		desCipher.init(Cipher.DECRYPT_MODE, originalKey);
		byte e[] = desCipher.doFinal(arr);
		return new String(e);
	}
	
}
