package com.ljt.study.huafa.dto.clink.response;

import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-06-09 15:16
 */
@Data
public class ClientDetailResponse extends ClinkBaseResponse {

    private ClientDetail client;


    @Data
    public static class ClientDetail {

        /**
         * 座席号
         */
        private String cno;

        /**
         * 座席名称
         */
        private String name;

        /**
         * 区号
         */
        private String areaCode;

        /**
         * 座席角色，0普通座席 1班长座席
         */
        private Integer role;

        /**
         * 绑定电话
         */
        private String bindTel;

        /**
         * 电话绑定类型，电话类型， 1: 固话；2: 手机；3:IP话机；4:软电话
         */
        private Integer telType;

        /**
         * 是否激活 0否 1是
         */
        private Integer active;

        /**
         * 座席状态 0离线 1离线
         */
        private Integer status;

        /**
         * 号码隐藏类型，0不隐藏 1全局。
         */
        private Integer hiddenTel;

        /**
         * 队列号
         */
        private List<String> qnos;

        /**
         * 外显号
         */
        private List<String> clid;

        /**
         * 外显号配置
         */
        private List<ClinkArea> clidArea;

        /**
         * 外显规则 1: 随机；2: 轮选
         */
        private Integer clidRule;

        /**
         * 轮选类型 1:按天轮选；2:按次轮选。当外显规则为轮选时为必选项
         */
        private Integer recurrentselectionType;

        /**
         * 轮选值设置。当外显规则为轮选时为必选项
         * 1: 按天轮选，每n次外呼更换一次外显号码，可设置 1-30 次
         * 2: 按次轮选，每n次外呼更换一次外显号码，可设置 1-30 天
         */
        private Integer recurrentselectionValue;

        /**
         * 座席权限
         */
        private ClientPermission permission;

        /**
         * 座席类型，1：全渠道、2：呼叫中心、3：在线客服
         */
        private Integer type;

        /**
         * 在线客服座席会话上限开关，0：关闭、1：开启
         */
        private Integer chatLimit;

        /**
         * 在线客服座席会话上限
         */
        private Integer chatLimitNum;

        /**
         * 座席所属组织层级标识数组
         */
        private String[] orgSys;

        /**
         * 座席所属业务线与业务系统标识数组
         */
        private String[] businessLineAndSystem;

        /**
         * 座席所属组织层级名称数组
         */
        private String[] orgSysName;

        /**
         * 座席所属业务线与业务系统名称数组
         */
        private String[] businessLineAndSystemName;
    }


    @Data
    public static class ClientPermission {

        /**
         * 语音转写，0关闭 1开启。默认关闭
         */
        private Integer asr;

        /**
         * 外呼权限， 0关闭 1无限制 2国内长途 3国内本地。默认无限制
         */
        private Integer call;

        /**
         * 通话记录查看权限，1全部 2所属队列 3本座席。默认全部
         */
        private Integer cdr;

        /**
         * 录音试听下载权限， 0关闭 1试听 2试听下载。默认试听下载
         */
        private Integer recordDownload;

        /**
         * 短信发送权限，0关闭 1开启。默认关闭
         */
        private Integer sms;

        /**
         * 通话录音权限，0关闭 1呼入 2外呼 3全部。默认全部
         */
        private Integer record;

        /**
         * 在线客服查看会话记录权限 ，0：全部、1：所属队列、2：本座席； 默认值为 0
         */
        private Integer chat;
    }

}

