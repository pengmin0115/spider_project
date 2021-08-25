package com.spider.HJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: pengmin
 * @date: 2021/8/25 16:17
 */
public class HJ82 {

    /**
     * 分子为1的分数称为埃及分数。现输入一个真分数(分子比分母小的分数，叫做真分数)，请将该分数分解为埃及分数。
     * 如：8/11 = 1/2+1/5+1/55+1/110。
     * <p>
     * 注：真分数指分子小于分母的分数，分子和分母有可能gcd不为1！
     * 如有多个解，请输出任意一个。
     * 请注意本题含有多组样例输入
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] arr = line.split("/");
            int upperNum = Integer.parseInt(arr[0]);
            int bottomNum = Integer.parseInt(arr[1]);
            divideNum(upperNum, bottomNum);
            List<Integer> list = getBottomNumbers(upperNum, bottomNum);
            StringBuilder builder = new StringBuilder();
            for (Integer integer : list) {
               builder.append("1/" + integer + "+");
            }
            builder.deleteCharAt(builder.length() - 1);
            System.out.println(builder.toString());
        }
    }

    public static List<Integer> getBottomNumbers(Integer upperNum,Integer bottomNum){
        List<Integer> resList = new ArrayList<>();
        for (int i = 2;; i++) {
            if (canAdd(resList,i,upperNum,bottomNum)) {
                resList.add(i);
            }
            if (isEquals(resList,upperNum,bottomNum)) {
                return resList;
            }
        }
    }

    /**
     * 检验是否等值;
     * @param resList
     * @param upperNum
     * @param bottomNum
     * @return
     */
    private static boolean isEquals(List<Integer> resList, Integer upperNum, Integer bottomNum) {
        boolean flag = false;
        Integer commonBottom = bottomNum;
        Integer upperNumberInList = 0;
        for (Integer tempBottom : resList) {
            commonBottom *= tempBottom;
        }
        Integer rowUpperNumCount = upperNum * (commonBottom / bottomNum);
        for (Integer integer : resList) {
            upperNumberInList += (commonBottom / integer);
        }
        if (rowUpperNumCount.equals(upperNumberInList)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 是否可以加入当前分母;
     * @param list
     * @param num
     * @param upperNum
     * @param bottomNum
     * @return
     */
    public static boolean canAdd(List<Integer> list, Integer num,Integer upperNum,Integer bottomNum){
        boolean flag = false;
        Integer commonBottom = bottomNum * num;
        Integer upperNumberInList = 0;
        for (Integer tempBottom : list) {
            commonBottom *= tempBottom;
        }
        Integer rowUpperNumCount = upperNum * (commonBottom / bottomNum);
        for (Integer integer : list) {
            upperNumberInList += (commonBottom / integer);
        }
        upperNumberInList += (commonBottom / num);
        if (rowUpperNumCount >= upperNumberInList) {
            flag = true;
        }
        return flag;
    }

    /**
     * 分数化简;
     * @param upperNum
     * @param bottomNum
     */
    public static void divideNum(Integer upperNum, Integer bottomNum) {
        for (int i = 2; i <= upperNum; i++) {
            if (upperNum % i == 0 && bottomNum % i == 0) {
                upperNum = upperNum / i;
                bottomNum = bottomNum / i;
                divideNum(upperNum,bottomNum);
            }
        }
    }

}
