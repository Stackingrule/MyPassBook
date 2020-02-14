package com.stackingrule.passbook.security;

/**
 * <h1>用ThreadLocal 去单独存储每一个线程携带的 Token 信息</h1>
 */
public class AccessContext {

    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String getToken() {
        return token.get();
    }

    public static void setToken(String tokenStr) {
        token.set(tokenStr);
    }

    public static void clearAccessKey() {
        token.remove();
    }

}
