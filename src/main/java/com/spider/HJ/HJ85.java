package com.spider.HJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 15:45
 */
public class HJ85 {

    /**
     * 最长回文子串
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String sourceStr = scanner.nextLine();
            int maxLength = 0;
            int length = sourceStr.length();
            if (isValid(sourceStr)) {
                System.out.println(length);
                continue;
            }
            List<String> list = new ArrayList<>();
            for (int i =0; i <= length; i++) {
                for (int j = 0; j<= length; j++) {
                    if (j > i) {
                        list.add(sourceStr.substring(i,j));
                    }
                }
            }
            for (String str : list) {
                if (isValid(str)){
                    maxLength = Math.max(maxLength,str.length());
                }
            }
            System.out.println(maxLength);
        }
    }

    public static boolean isValid(String string) {
        if (string.length() <= 0) {
            return false;
        }
        if (string.length() == 1) {
            return true;
        }
        int length = string.length();
        if (length % 2 == 0) {
            for (int i = 0; i <= length / 2; i++) {
                if (string.charAt(i) != string.charAt(length - 1 - i)) {
                    return false;
                }
            }
            return true;
        }

        for (int i = 0; i < (length - 1) / 2; i++) {
            if (string.charAt(i) != string.charAt(length -1 - i)) {
                return false;
            }
        }
        return true;

    }
}
