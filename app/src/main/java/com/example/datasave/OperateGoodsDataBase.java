package com.example.datasave;
import android.content.Context;

/**
 * Created by leeyu on 2016/11/12.
 */
public class OperateGoodsDataBase implements GoodsDataBaseInterface{

    private static OperateGoodsDataBase instance  = new OperateGoodsDataBase();

    public static OperateGoodsDataBase getInstance(){
        return  instance;
    }

    private OperateGoodsDataBase(){

    }


    /**
     *添加和删除商品数量,并得到商品数量
     */
    @Override
    public int saveGoodsNumber(Context context, int menupos, int goodsid, String goodsnum , String goodsprice) {
        return OperateGoodsDataBaseStatic.saveGoodsNumber(context , menupos , goodsid , goodsnum ,goodsprice);
    }
    /**
     *根据下标得到 第二级对应购物的数量
     */
    @Override
    public int getSecondGoodsNumber(Context context, int menupos, int goodsid) {
        return OperateGoodsDataBaseStatic.getSecondGoodsNumber(context, menupos, goodsid);
    }
    /**
     * 根据第一级的下标 得到第二级的所有购物数量
     */
    @Override
    public int getSecondGoodsNumberAll(Context context, int menupos) {
        return OperateGoodsDataBaseStatic.getSecondGoodsNumberAll(context, menupos);
    }

    /**
     *据第一级的下标 得到第二级的所有购物的价格
     */
    @Override
    public int getSecondGoodsPriceAll(Context context, int menupos) {
        return OperateGoodsDataBaseStatic.getSecondGoodsPriceAll(context, menupos);
    }
    /**
     *删除所有的购物数据
     */
    @Override
    public void deleteAll(Context context) {
        OperateGoodsDataBaseStatic.deleteAll(context);
    }

    @Override
    public int getGoodsPriceAll(Context context) {
        int i=0,sum=0;
        while(i<DemoData.ListMenu_STYLE.length){
            sum+=getSecondGoodsPriceAll(context,i);
            i++;
        }
        return sum;
    }

    @Override
    public int getGoodsNumberAll(Context context) {
        int i=0,sum=0;
        while(i<DemoData.ListMenu_STYLE.length){
            sum+=getSecondGoodsNumberAll(context,i);
            i++;
        }
        return sum;
    }
}
