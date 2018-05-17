package org.dec.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Decimon
 * @date 2018/5/1
 */
public class RequestTools {


    public static String processHttpRequest(String url, RequestMethod requestMethod, Map<String, String> paramsMap) {
        List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
        switch (requestMethod) {
            case GET:
                HttpGet httpGet = new HttpGet(url);
                for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext(); ) {
                    String key = it.next();
                    String value = paramsMap.get(key);
                    formparams.add(new BasicNameValuePair(key, value));
                }
                return doRequest(null, httpGet, formparams);
            case POST:
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json");
                for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext(); ) {
                    String key = it.next();
                    String value = paramsMap.get(key);
                    formparams.add(new BasicNameValuePair(key, value));
                }
                return doRequest(httpPost, null, formparams);
            case PUT:
                HttpPut httpPut = new HttpPut();
                httpPut.setHeader("Accept-Encoding", "gzip, deflate");
                httpPut.setHeader("Accept-Language", "zh-CN");
                httpPut.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");

            default:
                break;
        }
        return "";
    }
/*     if (requestMethod == RequestMethod.POST) {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json");
        for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = paramsMap.get(key);
            formparams.add(new BasicNameValuePair(key, value));
        }
        return doRequest(httppost, null, formparams);
    } else if (RequestMethod.GET == requestMethod) {
        HttpGet httppost = new HttpGet(url);
        for (Iterator<String> it = paramsMap.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = paramsMap.get(key);
            formparams.add(new BasicNameValuePair(key, value));
        }
        return doRequest(null, httppost, formparams);
    }*/

    private static String doRequest(HttpPost httpPost, HttpGet httpGet, List<BasicNameValuePair> formparams) {
        try {
            CloseableHttpResponse response = null;
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams);
            // setSocketTimeout设置请求，setConnectTimeout传输超时时间和setConnectionRequestTimeout从连接池获取连接的超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(25000).setConnectTimeout(3000).setConnectionRequestTimeout(2000).build();
            if (null != httpPost) {
                uefEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(uefEntity);
                httpPost.setConfig(requestConfig);
                response = HttpClientPool.getHttpClient().execute(httpPost);
            } else {
                httpGet.setConfig(requestConfig);
                response = HttpClientPool.getHttpClient().execute(httpGet);
            }
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity, "UTF-8");
            if (null == str || "".equals(str)) {
                return "";
            } else {
                return str;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return "";
    }


    /**
     * 处理json格式的body post请求
     *
     * @return
     * @throws Exception
     * @throws ClientProtocolException
     */
    public static String processPostJson(String postUrl, JSONObject jsonObj) throws ClientProtocolException, Exception {
        // HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(postUrl);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String str = null;
        StringEntity s = new StringEntity(jsonObj.toJSONString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(25000).setConnectTimeout(3000).setConnectionRequestTimeout(2000).build();

        post.setEntity(s);
        post.setConfig(requestConfig);

        CloseableHttpResponse response = HttpClientPool.getHttpClient().execute(post);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instreams = entity.getContent();
            str = convertStreamToString(instreams);
            post.abort();
        }
        // System.out.println(str);
        return str;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}