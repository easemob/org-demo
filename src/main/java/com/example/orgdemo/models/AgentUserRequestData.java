package com.example.orgdemo.models;

public class AgentUserRequestData {
    /**
     * 租户的管理员的用户名
     */
    private String username;
    /**
     * 租户的管理员的密码
     */
    private String password;
    /**
     * 租户的企业名称
     */
    private String name;
    /**
     * 租户的联系电话
     */
    private String phone;
    /**
     * 可选，是否创建默认的体验关联
     */
    private String experience;
    /**
     * 可选，值为zh_CN 或 en_US
     */
    private String language;
    /**
     * 设置租户的最大坐席数
     */
    private Integer agentMaxNum;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getAgentMaxNum() {
        return agentMaxNum;
    }

    public void setAgentMaxNum(Integer agentMaxNum) {
        this.agentMaxNum = agentMaxNum;
    }
}
