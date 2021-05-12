package com.ljt.study;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2021-05-12 09:24
 */
class SentinelTest {

    private static final String RS_TEST = "test";

    /**
     * API使用方式
     */
    public static void main(String[] args) {
        initFlowRules();
        for (int i = 0; i < 30; i++) {
            Entry entry = null;
            try {
                entry = SphU.entry(RS_TEST);
                /*您的业务逻辑 - 开始*/
                System.out.println("业务逻辑");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e) {
                /*流控逻辑处理 - 开始*/
                System.out.println("block...");
                /*流控逻辑处理 - 结束*/
            } finally {
                if (Objects.nonNull(entry)) {
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(RS_TEST);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
