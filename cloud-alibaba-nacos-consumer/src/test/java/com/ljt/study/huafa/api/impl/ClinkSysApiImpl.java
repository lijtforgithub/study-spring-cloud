package com.ljt.study.huafa.api.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.ljt.study.huafa.api.ClinkSysApi;
import com.ljt.study.huafa.client.ClinkHttpClient;
import com.ljt.study.huafa.dto.clink.ClinkBaseResponse;
import com.ljt.study.huafa.dto.clink.request.*;
import com.ljt.study.huafa.dto.clink.response.CallRecordResponse;
import com.ljt.study.huafa.dto.clink.response.CallRecordUrlResponse;
import com.ljt.study.huafa.dto.clink.response.ClientDetailResponse;
import com.ljt.study.huafa.dto.clink.response.ClinkLoginResponse;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.exception.ClinkClientException;
import com.ljt.study.huafa.prop.ClinkProperties;
import com.ljt.study.huafa.util.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.ljt.study.huafa.enums.ClinkRequestEnum.*;

/**
 * @author LiJingTang
 * @date 2023-06-07 09:11
 */
@Slf4j
@RequiredArgsConstructor
public class ClinkSysApiImpl implements ClinkSysApi {

    private final ClinkHttpClient client;

    @Autowired
    private ClinkProperties clinkProperties;

    @Override
    public ClientDetailResponse getClientDetail(ClientDetailRequest request) {
        return client.execute(GET_CLIENT_DETAIL, request, ClientDetailResponse.class);
    }

    @Override
    public ClinkLoginResponse getLoginInfo(ClinkLoginRequest request) {
        ValidatorUtils.validateBean(request);
        ClinkLoginResponse response = new ClinkLoginResponse();
        response.setIsLogin(false);

        try {
            ClientDetailRequest req = new ClientDetailRequest();
            req.setUsername(request.getUsername());
            ClientDetailResponse.ClientDetail client = getClientDetail(req).getClient();
            if (Objects.nonNull(client)) {
                setLoginInfo(request, response, client);
            }
        } catch (ClientException e) {
            log.info(GET_CLIENT_DETAIL.getDesc(), e);
        }

        return response;
    }

    @Override
    public CallRecordResponse listCallRecord(CallRecordRequest request) {
        return client.execute(LIST_CALL_RECORD, request, CallRecordResponse.class);
    }

    @Override
    public String getCallRecordUrl(CallRecordUrlRequest request) {
        try {
            return client.execute(GET_CALL_RECORD_URL, request, CallRecordUrlResponse.class).getRecordFileUrl();
        } catch (ClinkClientException e) {
            if ("DownloadRecordFileError".equalsIgnoreCase(e.getError().getCode())) {
                return null;
            }

            throw e;
        }
    }

    @Override
    public void onLine(OnLineRequest request) {
        client.execute(ONLINE, request, ClinkBaseResponse.class);
    }

    private void setLoginInfo(ClinkLoginRequest request, ClinkLoginResponse response, ClientDetailResponse.ClientDetail client) {
        response.setIsLogin(true);
        response.setCno(client.getCno());
        response.setPlatform(clinkProperties.getPlatform());
        response.setBusinessLine(clinkProperties.getBusinessLine());
        response.setBindType(client.getTelType() != null ? client.getTelType() - 1 : null);
        response.setBindTel(client.getBindTel());

        String data = request.getUid() + "-" + System.currentTimeMillis();
        RSA rsa = new RSA(null, clinkProperties.getPublicKey());
        String encryptStr = Base64.encode(rsa.encryptBase64(data, KeyType.PublicKey));
        response.setToken(encryptStr);
    }

}
