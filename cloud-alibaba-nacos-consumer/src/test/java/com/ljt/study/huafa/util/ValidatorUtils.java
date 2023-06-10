package com.ljt.study.huafa.util;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author LiJingTang
 * @date 2023-06-09 15:58
 */
public final class ValidatorUtils {

    private ValidatorUtils() {}

    private static final Validator VALIDATOR = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(true).buildValidatorFactory().getValidator();

    public static <T> void validateBean(T t) {
        Set<ConstraintViolation<T>> violationSet = VALIDATOR.validate(t);
        if (CollUtil.isNotEmpty(violationSet)) {
            ConstraintViolation<T> violation = violationSet.iterator().next();
            throw new IllegalArgumentException(violation.getMessage());
        }
    }

}
