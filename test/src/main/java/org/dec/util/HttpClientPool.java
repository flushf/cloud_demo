package org.dec.util;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author Decimon
 * @date 2018/5/1
 */
public class HttpClientPool {
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(400);
        cm.setDefaultMaxPerRoute(50);
    }

    public static CloseableHttpClient getHttpClient() {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig).build();
        return client;
    }

}
