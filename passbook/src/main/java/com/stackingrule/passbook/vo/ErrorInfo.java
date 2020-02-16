package com.stackingrule.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>统一错误信息</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo<T> {

    /** 统一错误码标识 **/
    public static final Integer ERROR = -1;

    /** 特定错误码 **/
    public Integer code;

    /** 错误信息 **/
    private String message;

    /** 请求 url  **/
    private String url;

    /** 请求返回数据 **/
    private T data;

}
