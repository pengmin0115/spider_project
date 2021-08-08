package com.spider.mail.entity;

import java.util.Date;

/**
 * @author pengmin
 * @date 2021/8/9 0:03
 */

public class MailEntity {

    private Integer id;

    private String subtitle;

    private String content;

    private Date createTime;

    private Integer isSend;

    public Integer getId() {
        return id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
