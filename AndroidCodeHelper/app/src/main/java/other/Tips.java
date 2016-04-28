package other;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 2016/4/28.
 */
public class Tips {
    //遍历HashMap的最佳方法

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    //使用Java在一个区间内产生随机整数数
    public static int randInt(int min, int max) {
        Random rand=new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
