package com.spider.HJ;

import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 15:24
 */
public class HJ91 {
    /**
     * 走方格（递归方法）
     *
     * 转化为:x,y方向上的数值变化问题,向下走y+1, 向右走x+1,
     * 最终x,y的值等于方格横纵值的时候即为1中方案,方案数自增;
     */

    public static int sum = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            sum = 0;
            calculateNumber(0,0,n,m);
            System.out.println(sum);
        }
    }


    public static void calculateNumber(int i, int j, int n, int m) {
        if (i == n && j == m) {
            ++sum;
            return;
        }
        if (i + 1 <= n) {
            calculateNumber(i + 1, j, n, m);
        }
        if (j + 1 <= m) {
            calculateNumber(i, j + 1, n, m);
        }
    }
}
