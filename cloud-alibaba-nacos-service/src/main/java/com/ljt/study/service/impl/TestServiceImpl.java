package com.ljt.study.service.impl;

import com.ljt.study.dao.TestMapper;
import com.ljt.study.entity.Test;
import com.ljt.study.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LiJingTang
 * @date 2023-04-13 13:37
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @Transactional
    public Integer save(String key) {
        Test t = new Test();
        t.setCode(key);
        t.setName(key);
        testMapper.insert(t);
        return t.getId();
    }

}
