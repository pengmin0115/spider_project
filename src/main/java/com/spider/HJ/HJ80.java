package com.spider.HJ;

import java.util.*;

/**
 * @author: pengmin
 * @date: 2021/8/25 17:45
 */
public class HJ80 {
    /**
     * 将两个整型数组按照升序合并，并且过滤掉重复数组元素。
     * 输出时相邻两数之间没有空格。
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            for (int i = 0; i < 2; i++) {
                Integer count = Integer.parseInt(scanner.nextLine());
                String str = scanner.nextLine();
                String[] arr = str.split(" ");
                for (String numString : arr) {
                    Integer integer = Integer.parseInt(numString);
                    if (!includeNumber(integer,list)) {
                        list.add(integer);
                    }
                }
            }
            Collections.sort(list, (o1, o2) -> (o1 - o2));
            for (Integer integer : list) {
                System.out.print(integer);
            }
            System.out.print("\n");
        }
    }

    public  static boolean includeNumber(Integer num, List<Integer> list){
        boolean flag = false;
        for (Integer integer : list) {
            if (integer.equals(num)) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }
}
