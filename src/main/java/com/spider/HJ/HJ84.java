package com.spider.HJ;

import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 16:02
 */
public class HJ84 {

    /**
     * 找出给定字符串中大写字符(即'A'-'Z')的个数。
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Integer upperCount = 0;
            for (int i = 0; i < line.length(); i ++) {
                char ch = line.charAt(i);
                if (ch >= 'A' && ch <= 'Z') {
                    upperCount++;
                }
            }
            System.out.println(upperCount);
        }
    }
}
