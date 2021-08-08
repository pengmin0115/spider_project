package com.spider.dao;

import com.spider.mail.entity.MailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author pengmin
 * @date 2021/8/9 0:13
 */
@Mapper
public interface MailDao {
    /**
     * 插入邮件内容
     * @param subtitle
     * @param content
     * @param date
     */
    void insert(@Param("subtitle") String subtitle,
                @Param("content") String content,
                @Param("date") Date date);

    /**
     * 获取邮件内容;
     * @return
     */
    List<MailEntity> getEntityList();

    /**
     *
     * @param mailEntityList
     */
    void updateStatus(List<MailEntity> mailEntityList);
}
