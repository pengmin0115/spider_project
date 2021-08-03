package com.spider.HJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: pengmin
 * @date: 2021/7/28 11:03
 * <p>
 * 反转字符串
 */
public class HJ106 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        StringBuilder builder = new StringBuilder();
        for (int i = line.length() - 1; i >= 0; i--) {
            builder.append(line.charAt(i));
        }
        System.out.println(builder.toString());
    }
}
