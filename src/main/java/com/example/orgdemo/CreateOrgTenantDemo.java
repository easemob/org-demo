package com.example.orgdemo;

import com.example.orgdemo.util.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heguojiao
 * @date 2021/7/21 15:07
 */
public class CreateOrgTenantDemo {
    /**
     * 沙箱访问域名：http://sandbox.kefuorg.easemob.com
     * 生成环境访问域名：http://kefuorg.easemob.com
     */
    private static final String DOMAIN = "http://sandbox.kefuorg.easemob.com";

    /**
     * org用户
     */
    private static final String USERNAME = "hemeili20210721@organ.com";
    private static final String PASSWORD = "!@#123";

    private static final String TOKEN_URL = "/v2/orgs/2059/token";
    private static final String CREATETENANT_URL = "/v2/orgs/2059/tenants";

    private HttpClientUtil httpClientUtil = null;
    private String charset = "utf-8";

//    /**
//     * post请求传输json参数
//     *
//     * @param url
//     *            url地址
//     * @param param
//     *            参数
//     * @return
//     */
//    public static String doPost(String url, String param) {
//        // post请求返回结果
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String resultStr = null;
//        HttpPost httpPost = new HttpPost(url);
//        // 设置请求和传输超时时间
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(3000).setConnectTimeout(1000).build();
//        httpPost.setConfig(requestConfig);
//        try {
//            if (null != param) {
//                // 解决中文乱码问题
////                StringEntity entity = new StringEntity("cardId=1&userId=2", "utf-8");
////                entity.setContentEncoding("UTF-8");
////                entity.setContentType("application/json");
////                httpPost.setEntity(entity);
//                StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
//                stringEntity.setContentEncoding("UTF-8");
//                stringEntity.setContentType("application/json");
//                httpPost.setEntity(stringEntity);
//            }
//            CloseableHttpResponse result = httpClient.execute(httpPost);
//            //请求发送成功，并得到响应
//            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                try {
//                    //读取服务器返回过来的json字符串数据
//                    resultStr = EntityUtils.toString(result.getEntity(), "utf-8");
//                } catch (Exception e) {
//                    //logger.error("post请求提交失败:" + url, e);
//                }
//            }
//        } catch (IOException e) {
//            //logger.error("post请求提交失败:" + url, e);
//        } finally {
//            httpPost.releaseConnection();
//        }
//        return resultStr;
//    }
//
//    /**
//     * 发送 get请求
//     */
//    @SuppressWarnings("finally")
//    public static String doGet(String url) {
//        String result = "";
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        try {
//            // 创建httpget.
//            HttpGet httpget = new HttpGet(url);
//            System.out.println("executing request " + httpget.getURI());
//            // 执行get请求.
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            try {
//                // 获取响应实体
//                HttpEntity entity = response.getEntity();
//                // 打印响应状态
//                System.out.println(response.getStatusLine());
//                if (entity != null) {
//                    // 打印响应内容长度
//                    //System.out.println("Response content length: " + entity.getContentLength());
//                    // 打印响应内容
//                    //System.out.println("Response content: " + EntityUtils.toString(entity));
//                    result = EntityUtils.toString(entity);
//                }
//            } finally {
//                response.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭连接,释放资源
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//    }


    public CreateOrgTenantDemo() {
        httpClientUtil = new HttpClientUtil();
    }

    public void createTenat(){
        //1、获取管理员Token
        String getTokenUrl = DOMAIN+TOKEN_URL;
        Map<String,String> OrgUserMap = new HashMap<>();
        OrgUserMap.put("username", USERNAME);
        OrgUserMap.put("password", PASSWORD);
        String result = httpClientUtil.doPost(getTokenUrl, OrgUserMap, charset);
        System.out.println(result);
    }

    public static void main(String[] args){
        CreateOrgTenantDemo demo = new CreateOrgTenantDemo();
        demo.createTenat();
    }

}
