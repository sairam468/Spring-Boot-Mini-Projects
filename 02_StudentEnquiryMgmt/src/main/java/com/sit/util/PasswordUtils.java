package com.sit.util;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtils {

	public static String generateRandomPasswor() {
		   // Define character set (added missing semicolon and fixed "z" typo)
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Generate a random password with only letters
        String pwd = RandomStringUtils.random(6, characters);

        return pwd;    }
	
}
