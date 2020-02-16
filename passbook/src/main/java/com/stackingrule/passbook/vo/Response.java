package com.stackingrule.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Controller 通用响应</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /** 错误码：正确返回0 **/
    private Integer errorCode = 0;

    /** 错误信息：正确返回空 **/
    private String errorMsg = "";

    /** 返回值对象 **/
    private Object data;

    /**
     * <h2>正确响应构造函数</h2>
     * @param data
     */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * <h2>空响应</h2>
     * @return
     */
    public static Response success() {
        return new Response();
    }

    /**
     * <h2>错误响应</h2>
     * @param errorMsg
     * @return
     */
    public static Response failure(String errorMsg) {
        return new Response(-1, errorMsg, null);
    }
}
