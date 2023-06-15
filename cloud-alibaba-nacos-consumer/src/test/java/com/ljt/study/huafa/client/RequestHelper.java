package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ljt.study.huafa.dto.QueryParam;
import com.ljt.study.huafa.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2023-05-24 11:12
 */
@Slf4j
public class RequestHelper {

    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    private static final Cache<Class<?>, Map<String, Field>> FIELD_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();
    private static final Cache<Class<?>, Map<String, Field>> FIELD_QUERY_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    public static MultiValueMap<String, String> requestToQuery(Object request) {
        if (Objects.isNull(request)) {
            return null;
        }

        return getQueryMap(request, getField(request.getClass()));
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

        Map<String, Field> fieldMap = getField(clazz);
        Map<String, Field> map = getField(clazz);

        fieldMap.forEach((k, v) -> {
            QueryParam anno = v.getAnnotation(QueryParam.class);
            if (Objects.nonNull(anno)) {
                map.put(StrUtil.blankToDefault(anno.value(), v.getName()), v);
            }
        });

        return getQueryMap(request, map);
    }

    private static MultiValueMap<String, String> getQueryMap(Object request, Map<String, Field> fieldMap) {
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

    private static Map<String, Field> getField(Class<?> clazz) {
        try {
            return FIELD_CACHE.get(clazz, () -> {
                Map<String, Field> fieldMap = new HashMap<>();
                ReflectionUtils.doWithFields(clazz, field -> fieldMap.putIfAbsent(field.getName(), field));
                fieldMap.remove(SERIAL_VERSION_UID);
                Field.setAccessible(fieldMap.values().toArray(new Field[0]), true);
                return fieldMap;
            });
        } catch (ExecutionException e) {
            log.warn("获取类字段信息异常", e);
            return Collections.emptyMap();
        }
    }

    private static final Set<Class<?>> UN_SUPPORT_TYPE = Sets.newHashSet(File.class, InputStream.class, Field.class, Method.class);
    private static final Predicate<Class<?>> PREDICATE = clazz -> {
        boolean array = clazz.isArray();
        if (array) {
            return true;
        }

        return UN_SUPPORT_TYPE.stream().anyMatch(type -> type.isAssignableFrom(clazz));
    };

    public static void checkRequest(Object req) {
        if (Objects.isNull(req)) {
            return;
        }
        Map<String, Field> fieldMap = getField(req.getClass());
        for (Field field : fieldMap.values()) {
            Class<?> type = field.getType();
            Assert.isFalse(PREDICATE.test(type), "不支持类型：" + type.getName());

            if (!Collection.class.isAssignableFrom(type)) {
                continue;
            }

            ResolvableType resolvableType = ResolvableType.forField(field).as(Collection.class);
            Class<?> cls = resolvableType.getGeneric().resolve();
            Assert.isFalse(Objects.nonNull(cls) && PREDICATE.test(cls), () -> new ClientException("不支持类型：" + (cls.isArray() ? "数组" : cls.getName())));
        }
    }

}
