package com.rkv.trackmail.util;

import java.util.regex.Pattern;

public class EmailUtil {

    public static boolean isEmailAddressValid(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }
}
