package other;

/**
 * Created by admin on 2016/4/29.
 */
public class SortArithmetic {
    //-------------------------------------------
    //冒泡排序
    //连续比较相邻元素，大于则交换，让大的往上浮，小的往下沉。
    //优化:只要有一趟没有任何交换，说明排序已经完成，不用继续再比较。
    //-------------------------------------------
    public int[] bubbleSort(int[] arr)
    {
        boolean needToNext=true;
        int temp=0;
        for(int i=1;i<arr.length&&needToNext;i++){
            needToNext=false;
            for(int j=0;j<arr.length-i;j++)
            {
                if(arr[j]>arr[j+1])
                {
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    needToNext=true;
                }
            }
        }
        return  arr;
    }
}
