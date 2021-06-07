package com.ljt.study.cloud;

import com.alibaba.fastjson.JSON;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author LiJingTang
 * @date 2020-07-03 12:01
 */
@EnableAdminServer
@SpringBootApplication
public class AdminServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class);
    }

    @Bean
    public DingDingNotifier dingDingNotifier(InstanceRepository repository) {
        return new DingDingNotifier(repository);
    }

}

@Slf4j
class DingDingNotifier extends AbstractStatusChangeNotifier {
    public DingDingNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        String serviceName = instance.getRegistration().getName();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        String status = instance.getStatusInfo().getStatus();
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        StringBuilder str = new StringBuilder();
        str.append("系统警告 : 【").append(serviceName).append("】")
            .append("【服务地址】" + serviceUrl)
            .append("【状态】" + status)
            .append("【详情】" + JSON.toJSONString(details));
        return Mono.fromRunnable(() -> log.info("实际发送：{}", str));
    }
}
