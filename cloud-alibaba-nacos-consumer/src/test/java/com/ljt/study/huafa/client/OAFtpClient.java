package com.ljt.study.huafa.client;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author LiJingTang
 * @date 2023-05-29 09:59
 */
@Slf4j
@AllArgsConstructor
public class OAFtpClient {

    public boolean upload(String workDir, String filename, InputStream inputStream) {
        FTPClient client = null;
        try {
            client = getClient();
            setClient(workDir, client);
            return client.storeFile(filename, inputStream);
        } catch (IOException e) {
            log.error("上传FTP文件失败：", e);
        } finally {
            closeClient(client);
        }
        return false;
    }


    private void closeClient(FTPClient client) {
        if (client != null) {
            try {
                client.disconnect();
            } catch (IOException e) {
                log.error("关闭ftp失败" + e.getMessage());
            }
        }
    }

    public FTPClient getClient() {
        FTPSClient client = new FTPSClient("SSL", true);
        client.setControlEncoding("UTF-8");
        client.enterLocalPassiveMode();
        try {
            client.setConnectTimeout(10000);
            client.connect(ftpConfiguration.getHost(), ftpConfiguration.getPort());
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new BizException("FTP连接失败");
            }
            boolean loginSuccess = client.login(ftpConfiguration.getUsername(), ftpConfiguration.getPassword());
            if (!loginSuccess) {
                throw new BizException("FTP登录失败");
            }
            return client;
        } catch (Exception e) {
            log.error("FTP操作失败：", e);
            throw new BizException("FTP操作失败：" + e.getMessage());
        }
    }

    private final FtpRemoteFileTemplate ftpTemplate;

    public boolean upload(MultipartFile file) {
        Message<MultipartFile> message = MessageBuilder.withPayload(file).build();
//        boolean flag = messageChannel.send(message);
        String send = ftpTemplate.send(message, FileExistsMode.REPLACE);
        log.debug("上传文件{} => {}", file.getOriginalFilename(), send);
        return true;
    }

    @SneakyThrows
    public boolean upload(File file) {
        boolean flag;
        try (InputStream inputStream = new FileInputStream(file)){
            flag = ftpTemplate.execute(session -> {
                try {
                    session.write(inputStream, file.getName());
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });
        }


        log.debug("上传文件{} => {}", file.getName(), flag);
        return flag;
    }

}
