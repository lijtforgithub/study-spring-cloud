package com.ljt.study.controller;

import com.google.common.collect.ImmutableMap;
import com.ljt.study.inteceptor.RequestDTO;
import com.ljt.study.inteceptor.ResponseDTO;
import com.ljt.study.inteceptor.RestTemplateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ljt.study.inteceptor.ApiPathEnum.ASSOCIATION_WORD;
import static com.ljt.study.inteceptor.ApiPathEnum.QUERY_BY_SOURCE_CLASS_LABEL;
import static com.ljt.study.inteceptor.RestConstants.QUERY_STRING;

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

        RequestDTO dto = new RequestDTO();
        dto.setId(1L);
        dto.setName("xxoo");

        ResponseEntity<String> responseEntity = restTemplateWrapper.executeForEntity(QUERY_BY_SOURCE_CLASS_LABEL, dto, String.class, param);
        return responseEntity.getBody();
    }

    @GetMapping("/word")
    public String word() {
        ImmutableMap<String, String> param = ImmutableMap.of(QUERY_STRING, "高血压", "isEnd", "1");
        ResponseEntity<String> responseEntity = restTemplateWrapper.executeForEntity(ASSOCIATION_WORD, null, String.class, param);
        return responseEntity.getBody();
    }

    @PostMapping("/log")
    public ResponseDTO log(@RequestBody RequestDTO req, String key) {
        ResponseDTO resp = new ResponseDTO();
        resp.setCode(200);
        resp.setMessage("请求日志拦截");
        return resp;
    }

}
