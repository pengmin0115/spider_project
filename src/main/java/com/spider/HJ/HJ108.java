package com.spider.HJ;

import java.util.*;

/**
 * @author: pengmin
 * @date: 2021/7/28 10:19
 * <p>
 * 最小公倍数
 */
public class HJ108 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] arr = line.split(" ");
            int num1 = Integer.parseInt(arr[0]);
            int num2 = Integer.parseInt(arr[1]);
            List<Integer> resList = getCommonNumbers(divideNumber(num1), divideNumber(num2));
            int res = 1;
            for (Integer integer : resList) {
                res *= integer;
            }
            System.out.println(res);
        }
    }

    /**
     * 分解质因数;
     * @param number
     * @return
     */
    private static List<Integer> divideNumber(int number) {
        List<Integer> list = new ArrayList<>();
        for (int num = 1; num <= number; num++) {
            if(num == number) {
                list.add(num);
                break;
            }
            if (number % num == 0 && num != 1) {
                list.add(num);
                number /= num;
                num = 0;
            }
        }
        return list;
    }

    /**
     * 求两个集合并集,相同元素取个数多的;
     * @param list1
     * @param list2
     * @return
     */
    public static List<Integer> getCommonNumbers(List<Integer> list1, List<Integer> list2){
        List<Integer> resList = new ArrayList<>();
        Map<Integer,Integer> resMap = new HashMap<>();
        Map<Integer,Integer> countMap = new HashMap<>();
        Map<Integer,Integer> countMap2 = new HashMap<>();
        for (Integer integer : list1) {
            if (countMap.containsKey(integer)) {
                countMap.put(integer,countMap.get(integer) + 1);
                continue;
            }
            countMap.put(integer,1);
        }
        for (Integer integer : list2) {
            if (countMap2.containsKey(integer)) {
                countMap2.put(integer,countMap2.get(integer) + 1);
                continue;
            }
            countMap2.put(integer,1);
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = countMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            Integer num = entry.getKey();
            Integer count = entry.getValue();
            if(countMap2.containsKey(num) && countMap2.get(num) > count) {
                resMap.put(num,countMap2.get(num));
                continue;
            }
            resMap.put(num,count);
        }
        Iterator<Map.Entry<Integer, Integer>> map2Iterator = countMap2.entrySet().iterator();
        while (map2Iterator.hasNext()) {
            Map.Entry<Integer, Integer> e = map2Iterator.next();
            Integer num = e.getKey();
            Integer count = e.getValue();
            if (!resMap.containsKey(num)) {
                resMap.put(num,count);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> resIterator = resMap.entrySet().iterator();
        while (resIterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = resIterator.next();
            Integer number = entry.getKey();
            Integer count = entry.getValue();
            for (Integer i = 0; i < count; i++) {
                resList.add(number);
            }
        }
        return resList;
    }
}
