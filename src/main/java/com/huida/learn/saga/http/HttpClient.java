package com.huida.learn.saga.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: huida
 * @date: 2024/3/27
 **/
@Component
@Slf4j
public class HttpClient implements InitializingBean {


    private static  OkHttpClient okHttpClient ;

    @Value("${okhttp.maxIdleConnections}")
    private int maxIdleConnections;
    @Value("${okhttp.keepAliveDuration}")
    private long keepAliveDuration;
    @Value("${okhttp.connectTimeout}")
    private long connectTimeout;
    @Value("${okhttp.readTimeout}")
    private long readTimeout;

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    @Override
    public void afterPropertiesSet() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES))
                .build();
    }


    public String doGet(String url, String queryString, Map<String, String> headerMap) throws Exception {
        Response response = this.getGetResponse(url,queryString,headerMap);
        this.errorResponseLog(response,url);
        return response.body().string();
    }

    public String doPost(String url, String queryString, Map<String,String> paramMap, Map<String, String> headerMap) throws Exception {
        Response response = this.postExecute(url, queryString, paramMap, headerMap);
        this.errorResponseLog(response,url);
        return response.body().string();
    }

    public Response getPostResponse(String url, String queryString, Map<String,String> paramMap, Map<String, String> headerMap) throws Exception {
        Response response = this.postExecute(url, queryString, paramMap, headerMap);
        this.errorResponseLog(response,url);
        return response;
    }

    public Response getGetResponse(String url, String queryString, Map<String, String> headerMap) throws IOException {
        Request.Builder reqBuilder = new Request.Builder();
        this.prepareHeader(headerMap,reqBuilder);
        url = (StringUtils.isEmpty(queryString) ? url : url + "?" + queryString);
        Request getRequest = reqBuilder.url(url).get().build();
        return okHttpClient.newCall(getRequest).execute();
    }

    private Response postExecute(String url, String queryString, Map<String,String> paramMap, Map<String, String> headerMap) throws IOException {
        Response response = null;
        Request.Builder reqBuilder = new Request.Builder();
        if(headerMap !=null){
            for (String key : headerMap.keySet()) {
                reqBuilder.addHeader(key, headerMap.get(key));
            }
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if(paramMap !=null){
            for (String key : paramMap.keySet()) {
                formBodyBuilder.add(key, paramMap.get(key));
            }
        }
        RequestBody body = formBodyBuilder.build();
        if (queryString != null) {
            url = url+"?"+queryString;
        }
        Request postRequest = reqBuilder.url(url).post(body).build();
        return okHttpClient.newCall(postRequest).execute();
    }

    public String doMix(String url, String queryString, Map<String, String> headerMap) throws Exception {
        Response response = null;
        Request.Builder reqBuilder = new Request.Builder();
        this.prepareHeader(headerMap,reqBuilder);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        RequestBody body = formBodyBuilder.build();
        Request postRequest = reqBuilder.url(url+"?"+queryString).post(body).build();
        response = okHttpClient.newCall(postRequest).execute();
        this.errorResponseLog(response,url);
        return response.body().string();
    }

    public String doMix(String url, String queryString,Map<String, String> bodyParamMap, Map<String, String> headerMap) throws Exception {
        Response response = null;
        Request.Builder reqBuilder = new Request.Builder();
        this.prepareHeader(headerMap,reqBuilder);
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if(bodyParamMap != null){
            for (String key : bodyParamMap.keySet()) {
                formBodyBuilder.add(key, headerMap.get(key));
            }
        }
        RequestBody body = formBodyBuilder.build();
        Request postRequest = reqBuilder.url(url+"?"+queryString).post(body).build();
        response = okHttpClient.newCall(postRequest).execute();
        this.errorResponseLog(response,url);
        return response.body().string();
    }

    public String doPostByJson(String url, String json, Map<String, String> headerMap) throws Exception {
        Response response = null;
        Request.Builder builder = new Request.Builder().url(url);
        this.prepareHeader(headerMap,builder);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = builder.post(requestBody).build();
        response = okHttpClient.newCall(request).execute();
        this.errorResponseLog(response,url);
        return response.body().string();
    }


    private void prepareHeader( Map<String, String> headerMap , Request.Builder builder){
        if(headerMap !=null){
            for (String key : headerMap.keySet()) {
                builder.addHeader(key, headerMap.get(key));
            }
        }
    }


    private void errorResponseLog(Response response,String url) throws Exception{
        if(response ==null){
            log.error("okhttpclient 调用失败，url={},  message=response is null",url);
            throw  new Exception(String.format("调用失败，url=%s,  status=null",url));
        }
        if(! response.isSuccessful()){
            log.error("okhttpclient 调用失败，url={},  message={}",url,response.body().string());
            throw  new Exception(String.format("调用失败，url=%s,  status=%s",url,response.code()));
        }
    }


    public boolean callService(String service, Map<String, String> args) {
        boolean result = false;
        // 构建请求URL
        String url = "http://your-service-url/" + service;

        // 构建请求体
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            formBodyBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = formBodyBuilder.build();

        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        // 创建POST请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                result = true;
                log.info("Response from service " + service + ": " + response.body().string());
            } else {
                log.info("Failed to call service " + service + ", response code: " + response.code());
            }
        } catch (IOException e) {
            log.error("Error calling service " + service, e);
        }

        return result;
    }
}
