package com.spider.jsoup;

import com.spider.dao.UserDao;
import com.spider.utils.SpringUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pengmin
 * @date 2021/4/2 11:23
 */

public class MyThread extends Thread {

    private UserDao userDao = (UserDao) SpringUtil.getBean("userDao");

    private int startPage;

    private int userInfoUrlNum;

    private static final Object obj = new Object();

    private static final String TIME_FORMAT = "yyyy-dd-MM hh:mm:ss SSS";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(TIME_FORMAT);

    public MyThread(int startPage, int userInfoUrlNum) {
        this.startPage = startPage;
        this.userInfoUrlNum = userInfoUrlNum;
    }

    public MyThread(int startPage) {
        this.startPage = startPage;
    }

    public static void main(String[] args) {
        System.out.println(FORMATTER.format(new Date()));
    }

    @Override
    public void run() {
        int realStartPage = (startPage - 1) * userInfoUrlNum + 1;
        HashMap<String, String> map = JsoupController.getSecondDegreeUrl(JsoupController.getFirstDegreeUrl(JsoupController.baseUrl, realStartPage));
        Set<Map.Entry<String, String>> entries = map.entrySet();
        // 加锁;
        Lock lock = new ReentrantLock(true);
        lock.tryLock();
        for (Map.Entry<String, String> entry : entries) {

            String name = entry.getKey();
            if (name.length() == 0) {
                name = "null";
            }

            String url = entry.getValue();
            UserInfo userInfo = JsoupController.getBasicInfo(url, name);

            if (userInfo != null) {
                // 全为"null"或为空串的判断;
                if ((StringUtils.isNullOrEmpty(userInfo.getFacebook())
                        && StringUtils.isNullOrEmpty(userInfo.getInstagram())
                        && StringUtils.isNullOrEmpty(userInfo.getPinterest())
                        && StringUtils.isNullOrEmpty(userInfo.getTwitter())
                        && StringUtils.isNullOrEmpty(userInfo.getYoutube()))) {
                    continue;
                }

                // 是否重复的判断;
                if (!"null".equals(userInfo.getUsername())) {
                    // 身份信息相同的不添加;
                    UserInfo userByName = userDao.getUserByName(userInfo.getUsername());
                    if (userByName != null) {
                        if ((userByName.getTwitter().equals(userInfo.getTwitter())
                                && userByName.getPinterest().equals(userInfo.getPinterest())
                                && userByName.getYoutube().equals(userInfo.getYoutube())
                                && userByName.getFacebook().equals(userInfo.getFacebook()))) {
                            continue;
                        }
                    }
                }

                userDao.insert(userInfo);
                System.out.println("[" + FORMATTER.format(new Date()) + "] " + Thread.currentThread() + "[有效信息]: " + userInfo.toString());
            }
        }
        lock.unlock();
    }

    /**
     * 批量插入操作;
     */
    private void batchInsert(ArrayList<UserInfo> list) {
        for (UserInfo info : list) {
            if (!info.getUsername().equals("null")) {
                // 身份信息相同的不添加;
                UserInfo userByName = userDao.getUserByName(info.getUsername());
                if (userByName != null) {
                    if ((userByName.getTwitter().equals(info.getTwitter())
                            && userByName.getPinterest().equals(info.getPinterest())
                            && userByName.getYoutube().equals(info.getYoutube())
                            && userByName.getFacebook().equals(info.getFacebook()))) {
                        continue;
                    }
                }
            }
            userDao.insert(info);
        }
    }
}
