package com.spider.HJ;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/2 13:53
 *
 * 输出负数的个数,和所有正整数的平均值
 */
public class HJ97 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            Integer counter = Integer.parseInt(scanner.nextLine().trim());
            Integer negativeNumCount = 0;
            Integer positiveNumCount = 0;
            String[] arr = scanner.nextLine().split(" ");
            Integer sum = 0;
            for (Integer i = 0; i < counter; i++) {
                int num = Integer.parseInt(arr[i]);
                if (num < 0) {
                    negativeNumCount ++;
                    continue;
                }
                if (num > 0) {
                    positiveNumCount++;
                    sum += num;
                }
            }
            System.out.print(negativeNumCount + " ");
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            System.out.printf( decimalFormat.format((double) sum  / positiveNumCount) + "\n");
        }
    }


}
