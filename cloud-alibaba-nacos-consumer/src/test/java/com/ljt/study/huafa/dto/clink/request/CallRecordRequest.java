package com.ljt.study.huafa.dto.clink.request;

import com.ljt.study.huafa.dto.clink.ClinkBaseRequest;
import lombok.Data;

/**
 * @author LiJingTang
 * @date 2023-06-06 11:47
 */
@Data
public class CallRecordRequest extends ClinkBaseRequest {

    /**
     * 队列号数组
     */
    private String[] qno;

    /**
     * 客户号码(支持模糊搜索)
     */
    private String customerNumber;

    /**
     * 满意度按键值筛选
     */
    private Integer investigationScore;

    /**
     * 座席号，要求只能是 4-11 位数字
     */
    private String cno;

    /**
     * 业务条线、业务系统(业务线标识_业务系统标识)
     */
    private String businessLineAndSystem;

    /**
     * 组织层级（由下划线“_”分割，如多个组织层级，传最上层）
     */
    private String orgSysStr;

    /**
     * 座席姓名(支持模糊搜索)
     */
    private String clientName;

    /**
     * 热线号码
     */
    private String hotline;

    /**
     * 0：全部 1：呼入 2：外呼
     */
    private Integer type;

    /**
     * 接听状态。取值范围如下：
     * 0: 全部
     * 1: 座席接听
     * 2: 已呼叫座席，座席未接听
     * 3: 系统接听
     * 4: 系统未接听-IVR配置错误
     * 5: 系统未接听-停机
     * 6: 系统未接听-欠费
     * 7: 系统未接听-黑名单
     * 8: 系统未接听-未注册
     * 9: 系统未接听-彩铃
     * 10: 系统未接听-网上400
     * 11: 系统未接听-呼叫超出营帐中设置的最大限制
     * 12: 系统未接听-客户呼入系统后在系统未应答前挂机
     * 13: 其他错误
     * 14: 客户未接听(外呼)
     * 15: 座席未接听(外呼)
     * 16: 双方接听(外呼)
     * 默认值为 0
     */
    private Integer status;

    /**
     * 自定义字段
     */
    private String userField;

    /**
     * 标记
     */
    private Integer mark;

    /**
     * 客户省份
     */
    private String province;

    /**
     * 客户城市
     */
    private String city;

    /**
     * 通话记录唯一标识
     */
    private String mainUniqueId;

    /**
     * 请求唯一标识
     */
    private String requestUniqueId;

    /**
     * 开始时间，时间戳格式精确到秒。默认值取当前月份第一天
     */
    private Long startTime;

    /**
     * 结束时间，时间戳格式精确到秒，开始时间和结束时间跨度不能超过一个月。默认值取当前时间
     */
    private Long endTime;

    /**
     * 偏移量，范围 0-99990。默认值为 0，但limit + offset 不允许超过100000
     */
    private Integer offset;

    /**
     * 查询条数，范围 10-100。默认值为 10，但limit + offset 不允许超过100000
     */
    private Integer limit;

}
