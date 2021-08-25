package com.spider.HJ;

import com.sun.media.sound.SoftTuning;

import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 17:37
 */
public class HJ81 {

    /**
     * 判断短字符串中的所有字符是否在长字符串中全部出现。
     * 请注意本题有多组样例输入。
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        String str1 = "";
        String str2 = "";
        while (scanner.hasNextLine()) {
            count++;
            if (count == 1) {
                str1 = scanner.nextLine();
            } else  {
                str2 = scanner.nextLine();
            }
            if(count == 2) {
                boolean flag = true;
                char[] chars = str1.toCharArray();
                for (char ch : chars) {
                    if (!str2.contains(String.valueOf(ch))) {
                        flag = false;
                        break;
                    }
                }
                System.out.println(flag);
                str1 = "";
                str2 = "";
                count = 0;
            }
        }
    }
}
