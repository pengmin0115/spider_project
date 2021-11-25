package com.spider.jsoup;

import com.alibaba.excel.EasyExcel;
import com.spider.dao.UserDao;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengmin
 * @date 2021/3/31 21:29
 */
@RestController
@SuppressWarnings("all")
public class JsoupController {

    @Autowired
    private UserDao userDao;

    static String testDestUrl = "https://www.amazon.it/gp/profile/amzn1.account.AHU6IMJJCOSE2VYS46I4IBYTYHYQ/ref=cm_cr_tr_tbl_571_name";
    static String str2 = "https://www.amazon.it/gp/profile/amzn1.account.AGPRNSLARPXD5KM7KWO5V547CDPA/ref=cm_cr_tr_tbl_2_name";
    private static String myUrl = "https://www.amazon.it/hz/leaderboard/top-reviewers/ref=cm_cr_tr_link_1?page=1";

    static String baseUrl = "https://www.amazon.it/hz/leaderboard/top-reviewers/ref=cm_cr_tr_link_1?page=";
    static String genericUrl = "https://www.amazon.it";

    private static final String DOCUMENT_PATH = "/home/info/";

    private static final String LOCAL_DOCUMENT_PATH = "d:\\info\\";

    private static final Object OBJ = new Object();

    /**
     * 总的1级链接数量;
     */
    private static final int TOTAL_1ST_PAGE = 1000;

    /**
     * 线程数;
     */
    private static final int THREAD_COUNT = 20;

    /**
     * 默认超时时间60S;
     */
    private static final int TIME_OUT = 60 * 1000;

    /**
     * 1个线程负责的1级页面菜单的个数;
     */
    private static final int PAGE_RANGE_SIZE = TOTAL_1ST_PAGE / THREAD_COUNT;

    /**
     * 单个线程负责的具体的userInfo_URL数量;(每页10个用户账号信息)
     */
    private static final int THREAD_TO_URL_PARAMS = PAGE_RANGE_SIZE * 10;

    private static final String TIME_FORMAT = "yyyy-dd-MM hh:mm:ss SSS";

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(TIME_FORMAT);

    // 测试用;
    private static final int THREAD_COUNT_TEST = 2;
    private static final int PAGE_RANGE_SIZE_TEST = 1;
    private static final int THREAD_TO_URL_PARAMS_TEST = 10;

    public static final List<String> IP_LIST = new ArrayList<>();

    public static final AtomicInteger IP_INDEX = new AtomicInteger(0);

    @PostConstruct
    public void init() throws IOException {
        System.out.println("初始化IP列表");
        BufferedReader reader = new BufferedReader(new InputStreamReader(JsoupController.class.getClassLoader().getResourceAsStream("ip.txt")));
        String line = "";
        while ((line = reader.readLine())!= null) {
            IP_LIST.add(line.trim());
        }
        System.out.println("初始化IP列表完成, size:" + IP_LIST.size());
        for (int i = 0; i < IP_LIST.size(); i++) {
            System.out.println((i + 1) + ":" + IP_LIST.get(i));
        }
    }

