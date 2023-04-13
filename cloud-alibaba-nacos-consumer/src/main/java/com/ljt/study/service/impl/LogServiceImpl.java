package com.ljt.study.service.impl;

import com.ljt.study.dao.LogMapper;
import com.ljt.study.dao.VTestMapper;
import com.ljt.study.entity.Log;
import com.ljt.study.entity.VTest;
import com.ljt.study.feign.NacosServiceFeignClient;
import com.ljt.study.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-04-13 11:29
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;
    @Autowired
    private VTestMapper testMapper;
    @Autowired
    private NacosServiceFeignClient serviceFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTx(String key) {
        saveT(key);

        Integer count = logMapper.selectCount(null);
        log.info("log 记录数 {}", count);

        saveT(key);

        Log l = new Log();
        l.setContent(LocalDateTime.now().toString());
        logMapper.insert(l);
        log.info("保存一条log记录");

        count = logMapper.selectCount(null);
        log.info("log 记录数 {}", count);

        List<VTest> list = testMapper.selectList(null);
        log.info("查询视图结果：{}", list);
    }

    private void saveT(String key) {
        Integer id = serviceFeignClient.saveT(StringUtils.defaultIfBlank(key, RandomStringUtils.randomAlphabetic(5).toLowerCase()));
        log.info("feign保存一条记录：{}", id);
    }

}
