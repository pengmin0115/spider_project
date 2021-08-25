package com.spider.HJ;

import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 15:39
 */
public class HJ86 {
    /**
     * 求一个byte数字对应的二进制数字中1的最大连续数，例如3的二进制为00000011，最大连续2个1
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int i = scanner.nextInt();
            String str = Integer.toBinaryString(i);
            int maxOneStr = 0;
            String[] split = str.split("0");
            for (String element : split) {
                if (element.startsWith("1")) {
                    maxOneStr = Math.max(maxOneStr,element.length());
                }
            }
            System.out.println(maxOneStr);
        }
    }
}
