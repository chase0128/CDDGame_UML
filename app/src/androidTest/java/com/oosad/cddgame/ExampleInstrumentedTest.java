package com.oosad.cddgame;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DistributeCards();
        assertEquals("com.oosad.cddgame", appContext.getPackageName());
    }
    /**
     * 发牌，目前为随机算法，不考虑重复
     * @return
     */

    public void DistributeCards() {
        int cardnum[]=new int[52];
        for(int i=1;i<52;i++){
            cardnum[i]=i;
        }
        shuffle(cardnum);
        Log.d("Text",
                cardnum[1]+"");
    }

    /**
     * 打乱数组的顺序（用来打乱卡片的顺序)
     * @param arr
     */
    public  void shuffle(int []arr){
        Random random=new Random();
        for(int i=arr.length;i>0;i--){
            int randInt=random.nextInt(arr.length);
            swap(arr,randInt,i-1);
        }
    }
    /**
     * 交换数组中的两个数字
     * @param arr
     * @param i
     * @param j
     */
    public void swap(int[]arr,int i,int j){
        int k=arr[i];
        arr[i]=arr[j];
        arr[j]=k;
    }
}
