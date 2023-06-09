package com.ljt.study.huafa.client;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.ljt.study.huafa.dto.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

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

        BiConsumer<Field, Map<String, Field>> consumer = (field, fieldMap) -> {
            fieldMap.put(field.getName(), field);
        };

        return getQueryMap(request, consumer);
    }

    public static MultiValueMap<String, String> requestExtractQuery(Object request) {
        if (Objects.isNull(request)) {
            return null;
        }

        Class<?> clazz = request.getClass();
        QueryParam annotation = clazz.getAnnotation(QueryParam.class);
        if (Objects.nonNull(annotation)) {
            return requestToQuery(request);
        }

        BiConsumer<Field, Map<String, Field>> consumer = (field, fieldMap) -> {
            QueryParam anno = field.getAnnotation(QueryParam.class);
            if (Objects.nonNull(anno)) {
                fieldMap.put(StrUtil.blankToDefault(anno.value(), field.getName()), field);
            }
        };

        return getQueryMap(request, consumer);
    }

    private static MultiValueMap<String, String> getQueryMap(Object request, BiConsumer<Field, Map<String, Field>> consumer) {
        Map<String, Field> fieldMap = new HashMap<>();
        ReflectionUtils.doWithFields(request.getClass(), field -> consumer.accept(field, fieldMap));
        Field.setAccessible(fieldMap.values().toArray(new Field[0]), true);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        fieldMap.forEach((k, v) -> {
            try {
                Object value = v.get(request);
                if (Objects.nonNull(value)) {
                    if (value instanceof Collection) {
                        Collection<?> collection = (Collection<?>) value;
                        List<String> list = collection.stream().map(Object::toString).collect(Collectors.toList());
                        map.addAll(k, list);
                    } else {
                        map.put(k, Lists.newArrayList(value.toString()));
                    }
                }
            } catch (IllegalAccessException e) {
                log.warn("获取属性{}异常", k, e);
            }
        });
        return map;
    }

}
