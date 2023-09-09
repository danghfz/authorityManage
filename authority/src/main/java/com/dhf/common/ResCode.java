package com.dhf.common;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/09 11:20
 */
public enum ResCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 失败
     */
    ERROR(500, "失败"),
    /**
     * 未登录
     */
    NO_LOGIN(600, "未登录"),
    /**
     * 没有权限
     */
    NO_AUTH(700, "没有权限");
    private final int code;
    private final String msg;

    private ResCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int code() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
