package com.ljt.study.huafa.ldap.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-05-22 20:15
 */
@Slf4j
public class AttributeUtils {

    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new HashMap<>();

    @SneakyThrows
    public static <T> T mapFromAttributes(Attributes attrs, Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            return null;
        }

        Map<String, Field> fieldMap = FIELD_CACHE.computeIfAbsent(clazz, k -> getFieldMap(clazz));
        T t = clazz.newInstance();

        fieldMap.forEach((k, v) -> {
            Class<?> type = v.getType();
            try {
                v.set(t, getValue(attrs, k, type));
            } catch (IllegalAccessException e) {
                log.warn("设置属性{}=>{}异常", k, v.getName(), e);
            }
        });
        return t;
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        ReflectionUtils.doWithFields(clazz, field -> {
            String attr = null;
            org.springframework.ldap.odm.annotations.Attribute annotation = field.getAnnotation(org.springframework.ldap.odm.annotations.Attribute.class);
            if (Objects.isNull(annotation) || !StringUtils.hasLength(annotation.name())) {
                attr = field.getName();
            } else {
                attr = annotation.name();
            }

            fieldMap.put(attr, field);
        });

        Field.setAccessible(fieldMap.values().toArray(new Field[0]), true);
        return fieldMap;
    }

    private static Object getValue(Attributes attrs, String key, Class<?> clazz) {
        Object value = null;
        try {
            Attribute attribute = attrs.get(key);
            if (Objects.isNull(attribute)) {
                log.debug("属性{}不存在", key);
                return null;
            }
            value = attribute.get();
        } catch (NamingException e) {
            log.error("获取属性{}异常", key, e);
        }

        if (Objects.isNull(value)) {
            return null;
        }

        String strValue = value.toString();

        if (String.class == clazz) {
            return strValue;
        }
        if (Integer.class == clazz) {
            return Integer.valueOf(strValue);
        }
        if (Long.class == clazz) {
            return Long.valueOf(strValue);
        }

        throw new IllegalArgumentException("暂不支持类型：" + clazz.getName());
    }

}
