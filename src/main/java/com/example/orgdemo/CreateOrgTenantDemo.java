package com.example.orgdemo;

import com.alibaba.fastjson.JSONObject;
import com.example.orgdemo.models.AgentUserRequestData;
import com.example.orgdemo.models.EasemobChannelRequestData;
import com.example.orgdemo.models.OrgToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateOrgTenantDemo {
    /**
     * 沙箱访问域名：http://sandbox.kefuorg.easemob.com 生成环境访问域名：http://kefuorg.easemob.com
     */
    private static final String DOMAIN = "http://sandbox.kefuorg.easemob.com";

    /**
     * org用户, 需要提前找运营申请org账号
     */
    private static final String USERNAME = "****@organ.com";
    private static final String PASSWORD = "***";
    private static final String ORGID = "****";


    private static final String TOKEN_URL = "/v2/orgs/%s/token";
    private static final String CREATETENANT_URL = "/v2/orgs/%s/tenants";
    private static final String EASEMOBCHANNEL_URL = "/v2/orgs/%s/tenants/%s/easemobchannels";

    private RestTemplate restTemplate;

    public CreateOrgTenantDemo() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(5000);
        restTemplate = new RestTemplate(factory);
    }

    public void createTenat() {
        // 1、获取管理员Token,token有7天有效期，可以缓存
        OrgToken orgToken = callOrgApiToken();
        if (orgToken == null) {
            return;
        }
        String cookie = generateCookie(orgToken);

        // 2、创建tenant
        String tenantId = callCreateTenatApi(cookie);

        // 3、为租户创建一个appKey关联
        if (!StringUtils.hasLength(tenantId)) {
            return;
        }
        callCreateEasemobchannel(tenantId, cookie);

    }

    private String callCreateEasemobchannel(String tenantId, String cookie) {
        String createTenantUrl = String.format(DOMAIN + EASEMOBCHANNEL_URL, ORGID, tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        EasemobChannelRequestData requestData = new EasemobChannelRequestData();
        requestData.setName("API创建关联测试10");// 必填，这里是测试数据
        requestData.setDescription("API创建关联测试描述");// 必填，这里是测试数据
        requestData.setAppKey("1420210722092053#api-test1");// 必填，这里是测试数据
        requestData.setClientId("YXA6YQ9toOrqEeuElB1FW52gNw");// 必填，这里是测试数据
        requestData.setClientSecret("YXA6J_KJJyKMVL15aP2VpLdUaMxCG5Y");// 必填，这里是测试数据
        requestData.setServiceEaseMobIMNumber("***");// 必填，这里是测试数据
        requestData.setServiceEaseMobIMPassword("***");// 必填，这里是测试数据
        HttpEntity<String> requestEntity =
                new HttpEntity(JSONObject.toJSONString(requestData), headers);
        ResponseEntity<ObjectNode> response =
                restTemplate.exchange(createTenantUrl, HttpMethod.POST, requestEntity,
                        ObjectNode.class);
        if (response != null && response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            System.out.println("call create tenant easemobchannel api success ,result : "
                    + response.getBody());
        } else {
            System.out.println(
                    "call create tenant easemobchannel api failed,result : " + response.getBody());
        }
        return null;

    }

    private String callCreateTenatApi(String cookie) {
        String createTenantUrl = String.format(DOMAIN + CREATETENANT_URL, ORGID);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        AgentUserRequestData requestData = new AgentUserRequestData();
        // 为测试方便，这里随机生成用户名
        Random r = new Random();
        requestData.setUsername(r.nextInt(100) + "banxia20210720-2@qq.com");// 必填
        requestData.setPassword("1234qqqq");// 必填，这里是测试数据
        requestData.setName("easemob");// 必填，这里是测试数据
        requestData.setPhone("13800000000");// 必填，这里是测试数据
        requestData.setExperience(false);// 必填，这里是测试数据
        requestData.setAgentMaxNum(10);// 必填，这里是测试数据

        HttpEntity<String> requestEntity =
                new HttpEntity(JSONObject.toJSONString(requestData), headers);
        ResponseEntity<ObjectNode> response =
                restTemplate.exchange(createTenantUrl, HttpMethod.POST, requestEntity,
                        ObjectNode.class);
        if (response != null && response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            System.out.println("call create tenant api success ,result : " + response.getBody());
            ObjectNode objectNode = response.getBody();
            JsonNode entityNode = objectNode.get("entity");
            if (entityNode != null) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> userMap = mapper.convertValue(entityNode.get("agentUser"),
                        new TypeReference<Map<String, String>>() {});
                return userMap.get("tenantId");
            }
        } else {
            System.out.println("call create tenant api failed,result : " + response.getBody());
        }
        return null;
    }

    private String generateCookie(OrgToken orgToken) {
        StringBuilder sb = new StringBuilder();
        sb.append("orgId=");
        sb.append(orgToken.getOrgId());
        sb.append(";");
        sb.append("userId");
        sb.append(orgToken.getUserId());
        sb.append(";");
        sb.append("SESSION=");
        sb.append(orgToken.getToken());
        return sb.toString();
    }

    private OrgToken callOrgApiToken() {
        String tokenUrl = String.format(DOMAIN + TOKEN_URL, ORGID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> map = new HashMap<>();
        map.put("username", USERNAME);// 必填，这里是测试数据
        map.put("password", PASSWORD);// 必填，这里是测试数据
        HttpEntity<String> requestEntity = new HttpEntity(JSONObject.toJSONString(map), headers);
        ResponseEntity<ObjectNode> response =
                restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, ObjectNode.class);
        // System.out.println("got org api token response : " + JSONObject.toJSONString(response));
        if (response != null && response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null) {
            ObjectNode objectNode = response.getBody();
            JsonNode entityNode = objectNode.get("entity");
            if (entityNode != null) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> userMap = mapper.convertValue(entityNode.get("user"),
                        new TypeReference<Map<String, String>>() {});
                Map<String, String> tokenMap = mapper.convertValue(entityNode.get("token"),
                        new TypeReference<Map<String, String>>() {});
                OrgToken orgToken = new OrgToken();
                orgToken.setOrgId(ORGID);
                orgToken.setUserId(userMap.get("userId"));
                orgToken.setToken(tokenMap.get("value"));
                System.out.println("call get org api token success , result : "
                        + JSONObject.toJSONString(orgToken));
                return orgToken;
            }
        } else {
            System.out.println("call get org api token failed ,result : "
                    + JSONObject.toJSONString(response.getBody()));
        }
        return null;
    }

    public static void main(String[] args) {
        CreateOrgTenantDemo demo = new CreateOrgTenantDemo();
        demo.createTenat();
    }

}
