package com.rkv.trackmail.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailUtilTest {

    @Test
    public void testValidEmailAddress() {
        assertTrue(EmailUtil.isEmailAddressValid("name@mail.com"));
        assertTrue(EmailUtil.isEmailAddressValid("name+name@mail.com"), "+ is valid");
    }

    @Test
    public void testInvalidEmailAddress() {
        assertFalse(EmailUtil.isEmailAddressValid("name|xx@mail.com"), "pipe is not valid");
        assertFalse(EmailUtil.isEmailAddressValid("name'xx@mail.com"), "' is not valid");
        assertFalse(EmailUtil.isEmailAddressValid(".name@mail.com"), ". s not valid");
    }
}
