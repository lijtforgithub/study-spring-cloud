package com.ljt.study.controller;

import com.ljt.study.api.ServiceApi;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2021-08-05 11:43
 */
@RestController
@RequestMapping("/resp")
public class ResponseController {

    @Autowired
    private LoadBalancerClient balancerClient;
    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    @GetMapping("/url")
    public void httpUrlConnection(HttpServletResponse response) {
        URL url = new URL(getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(HttpMethod.GET.name());
        connection.setDoOutput(true);

        if (HttpStatus.OK.value() == connection.getResponseCode()) {
            write(response, IOUtils.toByteArray(connection.getInputStream()));
        }
    }

    private String getUrl() {
        ServiceInstance instance = balancerClient.choose(ServiceApi.APP_NAME);
        return instance.getUri().toString() + "/api/resp";
    }

    @SneakyThrows
    private void write(HttpServletResponse response, byte[] body) {
        response.setContentType("text/plain; charset=utf-8");
        response.getOutputStream().write(body);
    }

    @SneakyThrows
    @GetMapping("/okhttp")
    public void ribbon(HttpServletResponse response) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUrl())
                .build();
        Response resp = client.newCall(request).execute();

        if (resp.isSuccessful()) {
            write(response, Objects.requireNonNull(resp.body()).bytes());
        }
    }

    /**
     * https://blog.csdn.net/junoohoome/article/details/116074595
     * https://blog.csdn.net/hearain528/article/details/104924978
     */
    @SneakyThrows
    @GetMapping("/rest")
    public void restTemplate(HttpServletResponse response) {
        ResponseEntity<Resource> entity = restTemplate.getForEntity("http://" + ServiceApi.APP_NAME + "/api/resp", Resource.class);
        write(response, IOUtils.toByteArray(Objects.requireNonNull(entity.getBody()).getInputStream()));
    }

}
