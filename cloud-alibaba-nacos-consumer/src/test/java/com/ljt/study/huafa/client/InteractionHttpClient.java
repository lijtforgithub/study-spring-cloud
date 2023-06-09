package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.ljt.study.huafa.dto.interaction.InteractionBaseRequest;
import com.ljt.study.huafa.dto.interaction.InteractionBaseResponse;
import com.ljt.study.huafa.enums.InteractionRequestEnum;
import com.ljt.study.huafa.enums.SystemEnum;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.prop.HttpClientProperties;
import com.ljt.study.huafa.prop.InteractionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LiJingTang
 * @date 2023-05-22 08:41
 */
@Slf4j
public class InteractionHttpClient extends GatewayHttpClient<InteractionRequestEnum, InteractionBaseRequest, InteractionBaseResponse<?>> {

    @Autowired
    private InteractionProperties interactionProperties;


    public <R extends InteractionBaseResponse<?>> R execute(InteractionRequestEnum requestEnum, InteractionBaseRequest req, Class<R> clazz) {
        return super.doExecute(requestEnum, req, clazz);
    }


    @Override
    protected void handleResponse(InteractionBaseResponse<?> resp) {
        super.handleResponse(resp);
        Assert.isTrue(StrUtil.isBlank(resp.getMsg()), () -> {
            log.warn("交互中心异常：{}", resp.getData());
            return new ClientException(resp.getMsg());
        });
    }

    @Override
    protected String getBaseUrl() {
        return interactionProperties.getUrl();
    }

    @Override
    protected SystemEnum getSystem() {
        return SystemEnum.INTERACTION;
    }

    @Override
    protected HttpClientProperties getHttpClientProperties() {
        return interactionProperties.getHttpclient();
    }

}
