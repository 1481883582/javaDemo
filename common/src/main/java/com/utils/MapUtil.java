package com.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

@Slf4j
public class MapUtil {
    public static Object convert2Object(Class clazz, Map<String, Object[]> map) throws
            IntrospectionException, InstantiationException, IllegalAccessException {

        BeanInfo bi = Introspector.getBeanInfo(clazz);

        Object obj = clazz.newInstance();

        PropertyDescriptor[] pds = bi.getPropertyDescriptors();

        String pName;
        for (PropertyDescriptor pd : pds) {
            pName = pd.getName();
            if (map.containsKey(pName)) {
                try {
                    pd.getWriteMethod().invoke(obj, map.get(pName)[0]);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return obj;
    }

    /**
     * 将Map转换为对象
     *
     * @param paramMap
     * @param cls
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }
}
