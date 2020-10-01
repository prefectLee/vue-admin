package com.lee.vue.system.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author      :lee
 * @date        :Created in 2020/10/1
 * @description :响应类
 * @since       :JDK 1.6
 **/
@ApiModel("响应类")
@Data
public class Result<T>{

    @ApiModelProperty(value ="响应代码 200:success;<br>500:系统错误;<br>90000000:操作失败;<br>90000002:未登录/token过期;" +
            "<br>90000003:参数错误;<br>10000011:验证码错误;<br>10000007:当前用户无该接口权限")
    private  int code;

    @ApiModelProperty(value ="提示信息")
    private  String msg;

    @ApiModelProperty(value ="数据")
    private T data;


    private Result() {
    }

    /**
     * 自定义返回状态码
     *
     * @param code
     * @param msg
     * @param data
     */
    private static <T> Result result(int code, String msg, T data) {
        Result result = new Result();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result;
    }

    /**
     * 自定义返回状态码
     *
     * @param httpStatus
     * @param data
     */
    private static <T> Result result(HttpStatus httpStatus, T data) {
        return result(httpStatus.status, httpStatus.value, data);
    }

    /**
     * 自定义返回状态码 [不建议使用，建议扩展方法]
     *
     * @param status
     * @param message
     */
    public static <T> Result result(int status, String message) {
        return result(status, message, null);
    }


    /**
     * 操作成功
     */
    public static <T> Result success() {
        return success(HttpStatus.SUCCESS.value, null);
    }

    /**
     * 操作成功
     *
     * @param totalPage
     * @param total
     * @param list
     */
    public static <T> Result success(int totalPage, long total, List<T> list) {
        return success(new ResultPage<T>(totalPage, total, list));
    }


    /**
     * 操作成功
     *
     * @param total
     * @param list
     */
    public static <T> Result success(long total, List<T> list) {
        return success(new ResultPage<T>(list.size(), total, list));
    }

    /**
     * 操作成功
     *
     * @param resultPage
     */
    public static <T> Result success(ResultPage<T> resultPage) {
        return success(HttpStatus.SUCCESS.value, resultPage);
    }


    /**
     * 操作成功
     *
     * @param message
     */
    public static <T> Result success(String message) {
        return success(message, null);
    }

    /**
     * 操作成功
     *
     * @param data
     */
    public static <T> Result success(T data) {
        return success(HttpStatus.SUCCESS.value, data);
    }

    /**
     * 操作成功
     *
     * @param message
     * @param data
     */
    public static <T> Result success(String message, T data) {
        return result(HttpStatus.SUCCESS.status, message, data);
    }


    /**
     * 操作失败
     *
     * @param message
     */
    public static <T> Result fail(String message) {
        return result(HttpStatus.FAIL.status, message, null);
    }


    /**
     * 未授权
     */
    public static <T> Result error401() {
        return result(HttpStatus.UNAUTHORIZED.status, HttpStatus.UNAUTHORIZED.value, null);
    }


    /**
     * 不支持的请求类型
     */
    public static <T> Result error415() {
        return result(HttpStatus.UNSUPPORTED_MEDIA_TYPE.status, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value, null);
    }


    /**
     * 404 未找到
     */
    public static <T> Result error404() {
        return result(HttpStatus.NOT_FOUND.status, HttpStatus.NOT_FOUND.value, null);
    }


    /**
     * 服务器内部错误
     */
    public static <T> Result error500() {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.status, HttpStatus.INTERNAL_SERVER_ERROR.value, null);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultPage<T> {

        /**
         * 当前页，有多少条
         */
        private Integer totalPage;

        /**
         * 总共，有多少条
         */
        private Long total;

        /**
         * 数据
         */
        private List<T> list;

    }

    /**
     * restful api 返回状态码。不够的时候自己扩展
     */
    @Getter
    public enum HttpStatus {

        /**
         * 正常 返回代码
         */
        SUCCESS(200, "操作成功"),

        /**
         * 错误 返回代码
         */
        FAIL(400, "操作失败"),

        /**
         * 参数检验失败 返回代码
         */
        VALIDATE_FAILED(304, "参数检验失败"),

        /**
         * 404 未找到
         */
        NOT_FOUND(404, "Not Found"),

        /**
         * 未授权
         */
        UNAUTHORIZED(401, "你还没有经过授权认证"),

        /**
         * 不支持的媒体类型
         */
        UNSUPPORTED_MEDIA_TYPE(415, "不支持此请求类型！"),


        /**
         * 服务器内部错误 返回代码
         */
        INTERNAL_SERVER_ERROR(500, "服务器内部错误");

        private int status;

        private String value;

        HttpStatus(int status, String value) {
            this.status = status;
            this.value = value;
        }

    }

}
