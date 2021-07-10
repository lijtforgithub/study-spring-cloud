package com.ljt.study.config;

import com.google.common.collect.ImmutableMap;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory#create(java.lang.String)
 * FeignClient预热器：将预热汇入启动阶段,以加快首次请求响应(RT)
 *
 * @author LiJingTang
 * @date 2021-07-10 10:52
 */
@Slf4j
@ConditionalOnClass(name = "org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient")
public class FeignClientWarmUp implements ApplicationRunner, ResourceLoaderAware, EnvironmentAware, ApplicationContextAware {

    private static final String BASE_PCK = "com.ljt.study";

    private ApplicationContext applicationContext;
    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        try {
            final Set<String> appNames = getAppNames();
            preheat(appNames);
        } catch (Exception e) {
            log.warn("FeignClient预热逻辑处理失败", e);
        }
    }

    private void preheat(Set<String> appNames) {
        log.info("FeignClient预热开始:{}", appNames);

        LoadBalancerFeignClient feignClient = applicationContext.getBean("feignClient", LoadBalancerFeignClient.class);
        Request.Options options = new Request.Options();
        Map<String, Collection<String>> headers = ImmutableMap.of(HttpHeaders.CONTENT_TYPE, Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE));

        final String url = "http://%s/actuator/health";

        appNames.forEach(appName -> {
            try {
                // body 传null 必须使用指定编码的构造方法
                Request request = Request.create(Request.HttpMethod.GET, String.format(url, appName), headers, null, StandardCharsets.UTF_8);
                Response response = feignClient.execute(request, options);
                log.info("FeignClient 预热结果：{}->{}", appName, response.status());
            } catch (Exception e) {
                log.error("FeignClient 预热失败：" + appName, e);
            }
        });
    }

    private Set<String> getAppNames() {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        return scanner.findCandidateComponents(BASE_PCK).stream()
                .map(candidateComponent -> ((AnnotatedBeanDefinition) candidateComponent).getMetadata().getAnnotationAttributes(FeignClient.class.getCanonicalName()))
                .filter(Objects::nonNull).map(this::getClientName).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * @see org.springframework.cloud.openfeign.FeignClientsRegistrar
     */
    private ClassPathScanningCandidateComponentProvider getScanner() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false, environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation();
            }
        };
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));

        return scanner;
    }

    private String getClientName(Map<String, Object> client) {
        String value = (String) client.get("contextId");
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("value");
        }
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("name");
        }
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("serviceId");
        }
        if (StringUtils.hasText(value)) {
            return value;
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
