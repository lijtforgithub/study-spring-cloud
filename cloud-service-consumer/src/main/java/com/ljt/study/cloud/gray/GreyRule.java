package com.ljt.study.cloud.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * 灰度规则 Hystrix+Thread 不生效 不是同一个线程
 *
 * @author LiJingTang
 * @date 2021-05-29 17:18
 */
public class GreyRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        List<Server> list = getLoadBalancer().getReachableServers();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        GreyDTO dto = GreyHelper.get();
        if (Objects.nonNull(dto) && Objects.nonNull(dto.getUserId())) {
            GreyHelper.remove();
            if (dto.getUserId() <= 100) {
                Optional<Server> optional = list.stream().filter(server -> {
                    DiscoveryEnabledServer enabledServer = (DiscoveryEnabledServer) server;
                    String version = enabledServer.getInstanceInfo().getMetadata().get(GreyHelper.VERSION);
                    // 自处的v1可以从配置或数据库获取
                    return "v1".equalsIgnoreCase(version);
                }).findFirst();
                if (optional.isPresent()) {
                    return optional.get();
                }
            }
        }

        return list.get(new Random().nextInt(list.size()));
    }

}
