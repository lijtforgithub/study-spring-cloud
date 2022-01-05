package com.ljt.study.controller;

import com.google.common.collect.ImmutableMap;
import com.ljt.study.rest.RestTemplateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ljt.study.rest.ApiPathEnum.ASSOCIATION_WORD;
import static com.ljt.study.rest.ApiPathEnum.QUERY_BY_SOURCE_CLASS_LABEL;
import static com.ljt.study.rest.RestConstants.QUERY_STRING;

/**
 * @author LiJingTang
 * @date 2022-01-05 10:54
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private RestTemplateWrapper restTemplateWrapper;

    @GetMapping("/token")
    public RestTemplateWrapper.TokenDTO getToken() {
        return restTemplateWrapper.getToken();
    }

    @GetMapping("/query")
    public String query() {
        ImmutableMap<String, String> param = ImmutableMap.of(QUERY_STRING, "头部", "source", "diseaseSymptom", "label", "诊断详述");
        ResponseEntity<String> responseEntity = restTemplateWrapper.executeForEntity(QUERY_BY_SOURCE_CLASS_LABEL, null, String.class, param);
        return responseEntity.getBody();
    }

    @GetMapping("/word")
    public String word() {
        ImmutableMap<String, String> param = ImmutableMap.of(QUERY_STRING, "高血压", "isEnd", "1");
        ResponseEntity<String> responseEntity = restTemplateWrapper.executeForEntity(ASSOCIATION_WORD, null, String.class, param);
        return responseEntity.getBody();
    }

}
