package com.ljt.study.cloud.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2021-05-11 19:27
 */
@Component
public class SentinelConfig {

    public static final String RS_HW = "HelloWorld";

    @PostConstruct
    private void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RS_HW);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
