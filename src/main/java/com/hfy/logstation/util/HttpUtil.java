package com.hfy.logstation.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpUtil {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String simpleGet(String url) {
        HttpGet get = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(get)){
            return readEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String simplePost(String url, Map<String, String> params,String encoding) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<>();
        params.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encoding);
        post.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(post)){
            return readEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String postWithJson(String url, String json) {
        HttpPost post = new HttpPost(url);
        post.addHeader("context-type", "application/json");
        post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
        try(CloseableHttpResponse response = httpClient.execute(post)) {
            return readEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readEntity(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                return reader.lines()
                        .collect(Collectors.joining());
            }
        }
        return null;
    }
}
