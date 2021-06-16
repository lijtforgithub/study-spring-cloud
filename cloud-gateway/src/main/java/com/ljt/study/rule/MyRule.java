package com.ljt.study.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2021-05-10 16:47
 */
@Slf4j
@Component
public class MyRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        List<Server> list = getLoadBalancer().getReachableServers();
        log.info("服务：" + list);
        return list.get(0);
    }

}
