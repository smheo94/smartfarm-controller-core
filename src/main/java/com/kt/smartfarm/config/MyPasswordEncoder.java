package com.kt.smartfarm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

@Slf4j
public class MyPasswordEncoder implements PasswordEncoder {

	@Override
	public boolean matches(CharSequence charSequence, String testPassword) {
		boolean isMatched = false;
		if ( testPassword.startsWith("$") ) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			isMatched = encoder.matches(charSequence, testPassword);
		}
		if ( !isMatched ) {
			String encodedPassword = this.encodeSHA25(charSequence);
			isMatched = encodedPassword.equals(testPassword);
		}
		if ( !isMatched ) {
			String encodedPassword = this.encodeMD5(charSequence);
			isMatched = encodedPassword.equals(testPassword);
		}
		if ( !isMatched ) {
			isMatched = charSequence.toString().equals(testPassword);
		}
		return isMatched;
	}

	@Override
	public String encode(CharSequence charSequence) {
		String encodedPassword = null;
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			encodedPassword = encoder.encode(charSequence);
		} catch ( Exception ex ) {
			log.error("Password Encoding Error", ex);
		}
		return encodedPassword;
	}

	private String encodeSHA25(CharSequence charSequence) {
		String encodedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(charSequence.toString().getBytes());
			encodedPassword = this.bytesToHex(md.digest());
		} catch ( Exception ex ) {
			log.error("Password Encoding Error", ex);
		}
		return encodedPassword;
	}

	private String encodeMD5(CharSequence charSequence) {
		String encodedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(charSequence.toString().getBytes());
			encodedPassword = this.bytesToHex(md.digest());
		} catch ( Exception ex ) {
			log.error("Password Encoding Error", ex);
		}

		return encodedPassword;
	}

	private String bytesToHex(byte[] bytes)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i)
		{
			sb.append(String.format("%1$02x", bytes[i]));
		}
		return sb.toString();
	}
}
