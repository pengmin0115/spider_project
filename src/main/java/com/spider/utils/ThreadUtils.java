package com.spider.utils;

import com.spider.dao.UserDao;
import com.spider.jsoup.UserInfo;

import java.util.List;

/**
 * @author pengmin
 * @date 2021/6/26 12:26
 */

public class ThreadUtils implements Runnable{

    private boolean flag = false;
    private List<UserInfo> results = null;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<UserInfo> getResults() {
        return results;
    }

    public void setResults(List<UserInfo> results) {
        this.results = results;
    }

    private UserDao userDao= (UserDao) SpringUtil.getBean("userDao");

    @Override
    public void run() {
        results = userDao.getAllUserInfo();
        flag = true;
    }
}
