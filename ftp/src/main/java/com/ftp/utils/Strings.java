package com.ftp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class Strings extends StringUtils {
	/**
	 * 下划线
	 */
	private static final char SEPARATOR = '_';

    /**
     * 去掉字符串指定的前缀
     * @param str 字符串名称
     * @param prefix 前缀数组
     * @return
     */
    public static String removePrefix(String str, String[] prefix) {
        if (StringUtils.isEmpty(str)) {
            return "";
        } else {
            if (null != prefix) {
                String[] prefixArray = prefix;

                for(int i = 0; i < prefix.length; ++i) {
                    String pf = prefixArray[i];
                    if (str.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                        return str.substring(pf.length());//截取前缀后面的字符串
                    }
                }
            }

            return str;
        }
    }

    public static String getStringByEnter(int length, String string) {
        if (isEmpty(string)) return EMPTY;
        for (int i = 1; i <= string.length(); i++) {
            if (string.substring(0, i).getBytes(Charset.defaultCharset()).length > length) {
                return string.substring(0, i - 1) + "\n" +
                        getStringByEnter(length, string.substring(i - 1));
            }
        }
        return string;
    }

	/**
	 *返回去掉-的  16位Id
	 * @return
	 */
	public static String UUId16() {
        String uuId = Strings.UUId();
        uuId = uuId.replaceAll("-", "");
        return uuId.substring(0, 16);
    }

	/**
	 * 返回UUId
	 * @return
	 */
	public static String UUId() {
		return UUID.randomUUID().toString();
	}


	/**
	 * 返回当前字符串毫秒
	 * @return
	 */
    public static String currentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }



    /**
     * min-max之间的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int Random(Integer min, Integer max) {
        return (int) ((max - min) * Math.random()) + min;
    }

    /**
     * 字符串转int类型
     *
     * @param str
     * @return
     */
    public static Integer StrToInteger(String str) {
        if (Strings.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            log.error(e.toString());
        }

        return 0;
    }

    public static boolean isEmpty(List<Object> value) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] value) {
        if (value == null || value.length == 0) {
            return true;
        }
        return false;
    }

	public static boolean isNotEmpty(Long value) {
		return !isEmpty(value);
	}

	public static boolean isEmpty(Long value) {
		if (value == null || EMPTY.equals(value) || 0L == value) {
			return true;
		}
		return false;
	}

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean isEmpty(Object object) {
        if (object == null || EMPTY.equals(object)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(List<Object> value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(CharSequence value) {
        return !isEmpty(value);
    }

    /**
     * @param strs     字符串数组
     * @param splitStr 连接数组的字符串
     * @return
     * @Title: join
     * @Description: 用指定字符串数组相连接，并返回
     * @return: String
     */
    public static String join(String[] strs, String splitStr) {
        if (strs != null) {
            if (strs.length == 1) {
                return strs[0];
            }
            StringBuffer sb = new StringBuffer();
            for (String str : strs) {
                sb.append(str).append(splitStr);
            }
            if (sb.length() > 0) {
                sb.delete(sb.length() - splitStr.length(), sb.length());
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * @param str 字符串
     * @param end 结束位置
     * @return
     * @Title: subStrStart
     * @Description: 从头开始截取
     * @return: String
     */
    public static String subStrStart(String str, int end) {
        return subStr(str, 0, end);
    }

    /**
     * @param str   字符串
     * @param start 开始位置
     * @return
     * @Title: subStrEnd
     * @Description: 从尾开始截取
     * @return: String
     */
    public static String subStrEnd(String str, int start) {
        return subStr(str, str.length() - start, str.length());
    }

    /**
     * @param str    待截取的字符串
     * @param length 长度 ，>=0时，从头开始向后截取length长度的字符串；<0时，从尾开始向前截取length长度的字符串
     * @return
     * @throws RuntimeException
     * @Title: subStr
     * @Description: 截取字符串 （支持正向、反向截取）
     * @return: String
     */
    public static String subStr(String str, int length) throws RuntimeException {
        if (str == null) {
            throw new NullPointerException("字符串为null");
        }
        int len = str.length();
        if (len < Math.abs(length)) {
            throw new StringIndexOutOfBoundsException("最大长度为" + len + "，索引超出范围为:" + (len - Math.abs(length)));
        }
        if (length >= 0) {
            return subStr(str, 0, length);
        } else {
            return subStr(str, len - Math.abs(length), len);
        }
    }


    /**
     * 截取字符串 （支持正向、反向选择）
     *
     * @param str   待截取的字符串
     * @param start 起始索引 ，>=0时，从start开始截取；<0时，从length-|start|开始截取
     * @param end   结束索引 ，>=0时，从end结束截取；<0时，从length-|end|结束截取
     * @return 返回截取的字符串
     * @throws RuntimeException
     */
    public static String subStr(String str, int start, int end) throws RuntimeException {
        if (str == null) {
            throw new NullPointerException("");
        }
        int len = str.length();
        int s = 0;//记录起始索引
        int e = 0;//记录结尾索引
        if (len < Math.abs(start)) {
            throw new StringIndexOutOfBoundsException("最大长度为" + len + "，索引超出范围为:" + (len - Math.abs(start)));
        } else if (start < 0) {
            s = len - Math.abs(start);
        } else if (start < 0) {
            s = 0;
        } else {//>=0
            s = start;
        }
        if (len < Math.abs(end)) {
            throw new StringIndexOutOfBoundsException("最大长度为" + len + "，索引超出范围为:" + (len - Math.abs(end)));
        } else if (end < 0) {
            e = len - Math.abs(end);
        } else if (end == 0) {
            e = len;
        } else {//>=0
            e = end;
        }
        if (e < s) {
            throw new StringIndexOutOfBoundsException("截至索引小于起始索引:" + (e - s));
        }

        return str.substring(s, e);
    }

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }


    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || EMPTY.equals(str.trim());
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return EMPTY;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 把null值转为“”
     */
    public static String nullToString(Object obj) {
        return ObjectUtils.toString(obj).trim();
    }

	/**
	 * 转换成string
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
    	if(Strings.isEmpty(bytes)) return Strings.EMPTY;
    	return new String(bytes, StandardCharsets.UTF_8);
	}
}
