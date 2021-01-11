package com.utils;

import java.util.regex.Pattern;

/**
 * @program:
 * @description: 校验工具类
 * @author: xujingyang
 * @create: 2019-01-05 16:21
 **/
public class ValidatorUtil {

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证英文名
     */
    public static final String REGEX_STR= "^[a-zA-Z_][a-zA-Z\\d_]*$";

    /**
     * 正则表达式：验证是不是数字
     */
    public static final String REGEX_NUMERIC= "[0-9]*";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 判断是否是英文字符串  是true 否false
     * @param str
     * @return
     */
    public static boolean isStr(String str){
        return Pattern.matches(REGEX_STR, str);
    }

    /**
     * 判断是否是英文字符串  是true 否false
     * @param str 传入的字符串
     * @param msg  如果是null 弹框需要的文案
     * @param field  如果不是英文字符串 文案抬头加的文字
     * @return
     */
    public static boolean isStr(String str, String  msg, String field){
        if(Strings.isEmpty(str)){
            return true;
        }
        if(!isStr(str)){
            return true;
        }
        return false;
    }


    /**
     * 判断是否是英文字符串  是true 否false  不判null
     * @param str     传入的字符串
     * @param field  判断是否是英文字符串  是true 否false
     * @return
     */
    public static boolean isStr(String str, String field){
        if(Strings.isNotEmpty(str) && !isStr(str)){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isLong(String str){
        return Pattern.matches(REGEX_NUMERIC, str);
    }
}