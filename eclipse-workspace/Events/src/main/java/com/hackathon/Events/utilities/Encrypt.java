package com.hackathon.Events.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Default MD5
public class Encrypt {
	
	
	public static String process(String pass) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");  
        
        m.update(pass.getBytes());  
          
        byte[] bytes = m.digest();  
          
        StringBuilder s = new StringBuilder();  
        for(int i=0; i< bytes.length ;i++)  
        {  
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
        }  
          
        return s.toString();  
	}
	
}
