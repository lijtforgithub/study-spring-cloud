package com.ljt.study.huafa.api.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.StrUtil;
import com.ljt.study.huafa.api.OASysApi;
import com.ljt.study.huafa.client.OAFtpClient;
import com.ljt.study.huafa.client.OAHttpClient;
import com.ljt.study.huafa.dto.oa.request.FlowDetailRequest;
import com.ljt.study.huafa.dto.oa.request.FlowFileXml;
import com.ljt.study.huafa.dto.oa.request.StartFlowRequest;
import com.ljt.study.huafa.dto.oa.request.StartFlowSimpleRequest;
import com.ljt.study.huafa.dto.oa.response.FlowDetailResponse;
import com.ljt.study.huafa.dto.oa.response.FlowStatusResponse;
import com.ljt.study.huafa.dto.oa.response.StartFlowResponse;
import com.ljt.study.huafa.enums.OARequestEnum;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.prop.OAProperties;
import com.ljt.study.huafa.util.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:26
 */
@RequiredArgsConstructor
public class OASysApiImpl implements OASysApi {

    private final OAHttpClient client;

    @Autowired
    private OAProperties oaProperties;
    @Autowired
    private OAFtpClient ftpClient;

    @Override
    public StartFlowResponse startFlow(StartFlowSimpleRequest request) {
        ValidatorUtils.validateBean(request);
        String fileData = getFileData(request.getFormFile());

        StartFlowRequest req = new StartFlowRequest();
        req.setSystemName(oaProperties.getSystemName());
        req.setLoginName(request.getLoginName());
        req.setAccountName(request.getAccountName());
        req.setAuthCode(generateAuthCode(req.getSystemName(), request.getLoginName()));
        req.setFlowCode(request.getFlowCode());
        req.setSubject(request.getSubject());
        req.setDatas(request.getFormData());
        req.setFiles(fileData);
        req.setSendState("0");
        req.setVersion("IDM");
        return startFlow(req);
    }

    @Override
    public StartFlowResponse startFlow(StartFlowRequest request) {
        return client.execute(OARequestEnum.START_FLOW, request, StartFlowResponse.class);
    }

    @Override
    public FlowDetailResponse getFlowDetail(String flowId) {
        FlowDetailRequest request = new FlowDetailRequest();
        request.setFlowId(flowId);
        return client.execute(OARequestEnum.GET_FLOW_DETAIL, request, FlowDetailResponse.class);
    }

    @Override
    public FlowStatusResponse getFlowStatus(String flowId) {
        FlowDetailRequest request = new FlowDetailRequest();
        request.setFlowId(flowId);
        return client.execute(OARequestEnum.GET_FLOW_STATUS, request, FlowStatusResponse.class);
    }

    @Override
    public String generateAuthCode(String systemName, String loginName) {
        Assert.notBlank(systemName, "系统名称不能为空");
        Assert.notBlank(loginName, "登录名不能为空");

        return DigestUtils.md5DigestAsHex((systemName + loginName).getBytes());
    }

    private String getFileData(Map<String, InputStream> fileMap) {
        if (CollectionUtils.isEmpty(fileMap)) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        Map<String, InputStream> newFileMap = new HashMap<>();

        for (Map.Entry<String, InputStream> entry : fileMap.entrySet()) {
            if (StrUtil.isBlank(entry.getKey()) || Objects.isNull(entry.getValue())) {
                throw new IllegalArgumentException("附件参数不合法");
            }

            String fileName = newFileName(entry.getKey());
            map.put(fileName, entry.getKey());
            newFileMap.put(fileName, entry.getValue());
        }

        boolean success = ftpClient.batchUpload(newFileMap);
        Assert.isTrue(success, () -> new ClientException("文件上传失败"));
        return getXmlString(map);
    }

    private static String getXmlString(Map<String, String> map) {
        List<FlowFileXml.XmlUrl> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            FlowFileXml.XmlUrl xml = new FlowFileXml.XmlUrl();
            xml.setValue(entry.getKey());
            xml.setName(entry.getValue());
            list.add(xml);
        }

        try {
            FlowFileXml fileXml = new FlowFileXml();
            fileXml.setUrls(list);
            return fileXml.toXml().replaceAll(" {4}", "").replaceAll("\n", "");
        } catch (JAXBException e) {
            throw new ClientException(e.getMessage());
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    private static String newFileName(String fileName) {
        String suffix = FileUtil.getSuffix(fileName);

        String date = LocalDateTime.now().format(FORMATTER);
        return date + CharPool.DASHED + System.currentTimeMillis() + CharPool.DOT + suffix;
    }

}
