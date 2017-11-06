package com.fortech.elasticSearchREST.persistance;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public final class AuthenticationUtils {

	public static String encodeSHA256(String password) 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
		mDigest.update(password.getBytes("UTF-8"));
		byte[] digest = mDigest.digest();
		return DatatypeConverter.printBase64Binary(digest).toString();
	}
	
	public static void main(String[] args) {
		
	}
}
