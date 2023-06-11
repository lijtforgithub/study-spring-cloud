package com.ljt.study.huafa.client;

import cn.hutool.core.lang.Assert;
import com.ljt.study.huafa.exception.ClientException;
import com.ljt.study.huafa.prop.OAProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-05-29 09:59
 */
@Slf4j
public class OAFtpClient {

    @Autowired
    private OAProperties oaProperties;

    public boolean batchUpload(Map<String, InputStream> fileMap) {
        FTPClient client = null;
        try {
            client = getClient();
            for (Map.Entry<String, InputStream> entry : fileMap.entrySet()) {
                boolean success = client.storeFile(entry.getKey(), entry.getValue());
                Assert.isTrue(success, () -> new IOException(entry.getKey()));
            }
            return true;
        } catch (IOException e) {
            log.error("上传FTP文件失败", e);
            return false;
        } finally {
            closeClient(client);
        }
    }

    public boolean upload(String filename, InputStream inputStream) {
        FTPClient client = null;
        try {
            client = getClient();
            return client.storeFile(filename, inputStream);
        } catch (IOException e) {
            log.error("上传FTP文件失败", e);
            return false;
        } finally {
            closeClient(client);
        }
    }

    private void closeClient(FTPClient client) {
        if (Objects.nonNull(client)) {
            try {
                client.disconnect();
            } catch (IOException e) {
                log.error("关闭FT连接异异常", e);
            }
        }
    }

    private FTPClient getClient() {
        FTPSClient client = new FTPSClient("SSL", true);
        client.setControlEncoding(StandardCharsets.UTF_8.name());
        client.enterLocalPassiveMode();
        client.setConnectTimeout(10_000);

        try {
            client.connect(oaProperties.getFtp().getHost(), oaProperties.getFtp().getPort());
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new ClientException("FTP连接失败");
            }
            boolean success = client.login(oaProperties.getFtp().getUsername(), oaProperties.getFtp().getPassword());
            if (!success) {
                throw new ClientException("FTP登录失败");
            }
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.changeWorkingDirectory(oaProperties.getFtp().getWorkDir());
            return client;
        } catch (Exception e) {
            log.error("创建FTP连接失败", e);
            throw new ClientException("FTP连接异常：" + e.getMessage());
        }
    }

}
