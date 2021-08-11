package com.spider.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spider.dao.MailDao;
import com.spider.mail.entity.MailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

/**
 * @author: pengmin
 * @date: 2021/8/3 15:43
 * <p>
 * 定时爬取web内容发送邮件;
 */

@Component
@EnableScheduling
@SuppressWarnings(value = "all")
public class MailTimerTask {

    private static final String URL = "http://open.iciba.com/dsapi/";

    private static final String HOST = "smtp.163.com";

    private static final String PROTOCOL = "smtp";

    private static final String MAIL_PORT = "465";

    private static final String SENDER_MAIL = "pengmin0115@163.com";

    private static final String AUTH_WORD = "FGDRNZFNCGZHFGQF";

    private static final String RECEIVE_ADDRESS = "1443048473@qq.com";

    private static final String CC_MAIL = "pengmin9501@gmail.com";

    private static final Integer CONNECTION_TIMEOUT = 80 * 1000;

    private static final Integer READ_TIMEOUT = 15 * 1000;

    @Autowired
    private MailDao mailDao;

    @Scheduled(cron = "0 0 6 * * ?")
    public void sendMail() {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(URL).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(temp);
                    stringBuffer.append("\n\n");
                }
                String result = stringBuffer.toString();
                JSONObject jsonObject = JSON.parseObject(result);
                System.out.println(jsonObject);
                String content = jsonObject.get("content").toString();
                String note = jsonObject.get("note").toString();
                System.out.println(content);
                System.out.println(note);
                Properties props = new Properties();
                props.setProperty("mail.smtp.auth", "true");
                props.setProperty("mail.smtp.host", HOST);
                props.setProperty("mail.smtp.ssl.enable", "true");
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.socketFactory.port", MAIL_PORT);
                Session session = Session.getDefaultInstance(props);
                session.setDebug(true);
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(SENDER_MAIL));
                msg.setRecipients(MimeMessage.RecipientType.CC, new Address[]{new InternetAddress(CC_MAIL), new InternetAddress("189889386@qq.com")});
                msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(RECEIVE_ADDRESS));
                msg.setSubject("每日鸡汤", "UTF-8");
                StringBuffer sb = new StringBuffer();
                sb.append("MS Huang:");
                sb.append("</br>");
                sb.append("&nbsp;&nbsp;" + content);
                sb.append("</br>");
                sb.append("(" + note + ")");

                // 插入自定义的邮件内容;
                List<MailEntity> mailEntityList = mailDao.getEntityList();
                Integer titleCount = 1;
                if (!mailEntityList.isEmpty()) {
                    for (MailEntity mailEntity : mailEntityList) {
                        sb.append("</br>");
                        sb.append("Part" + (titleCount++) + ": " + mailEntity.getSubtitle());
                        sb.append("</br>");
                        sb.append(mailEntity.getContent());
                    }
                }

                sb.append("</br>");
                sb.append("<img src='" + jsonObject.get("picture").toString() + "'>");
                sb.append("</br>");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                sb.append("---");
                sb.append("Timestamp: " + format.format(System.currentTimeMillis()));
                msg.setContent(sb.toString(), "text/html; charset=utf-8");
                msg.saveChanges();
                //发送邮件
                Transport transport = session.getTransport(PROTOCOL);
                transport.connect(HOST, SENDER_MAIL, AUTH_WORD);
                //把邮件发送出去
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                // 更新邮件内容状态;
                if (!mailEntityList.isEmpty()) {
                    mailDao.updateStatus(mailEntityList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
        new MailTimerTask().sendMail();
    }
}
