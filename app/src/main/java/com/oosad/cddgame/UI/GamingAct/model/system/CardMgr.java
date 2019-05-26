package com.oosad.cddgame.UI.GamingAct.model.system;

import com.oosad.cddgame.UI.GamingAct.model.Card;
import com.oosad.cddgame.UI.GamingAct.model.CardSuit;

import java.util.Arrays;
import java.util.Random;

public class CardMgr {
    private Card[] robot_1_CardSet=new Card[13];
    private   Card[] robot_2_CardSet=new Card[13];
    private Card [] robot_3_CardSet=new Card[13];
    private Card[] player_CardSet=new Card[13];

    private static volatile CardMgr cardMgr=null;
    private void CardMgr(){

    }
    public static CardMgr getInstance(){
        if(cardMgr==null) {
            synchronized (CardMgr.class){
                if(cardMgr==null){
                    cardMgr=new CardMgr();
                }
            }
        }
        return cardMgr;
    }


    /**
     * 发牌，目前为随机算法
     * @return
     */

    public void DistributeCards() {
            int cardnum[]=new int[52];
            for(int i=0;i<52;i++){
                cardnum[i]=i;
            }
            shuffle(cardnum);
       DistributeCardsToOne(robot_1_CardSet,cardnum,0);
        DistributeCardsToOne(robot_2_CardSet,cardnum,13);
        DistributeCardsToOne(robot_3_CardSet,cardnum,26);
       DistributeCardsToOne(player_CardSet,cardnum,39);

    }

    /**
     * 52张牌中分13张牌给其中一个玩家
     * @param cards
     * @param arr
     * @param i
     */
    public void DistributeCardsToOne(Card[] cards,int []arr,int i){
        int j=i;
        for(int k=0;k<cards.length;k++){
            cards[k]=new Card((arr[j]/4)+1,CardSuit.values()[arr[j]%4]);
            j++;
        }
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

    /**
     * 获取玩家的牌
     * @return
     */
    public Card[] Get_Player_Cards(){
        Arrays.sort(player_CardSet);
        return  player_CardSet;
    }

    /**
     * 获取机器人1的牌
     * @return
     */
    public  Card[] Get_Robot_1_Cards(){
        Arrays.sort(robot_1_CardSet);
        return robot_1_CardSet;
    }

    /**
     * 获取机器人2的牌
     * @return
     */
    public  Card[] Get_Robot_2_Cards(){
        Arrays.sort(robot_2_CardSet);
        return  robot_2_CardSet;
    }

    /**
     * 获取机器3的牌
     * @return
     */
    public  Card[] Get_Robot_3_Cards(){
        Arrays.sort(robot_3_CardSet);
        return robot_3_CardSet;
    }
}
