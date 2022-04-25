package com.ljt.study.websocket.socketio.registry;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistryAutoConfiguration;
import com.ljt.study.websocket.socketio.SocketIoProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaojing
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnNacosDiscoveryEnabled
@ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
@AutoConfigureAfter(NacosServiceRegistryAutoConfiguration.class)
public class SocketIoRegistryConfig {

	@Bean
	public SocketIoAutoRegistration socketIoAutoRegistration(ApplicationContext applicationContext, SocketIoProperties socketIoProperties,
															 AutoServiceRegistrationProperties autoServiceRegistrationProperties,
															 NacosServiceRegistry nacosServiceRegistry, NacosDiscoveryProperties nacosDiscoveryProperties) {
		return new SocketIoAutoRegistration(applicationContext, socketIoProperties, autoServiceRegistrationProperties, nacosServiceRegistry, nacosDiscoveryProperties);
	}

}
