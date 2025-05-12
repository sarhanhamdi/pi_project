package org.example.utils;

public class VerificationSession {
    private static String code;
    private static String email;

    public static void setCode(String c) {
        code = c;
    }

    public static String getCode() {
        return code;
    }

    public static void setEmail(String e) {
        email = e;
    }

    public static String getEmail() {
        return email;
    }

    public static void clear() {
        code = null;
        email = null;
    }
}