    @RequestMapping("/export")
    public String export(){
        try {
            ArrayList<UserInfo> list = userDao.getAllUserInfo();
            String fileName = LOCAL_DOCUMENT_PATH + "userInfo.xlsx";
            EasyExcel.write(fileName, UserInfo.class).sheet("info").doWrite(list);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    @RequestMapping("/sql1")
    public String testSql() {
        try {
            String username = "lux";
            UserInfo userByName = userDao.getUserByName(username);
            System.out.println(username);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/test")
    public String testDataSource() {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("zhangsan");
            userInfo.setYoutube("aaa");
            userInfo.setPinterest("aaa");
            userInfo.setInstagram("aaa");
            userInfo.setTwitter("aaa");
            userInfo.setFacebook("aaa");
            userDao.insert(userInfo);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/jsoup")
    public String response() {
        for (int i = 1; i <= THREAD_COUNT; i++) {
            // 任务拆解; 每个线程50页,起始位置为1/51/101...
            MyThread myThread = new MyThread(i, PAGE_RANGE_SIZE);
            myThread.start();
            ArrayList<String> resList = new ArrayList<>();
        }
        return "ok";
    }

    public static void exec() {
        for (int i = 1; i <= THREAD_COUNT; i++) {
            // 任务拆解; 每个线程50页,起始位置为1/51/101...
            MyThread myThread = new MyThread(i, PAGE_RANGE_SIZE);
            myThread.start();
            ArrayList<String> resList = new ArrayList<>();
        }
    }

    /*public static void main(String[] args) {
     *//*ArrayList<String> firstDegreeUrl = getFirstDegreeUrl(myUrl, 1);
        HashMap<String, String> secondDegreeUrl = getSecondDegreeUrl(firstDegreeUrl);

        System.out.println("======");*//*
     *//*ArrayList<String> list = new ArrayList<>();
        list.add(myUrl);
        getSecondDegreeUrl(list);
        for (int i = 1; i <= THREAD_COUNT; i++) {
            // 任务拆解; 每个线程50页,起始位置为1/51/101...
            MyThread myThread = new MyThread(i, PAGE_RANGE_SIZE);
            myThread.start();
            }*//*

     *//*ArrayList<UserInfo> userInfoList = myThread.getUserInfoList();
            userInfoList.stream().forEach(userInfo -> finalList.add(userInfo));*//*
    }*/

    /**
     * 重载1个获取html对象的方法(有默认超时时间的);
     *
     * @param url
     * @return
     */
    public static String getHtml(String url) {
        return getHtml(url, TIME_OUT);
    }

    /**
     * 获取一级URL;
     *
     * @param url
     */
    public static ArrayList<String> getFirstDegreeUrl(String url, int startPage) {
        ArrayList<String> firstDegreeUrlList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (int i = startPage; count < PAGE_RANGE_SIZE; i++) {
            count++;
            firstDegreeUrlList.add(builder.append(url).append(i).toString());
            builder.delete(0, builder.length());
        }
        return firstDegreeUrlList;
    }


    public static void writeToExcel(ArrayList<UserInfo> list, int index) {
        String fileName = DOCUMENT_PATH + "template" + index + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, UserInfo.class).sheet("info").doWrite(list);
    }

    /**
     * @param list
     * @return
     */
    public static HashMap<String, String> getSecondDegreeUrl(ArrayList<String> list) {
        HashMap<String, String> map = new HashMap<>();
        int count = 0;
        for (String url : list) {
            String html = getHtml(url, 100 * 1000);
            Document document = Jsoup.parse(html);
            if (document == null) {
                return map;
            }
            Elements elements = document.select("table").select("a");
            for (Element element : elements) {
                String classAttr = element.attr("class");
                if (classAttr.equals("a-link-normal")) {
                    count++;
                    String name = element.select("b").text();
                    map.put(name, genericUrl + element.attr("href"));
                }
            }
        }
        System.out.println(Thread.currentThread() + " Url prepared OK.");
        return map;
    }

    /**
     * 改进的方法;
     *
     * @param url
     * @return
     */
    public static UserInfo updateGetUserInfo(String url, String userName) {
        String html = getHtml(url);
        if (html.equals("")) {
            return null;
        }
        Document document = Jsoup.parse(html);
        Elements allElements = document.getAllElements();
        boolean flag = false;
        int elementCount = 3;
        UserInfo userInfo = new UserInfo();
        for (Element element : allElements) {
            if (elementCount == 0) {
                String script = element.toString();
                userInfo.setUsername(userName);
                String[] arr = script.split("\n");
                String[] split = arr[2].trim().split("\\{");
                int count = 0;
                for (int i = split.length - 10; count < 5; i--) {
                    String str1 = split[i].split(",")[1];
                    String tempUrl = str1.substring(6);
                    if (tempUrl.startsWith("\"")) {
                        tempUrl = tempUrl.substring(1);
                    }
                    if (tempUrl.endsWith("\"")) {
                        tempUrl = tempUrl.substring(0, tempUrl.length() - 1);
                    }
                    switch (count) {
                        case 4: {
                            userInfo.setFacebook(tempUrl);
                            break;
                        }
                        case 3: {
                            userInfo.setTwitter(tempUrl);
                            break;
                        }
                        case 2: {
                            userInfo.setPinterest(tempUrl);
                            break;
                        }
                        case 1: {
                            userInfo.setInstagram(tempUrl);
                            break;
                        }
                        case 0: {
                            userInfo.setYoutube(tempUrl);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    count++;
                }
                break;
            }
            if (flag && elementCount > 0) {
                elementCount--;
            }
            if (element.attr("id").equals("profile_v5")) {
                flag = true;
            }
        }
        return userInfo;
    }

    /**
     * 获取到用户的公开信息;
     *
     * @param
     */
    public static synchronized UserInfo getBasicInfo(String url, String userName) {
        String html = getHtml(url);
        if (html.equals("")) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userName);
        Document document = Jsoup.parse(html);
        if (document == null) {
            return userInfo;
        }
        // 处理由于网络超时获取不到资源,引起的空指针异常;
        Elements elements = document.select("script");
        for (Element element : elements) {
            String str = element.toString();
            if (str.contains("window.CustomerProfileRootProps")
                    && str.contains("\"type\":\"twitter\"")
                    && str.contains("\"type\":\"facebook\"")
                    && str.contains("\"type\":\"pinterest\"")
                    && str.contains("\"type\":\"instagram\"")
                    && str.contains("\"type\":\"youtube\"")) {
                String[] arr = str.split("\n");
                String[] split = arr[2].trim().split("\\{");
                int count = 0;
                for (int i = split.length - 10; count < 5; i--) {
                    String str1 = split[i].split(",")[1];
                    String tempUrl = str1.substring(6);
                    if (tempUrl.startsWith("\"")) {
                        tempUrl = tempUrl.substring(1);
                    }
                    if (tempUrl.endsWith("\"")) {
                        tempUrl = tempUrl.substring(0, tempUrl.length() - 1);
                    }
                    switch (count) {
                        case 4: {
                            userInfo.setFacebook(tempUrl);
                            break;
                        }
                        case 3: {
                            userInfo.setTwitter(tempUrl);
                            break;
                        }
                        case 2: {
                            userInfo.setPinterest(tempUrl);
                            break;
                        }
                        case 1: {
                            userInfo.setInstagram(tempUrl);
                            break;
                        }
                        case 0: {
                            userInfo.setYoutube(tempUrl);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    count++;
                }
                break;
            }
        }
        return userInfo;
    }

    /**
     * @param @param  url
     * @param @return 页面内容
     * @return String  返回类型
     * @throws
     * @Title: getHtml
     * @Description: 获取页面内容
     */
    public static String getHtml(String url, int timeout) {
        String html = "";
        try {
            int retryTime = 5;
            for (int j = 0; j < retryTime; j++) {
                // 开始链接之前先随机睡眠几秒;
                SecureRandom secureRandom = new SecureRandom();
                int i = secureRandom.nextInt(5);
                Thread.sleep(i * 1000);
                String PROXY_ADDRESS = IP_LIST.get(IP_INDEX.get());
                String[] ip_array = PROXY_ADDRESS.split(":");
                Connection connection = Jsoup.connect(url)
//                        .proxy(ip_array[0], Integer.parseInt(ip_array[1]))
//                        .userAgent("\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31\"")
                        .timeout(timeout)
                        .followRedirects(true);
//                connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//                connection.header("Accept-Encoding", "gzip, deflate, sdch");
//                connection.header("Accept-Language", "zh-CN,zh;q=0.8");
                connection.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
//                connection.header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Mobile Safari/537.36");
                Connection.Response execute = connection.execute();
                if (execute.statusCode() == 200) {
                    html = execute.body();
                    break;
                } else {
                    /*if (IP_INDEX.get() == IP_LIST.size() - 1) {
                        System.out.println("ip资源已用完, 重置遍历索引...");
                        IP_INDEX.set(0);
                    } else {
                        IP_INDEX.incrementAndGet();
                    }
                    System.out.println("503错误更换代理IP, ip index:" + IP_INDEX.get());*/
                    Thread.sleep(i * 1000);
                    System.out.println("[" + FORMATTER.format(new Date()) + "] " + Thread.currentThread() + "retry: " + (j + 1));
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * @param @param  html  页面内容
     * @param @return 图片路径数组
     * @return ArrayList<String>    返回类型
     * @throws
     * @Title: getImgSrcListFromHtml
     * @Description: 获取页面内容图片路径
     */
    public static Set<String> getDestUrlFromHtml(String html) {
        Set<String> list = new HashSet<>();
        //解析成html页面
        Document document = Jsoup.parse(html);
        //获取目标
        Elements elements = document.select("div [class=a-text-center a-spacing-none pagination]").select("href");
        int len = elements.size();
        for (int i = 0; i < len; i++) {
            list.add(baseUrl + elements.get(i).attr("href"));
        }
        return list;
    }

    /**
     * @return List<String>  返回页面url数组
     * @throws
     * @Title: getNextPage
     * @Description: 从页面内容中获取下一个页面路径
     */
    public static HashSet<String> getNextPageUrl(String html) {
        HashSet<String> set = new HashSet<>();
        //解析成html页面
        Document document = Jsoup.parse(html);
        //获取目标
        Elements elements = document.select("div [class=list]").select("a");
        for (int i = 0; i < elements.size(); i++) {
            String href = elements.get(i).attr("href");
            if (href.startsWith("/")) {
                set.add(baseUrl + href);
            } else {
                /*if (!(href.endsWith("jpg") || href.endsWith("png"))){
                    continue;*/
                set.add(href);
            }
           /* String url = baseurl + elements.get(i).attr("href");
            list.add(url);*/
        }

        return set;
    }

    /**
     * @param @param list 图片路径数组
     * @param @param filepath  保存文件夹位置
     * @return void    返回类型
     * @throws
     * @Title: downloadImg
     * @Description: 下载图片 -- 通过获取的流转成byte[]数组，再通过FileOutputStream写出
     */
    public static void downloadImg(List<String> list, String filepath) {
        URL newUrl = null;
        HttpURLConnection hconnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        byte[] bs = null;
        try {
            int len = list.size();
            for (int i = 0; i < len; i++) {
                newUrl = new URL(list.get(i));
                hconnection = (HttpURLConnection) newUrl.openConnection();
                inputStream = hconnection.getInputStream();
                bs = getBytesFromInputStream(inputStream);
                File file = new File(filepath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = filepath + list.get(i).substring(list.get(i).lastIndexOf("/") + 1);
                System.out.println("生成图片路径:" + filepath);
                fileOutputStream = new FileOutputStream(new File(fileName));
                fileOutputStream.write(bs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @param @param  inputStream
     * @param @return byte[]
     * @return byte[]    返回类型
     * @throws
     * @Title: getBytesFromInputStream
     * @Description: InputStream流转换byte[]
     */
    public static byte[] getBytesFromInputStream(InputStream inputStream) {
        byte[] bs = null;
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, len);
            }
            bs = arrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bs;
    }

}
