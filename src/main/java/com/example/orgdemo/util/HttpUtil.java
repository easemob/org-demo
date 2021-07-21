//package com.example.orgdemo.util;
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.config.SocketConfig;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.params.ConnRouteParams;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.client.*;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.protocol.HttpRequestExecutor;
//import org.apache.http.util.EntityUtils;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.ResponseErrorHandler;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import java.io.IOException;
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//
///**
// * @author stliu @ apache.org
// */
//
//public class HttpUtil {
//  //  public static final RequestConfig defaultRequestConfig = getRequestConfig(3000, 3000, 3000);
//    public static final RequestConfig longWaitRequestConfig = getRequestConfig(30000, 3000, 3000);
//
//    private static RequestConfig getRequestConfig(int socketTimeOut, int connectTimeout, int ConnectionRequestTimeout){
//    	return RequestConfig.custom()
//	      .setSocketTimeout(socketTimeOut)
//	      .setConnectTimeout(connectTimeout)
//	      .setConnectionRequestTimeout(ConnectionRequestTimeout)
//	      .build();
//    }
//
//    public static final SocketConfig soketConfig = SocketConfig.custom().setSoKeepAlive(true).setSoReuseAddress(true).setSoTimeout(1000).build();
//	//public static final HttpRequestExecutor requestExecutor = new InstrumentedHttpRequestExecutor(MetricsConfiguration.METRIC_REGISTRY, HttpClientMetricNameStrategies.HOST_AND_METHOD);
//	//public static final PoolingHttpClientConnectionManager httpClientConnectionManager = new InstrumentedHttpClientConnectionManager(MetricsConfiguration.METRIC_REGISTRY);
//    protected static final ResponseErrorHandler ERROR_HANDLER = new ResponseErrorHandler() {
//        @Override
//        public boolean hasError(ClientHttpResponse response) throws IOException {
//            return false;
//        }
//
//        @Override
//        public void handleError(ClientHttpResponse response) throws IOException {
//        }
//    };
//
//    //public static final HttpClientBuilder httpClientBuilderRetry;
//    public static final HttpClientBuilder longWaitHttpClientBuilder;
//
//    public static CloseableHttpClient hc = null;
//
//  //  public static final ClientHttpRequestFactory clientHttpRequestFactory;
//    public static final ClientHttpRequestFactory longWaitClientHttpRequestFactory;
//
//    public static RestTemplate restTemplate;
//    public static final RestTemplate longWaitRestTemplate;
//
//
//    static {
//    	httpClientConnectionManager.setMaxTotal(1000);
//    	httpClientConnectionManager.setDefaultMaxPerRoute(1000);
//    	httpClientConnectionManager.setDefaultSocketConfig(soketConfig);
////    	httpClientBuilderRetry = createCommonHttpClientBuilder()
////        		.setDefaultRequestConfig(defaultRequestConfig)
////        		.setRetryHandler(new StandardHttpRequestRetryHandler(5, false) {
////                    public boolean retryRequest(final IOException exception, final int executionCount, final HttpContext context) {
////                        boolean retry = super.retryRequest(exception, executionCount, context);
////                        boolean customRetry = (exception instanceof ConnectException || exception instanceof ConnectTimeoutException
////                                || exception instanceof SocketTimeoutException || exception instanceof NoHttpResponseException);
////                        return (retry || customRetry) && (executionCount < getRetryCount());
////                    }
////                });
////        hc = httpClientBuilderRetry.build();
//
//  //      clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(hc);
//  //      restTemplate =  new RestTemplate(HttpUtil.clientHttpRequestFactory);
//  //      restTemplate.setErrorHandler(ERROR_HANDLER);
//
//
//        // build http client rest template that wait for more time and also does not retry for socket timeout exception
//        longWaitHttpClientBuilder = createCommonHttpClientBuilder()
//                .setDefaultRequestConfig(longWaitRequestConfig)
//                .setRetryHandler(new StandardHttpRequestRetryHandler(3, false) {
//                    @Override
//                    public boolean retryRequest(final IOException exception, final int executionCount, final HttpContext context) {
//                        boolean retry = super.retryRequest(exception, executionCount, context);
//                        boolean customRetry = exception instanceof ConnectException || exception instanceof ConnectTimeoutException;
//                        return (retry || customRetry) && (executionCount < getRetryCount());
//                    }
//                });
//        longWaitClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(longWaitHttpClientBuilder.build());
//        longWaitRestTemplate = new RestTemplate(longWaitClientHttpRequestFactory);
//        longWaitRestTemplate.setErrorHandler(ERROR_HANDLER);
//    }
//
//
//	private static HttpClientBuilder createCommonHttpClientBuilder() {
//		return HttpClientBuilder.create()
//                .setRequestExecutor(requestExecutor)
//                .setConnectionManager(httpClientConnectionManager)
//                .setDefaultSocketConfig(soketConfig)
//                .setMaxConnTotal(1000)
//                .setMaxConnPerRoute(1000)
//				.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
//				.setServiceUnavailableRetryStrategy(new DefaultServiceUnavailableRetryStrategy() {
//                    @Override
//                    public boolean retryRequest(final HttpResponse response, final int executionCount,
//                                                final HttpContext context) {
//                        boolean retry = super.retryRequest(response, executionCount, context);
//                        boolean customRetry = response.getStatusLine().getStatusCode() == HttpStatus.SC_GATEWAY_TIMEOUT;
//                        return (retry || customRetry) && (executionCount < 3);
//                    }
//                });
//	}
//
//
////    public static JsonNode processHttpMethod(HttpUriRequest httpPost, String proxyHost, int proxyPort, boolean enableProxy) throws IOException {
////        DefaultHttpClient client = new DefaultHttpClient();
////        enableSSLDefaultHttpClient(client);
////        if(enableProxy){
////            HttpHost proxy = new HttpHost(proxyHost,proxyPort);
////            client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
////        }
////        HttpResponse httpResponse = client.execute(httpPost);
////        HttpEntity entity = httpResponse.getEntity();
////        if (entity != null) {
////            String jsonString = EntityUtils.toString(entity, "utf-8");
////            return JSONUtil.getObjectMapper().readValue(jsonString, JsonNode.class);
////        }
////        return null;
////    }
//
////    public static DefaultHttpClient enableSSLDefaultHttpClient(DefaultHttpClient client) {
////        X509TrustManager xtm = new X509TrustManager() {
////            public void checkClientTrusted(X509Certificate[] chain, String authType)
////                    throws CertificateException {}
////
////            public void checkServerTrusted(X509Certificate[] chain, String authType)
////                    throws CertificateException {}
////
////            public X509Certificate[] getAcceptedIssuers() {
////                return null;
////            }
////        };
////        try {
////            SSLContext ctx = SSLContext.getInstance("TLS");
////            ctx.init(null, new TrustManager[] {xtm}, null);
////            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
////            client.getConnectionManager().getSchemeRegistry()
////                    .register(new Scheme("https", 443, socketFactory));
////        } catch (Exception e) {
////        	logger.error("fail to enableSSLDefaultHttpClient,",e);
////        }
////        return client;
////    }
//}
