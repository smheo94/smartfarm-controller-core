package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

@Slf4j
public class SHA512PasswordEncoder implements PasswordEncoder {


    @Override
    public boolean matches(CharSequence charSequence, String testPassword) {
        String encodedPassword = this.encode(charSequence);
        return encodedPassword.equals(testPassword);
    }

    @Override
    public String encode(CharSequence charSequence) {
        String encodedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
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
