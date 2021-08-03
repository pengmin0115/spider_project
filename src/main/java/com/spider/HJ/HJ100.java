package com.spider.HJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author: pengmin
 * @date: 2021/7/28 11:13
 *
 *
 * 功能:等差数列 2，5，8，11，14。。。。
 * 输入:正整数N >0
 * 输出:求等差数列前N项和
 * 本题为多组输入，请使用while(cin>>)等形式读取数据
 */
public class HJ100 {

    public static void main(String[] args) throws IOException {

        /*Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int count = Integer.parseInt(scanner.nextLine());
            System.out.println((2 + 2 + (count - 1) * 3) * count / 2);
        }*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while (!"".equals(line = reader.readLine()) && line!= null) {
            Integer count = Integer.parseInt(line);
            System.out.println((2 + 2 + (count - 1) * 3) * count / 2);
        }
    }
}
