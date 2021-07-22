package com.example.orgdemo.models;


public class EasemobChannelRequestData {

    /**
     * APP关联的名称，用于在环信移动客服系统中展示
     */
    private String name;
    /**
     * 登录环信即时通讯云管理后台获取（console.easemob.com）
     */
    private String description;
    /**
     * 登录环信即时通讯云管理后台获取（console.easemob.com）
     */
    private String clientId;
    /**
     * 登录环信即时通讯云管理后台获取（console.easemob.com）
     */
    private String clientSecret;
    /**
     * IM用户的用户名，用于与移动客服系统对接，不可再做其他用途
     */
    private String serviceEaseMobIMNumber;
    /**
     * 上述IM用户的密码
     */
    private String serviceEaseMobIMPassword;
    /**
     * IM用户的用户名，用于与移动客服系统对接，不可再做其他用途
     */
    private String appKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getServiceEaseMobIMNumber() {
        return serviceEaseMobIMNumber;
    }

    public void setServiceEaseMobIMNumber(String serviceEaseMobIMNumber) {
        this.serviceEaseMobIMNumber = serviceEaseMobIMNumber;
    }

    public String getServiceEaseMobIMPassword() {
        return serviceEaseMobIMPassword;
    }

    public void setServiceEaseMobIMPassword(String serviceEaseMobIMPassword) {
        this.serviceEaseMobIMPassword = serviceEaseMobIMPassword;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
