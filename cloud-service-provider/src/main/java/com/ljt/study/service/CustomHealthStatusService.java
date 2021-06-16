package com.ljt.study.service;

import lombok.Setter;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

/**
 * @author LiJingTang
 * @date 2020-06-23 14:27
 */
@Setter
@Service
public class CustomHealthStatusService implements HealthIndicator {

    private boolean up = true;

    @Override
    public Health health() {
        if (up) {
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }

}
