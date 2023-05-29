package com.ljt.study.huafa.client;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-05-24 11:12
 */
@Slf4j
public class RequestHelper {

    public static MultiValueMap<String, String> requestToQuery(Object request) {
        if (Objects.isNull(request)) {
            return null;
        }
        Map<String, Field> fieldMap = new HashMap<>();
        ReflectionUtils.doWithFields(request.getClass(), field -> fieldMap.put(field.getName(), field));
        Field.setAccessible(fieldMap.values().toArray(new Field[0]), true);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        fieldMap.forEach((k, v) -> {
            try {
                Object value = v.get(request);
                if (Objects.nonNull(value)) {
                    map.put(k, Lists.newArrayList(value.toString()));
                }
            } catch (IllegalAccessException e) {
                log.warn("获取属性{}异常", k, e);
            }
        });

        return map;
    }

}
