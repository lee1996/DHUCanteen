package com.example.customshoppingcardemo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.animutils.GoodsAnimUtil;


import com.example.datasave.DemoData;
import com.example.datasave.GoodsDataBaseInterface;
import com.example.datasave.OperateGoodsDataBase;
import com.example.person.PersonActivity;
import com.example.recycler.DividerItemDecoration;
import com.example.recycler.FirstCanteenFirstFloorAdapter;
import com.example.recycler.FirstCanteenSecondFloorAdapter;
import com.example.recycler.RecyclerViewContentAdapter;
import com.example.recycler.RecyclerViewMenuAdapter;
import com.example.recycler.SecondCanteenFirstFloorAdapter;
import com.example.recycler.SecondCanteenSecondFloorAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {
    /**
     * 标题
     */
    @InjectView(R.id.m_fml_title_tv)
    TextView mTitle;
    /**
     * 侧边栏菜单RecyclerView
     */
    @InjectView(R.id.m_list_menu)
    RecyclerView mRecyclerMenu;
    /**
     * 内容RecyclerView
     */
    @InjectView(R.id.ff)
    RecyclerView m_ff;
    @InjectView(R.id.fs)
    RecyclerView m_fs;
    @InjectView(R.id.sf)
    RecyclerView m_sf;
    @InjectView(R.id.ss)
    RecyclerView m_ss;
    /**
     * 商品总价
     */
    @InjectView(R.id.m_list_all_price)
    TextView mListAllPrice;
    /**
     * 图片按钮跳转到用户信息界面
     */
    @InjectView(R.id.user)
    ImageView user;
    /**
     * 物品总数量
     */
    @InjectView(R.id.m_list_num)
    TextView mListAllNum;
    /**
     * 侧边栏菜单数据填充器
     */
    private RecyclerViewMenuAdapter mRecyclerViewMenuCommonadapter = null;
    /**
     * 内容数据填充器
     */
    private RecyclerViewContentAdapter mRecyclerViewContentCommonadapter = null;
    private FirstCanteenFirstFloorAdapter mFirstCanteenFirstFloorAdapter=null;
    private FirstCanteenSecondFloorAdapter mFirstCanteenSecondFloorAdapter=null;
    private SecondCanteenFirstFloorAdapter mSecondCanteenFirstFloorAdapter=null;
    private SecondCanteenSecondFloorAdapter mSecondCanteenSecondFloorAdapter=null;
    private Context mContext;
    /**
     * 存储数据list
     */
    private List<String> firstcanteenfirstfloor=new ArrayList<String>();
    private List<String> firstcanteensecondfloor=new ArrayList<String>();
    private List<String> secondcanteenfirstfloor=new ArrayList<String>();
    private List<String> secondcanteensecondfloor=new ArrayList<String>();
    private List<String> stringMenuList = new ArrayList<String>();
    private List<String> stringContentList = new ArrayList<String>();
    public static int SELECTPOSITION = 0;
    /**
     * 数据操作接口
     */
    GoodsDataBaseInterface mGoodsDataBaseInterface = null;
    /**
     * 购物车布局
     */
    @InjectView(R.id.m_list_car_lay)
    RelativeLayout mCarLay;
    /**
     * 所需热量
     */
    @InjectView(R.id.need)
    TextView need;
    /**
     * 数据库的初始化
     */
    private SharedPreferences preferences;
    static int sum=0;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(MainActivity.this);
        mContext = this;
        initView();
        setRecyclerView();
        initHttp();
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);

            }
        });
        preferences=getSharedPreferences("needCal",MODE_PRIVATE);
        float init=(float)12.3;
        float needcal=preferences.getFloat("need",init);
        need.setText(String.valueOf(needcal));
    }
    private void initView() {
        mGoodsDataBaseInterface = OperateGoodsDataBase.getInstance();
        //清空数据库缓存
        mGoodsDataBaseInterface.deleteAll(mContext);
        mTitle.setText("DHU HEAT");
    }
    /**
     * 模拟网络请求数据
     */
    private void initHttp() {
        for (int i = 0; i < 4; i++) {
            stringMenuList.add("1111");
        }
        for (int i=0;i<3;i++){
            firstcanteenfirstfloor.add("3333");
        }
        for (int i = 0; i < 3; i++) {
            firstcanteensecondfloor.add("2222");
        }
        for (int i = 0; i < 3; i++) {
            secondcanteenfirstfloor.add("2222");
        }
        for (int i = 0; i < 3; i++) {
            secondcanteensecondfloor.add("2222");
        }
        setMenuCommonadapter();
        setFirstCanteenFirstFloor();
        setFirstCanteenSecondFloor();
        setSecondCanteenFirstFloor();
        setSecondCanteenSecondFloor();
    }
    //@OnClick({/*R.id.m_fml_title_back,*/ R.id.m_list_submit})
    //void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.m_fml_title_back:
                finish();
                break;
            case R.id.m_list_submit:
                Toast.makeText(this, "确定" ,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }*/
       // Toast.makeText(this,"确定",Toast.LENGTH_SHORT).show();
   // }
    /**
     * 设置RecyclerView的布局方式
     */
    private void setRecyclerView() {
        //垂直listview显示方式
        mRecyclerMenu.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerMenu.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        m_ff.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        m_fs.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        m_sf.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        m_ss.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        m_ff.setVisibility(View.VISIBLE);
    }
    /**
     * 菜单列表    数据填充
     */
    private void setMenuCommonadapter() {
        mRecyclerViewMenuCommonadapter = new RecyclerViewMenuAdapter(mContext, stringMenuList);
        mRecyclerMenu.setAdapter(mRecyclerViewMenuCommonadapter);
        mRecyclerViewMenuCommonadapter.setOnItemClickListener(new RecyclerViewMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                SELECTPOSITION = position;
                Log.e("TAG", "SELECTPOSITION:" + SELECTPOSITION);
                //if(position==2)
                  //  Toast.makeText(com.example.customshop pingcardemo.MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
                mRecyclerViewMenuCommonadapter.notifyDataSetChanged();
                if(position==0){
                    mFirstCanteenFirstFloorAdapter.notifyDataSetChanged();
                    m_ff.setVisibility(View.VISIBLE);
                    m_fs.setVisibility(View.GONE);
                    m_sf.setVisibility(View.GONE);
                    m_ss.setVisibility(View.GONE);
                }else if(position==1) {
                    mFirstCanteenSecondFloorAdapter.notifyDataSetChanged();
                    m_ff.setVisibility(View.GONE);
                    m_fs.setVisibility(View.VISIBLE);
                    m_sf.setVisibility(View.GONE);
                    m_ss.setVisibility(View.GONE);
                }else if(position==2){
                    mSecondCanteenFirstFloorAdapter.notifyDataSetChanged();
                    m_ff.setVisibility(View.GONE);
                    m_fs.setVisibility(View.GONE);
                    m_sf.setVisibility(View.VISIBLE);
                    m_ss.setVisibility(View.GONE);
                }else if(position==3){
                    mSecondCanteenSecondFloorAdapter.notifyDataSetChanged();
                    m_ff.setVisibility(View.GONE);
                    m_fs.setVisibility(View.GONE);
                    m_sf.setVisibility(View.GONE);
                    m_ss.setVisibility(View.VISIBLE);
                }
               // setAll();
            }
            @Override
            public void onItemLongClick(View v, int position) {}
        });
    }
//    /**
//     * 商品种类列表    数据填充
//     */
//    private void setContentCommonadapter() {
//        mRecyclerViewContentCommonadapter = new RecyclerViewContentAdapter(mContext, stringContentList);
//        m_ss.setAdapter(mRecyclerViewContentCommonadapter);
//        m_fs.setAdapter(mRecyclerViewContentCommonadapter);
//        m_sf.setAdapter(mRecyclerViewContentCommonadapter);
//        mRecyclerViewContentCommonadapter.setOnItemClickListener(new MyCommonOnItemClickListener());
//    }
    /*
    一食一楼商品列表 数据填充
     */
    private void setFirstCanteenFirstFloor(){
        mFirstCanteenFirstFloorAdapter=new FirstCanteenFirstFloorAdapter(mContext,firstcanteenfirstfloor);
        m_ff.setAdapter(mFirstCanteenFirstFloorAdapter);
        mFirstCanteenFirstFloorAdapter.setOnItemClickListener(new FFListener());
    }
    /*
    一食二楼商品列表 数据填充
     */
    private void setFirstCanteenSecondFloor(){
        mFirstCanteenSecondFloorAdapter=new FirstCanteenSecondFloorAdapter(mContext,firstcanteensecondfloor);
        m_fs.setAdapter(mFirstCanteenSecondFloorAdapter);
        mFirstCanteenSecondFloorAdapter.setOnItemClickListener(new FSListener());
    }
    /*
      二食一楼商品列表 数据填充
   */
    private void setSecondCanteenFirstFloor(){
        mSecondCanteenFirstFloorAdapter=new SecondCanteenFirstFloorAdapter(mContext,secondcanteenfirstfloor);
        m_sf.setAdapter(mSecondCanteenFirstFloorAdapter);
        mSecondCanteenFirstFloorAdapter.setOnItemClickListener(new SFListener());
    }
    /*
   二食二楼商品列表 数据填充
*/
    private void setSecondCanteenSecondFloor(){
        mSecondCanteenSecondFloorAdapter=new SecondCanteenSecondFloorAdapter(mContext,secondcanteensecondfloor);
        m_ss.setAdapter(mSecondCanteenSecondFloorAdapter);
        mSecondCanteenSecondFloorAdapter.setOnItemClickListener(new SSListener());
    }
    /*
    一食一楼的监听器
     */
    class FFListener implements  FirstCanteenFirstFloorAdapter.OnItemClickListener {
        @Override
        public void onItemClick(FirstCanteenFirstFloorAdapter.ViewHolder holder) {
        }
        @Override
        public void onItemLongClick(FirstCanteenFirstFloorAdapter.ViewHolder holder) {
        }
        /**
         * 添加
         */
        @Override
        public void onItemJiaClick(FirstCanteenFirstFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            /** 点击加号之前还没有数据的时候 */
            if (numText.isEmpty() || numText.equals("0")) {
                // Log.e("TAG", "点击获取信息：SELECTPOSITION--" + SELECTPOSITION + "  DemoData.ListMenu_GOODSID[position]--" + DemoData.ListMenu_GOODSID[holder.getPosition()]);
                holder.mImgJian.setVisibility(View.VISIBLE);
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FFID[holder.getPosition()], "1", DemoData.FFPrice[holder.getPosition()]) + "");
                holder.mNumber.setVisibility(View.VISIBLE);
            }/** 点击加号之前有数据的时候 */
            else {
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FFID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) + 1), DemoData.FFPrice[holder.getPosition()]) + "");
            }
            /** 动画 */
            GoodsAnimUtil.setAnim(MainActivity.this, holder.mImgJia, mCarLay);
            GoodsAnimUtil.setOnEndAnimListener(new onEndAnim());
            /** 统计购物总数和购物总价 */
        }
        /**
         * 减少
         */
        @Override
        public void onItemJianClick(FirstCanteenFirstFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FFID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) - 1), DemoData.FFPrice[holder.getPosition()]) + "");
            numText = holder.mNumber.getText().toString().trim();
            /** 减完之后  数据为0 */
            if (numText.equals("0")) {
                holder.mNumber.setVisibility(View.GONE);
                holder.mImgJian.setVisibility(View.GONE);
            }
            setAll();
        }
    }
    /*
    一食二楼的监听器
     */
    class FSListener implements  FirstCanteenSecondFloorAdapter.OnItemClickListener {
        @Override
        public void onItemClick(FirstCanteenSecondFloorAdapter.ViewHolder holder) {
        }
        @Override
        public void onItemLongClick(FirstCanteenSecondFloorAdapter.ViewHolder holder) {
        }
        /**
         * 添加
         */
        @Override
        public void onItemJiaClick(FirstCanteenSecondFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            /** 点击加号之前还没有数据的时候 */
            if (numText.isEmpty() || numText.equals("0")) {
                // Log.e("TAG", "点击获取信息：SELECTPOSITION--" + SELECTPOSITION + "  DemoData.ListMenu_GOODSID[position]--" + DemoData.ListMenu_GOODSID[holder.getPosition()]);
                holder.mImgJian.setVisibility(View.VISIBLE);
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FSID[holder.getPosition()], "1", DemoData.FSPrice[holder.getPosition()]) + "");
                holder.mNumber.setVisibility(View.VISIBLE);
            }/** 点击加号之前有数据的时候 */
            else {
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) + 1), DemoData.FSPrice[holder.getPosition()]) + "");
            }
            /** 动画 */
            GoodsAnimUtil.setAnim(MainActivity.this, holder.mImgJia, mCarLay);
            GoodsAnimUtil.setOnEndAnimListener(new onEndAnim());
            /** 统计购物总数和购物总价 */
        }
        /**
         * 减少
         */
        @Override
        public void onItemJianClick(FirstCanteenSecondFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.FSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) - 1), DemoData.FSPrice[holder.getPosition()]) + "");
            numText = holder.mNumber.getText().toString().trim();
            /** 减完之后  数据为0 */
            if (numText.equals("0")) {
                holder.mNumber.setVisibility(View.GONE);
                holder.mImgJian.setVisibility(View.GONE);
            }
            setAll();
        }
    }
    /*
    二食一楼的监听器
     */
    class SFListener implements  SecondCanteenFirstFloorAdapter.OnItemClickListener {
        @Override
        public void onItemClick(SecondCanteenFirstFloorAdapter.ViewHolder holder) {
        }
        @Override
        public void onItemLongClick(SecondCanteenFirstFloorAdapter.ViewHolder holder) {
        }
        /**
         * 添加
         */
        @Override
        public void onItemJiaClick(SecondCanteenFirstFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            /** 点击加号之前还没有数据的时候 */
            if (numText.isEmpty() || numText.equals("0")) {
                // Log.e("TAG", "点击获取信息：SELECTPOSITION--" + SELECTPOSITION + "  DemoData.ListMenu_GOODSID[position]--" + DemoData.ListMenu_GOODSID[holder.getPosition()]);
                holder.mImgJian.setVisibility(View.VISIBLE);
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SFID[holder.getPosition()], "1", DemoData.SFPrice[holder.getPosition()]) + "");
                holder.mNumber.setVisibility(View.VISIBLE);
            }/** 点击加号之前有数据的时候 */
            else {
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SFID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) + 1), DemoData.SFPrice[holder.getPosition()]) + "");
            }
            /** 动画 */
            GoodsAnimUtil.setAnim(MainActivity.this, holder.mImgJia, mCarLay);
            GoodsAnimUtil.setOnEndAnimListener(new onEndAnim());
            /** 统计购物总数和购物总价 */
        }
        /**
         * 减少
         */
        @Override
        public void onItemJianClick(SecondCanteenFirstFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SFID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) - 1), DemoData.SFPrice[holder.getPosition()]) + "");
            numText = holder.mNumber.getText().toString().trim();
            /** 减完之后  数据为0 */
            if (numText.equals("0")) {
                holder.mNumber.setVisibility(View.GONE);
                holder.mImgJian.setVisibility(View.GONE);
            }
            setAll();
        }
    }
    /*
    二食二楼的监听器
     */
    class SSListener implements  SecondCanteenSecondFloorAdapter.OnItemClickListener {
        @Override
        public void onItemClick(SecondCanteenSecondFloorAdapter.ViewHolder holder) {
        }
        @Override
        public void onItemLongClick(SecondCanteenSecondFloorAdapter.ViewHolder holder) {
        }
        /**
         * 添加
         */
        @Override
        public void onItemJiaClick(SecondCanteenSecondFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            /** 点击加号之前还没有数据的时候 */
            if (numText.isEmpty() || numText.equals("0")) {
                // Log.e("TAG", "点击获取信息：SELECTPOSITION--" + SELECTPOSITION + "  DemoData.ListMenu_GOODSID[position]--" + DemoData.ListMenu_GOODSID[holder.getPosition()]);
                holder.mImgJian.setVisibility(View.VISIBLE);
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SSID[holder.getPosition()], "1", DemoData.SSPrice[holder.getPosition()]) + "");
                holder.mNumber.setVisibility(View.VISIBLE);
            }/** 点击加号之前有数据的时候 */
            else {
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) + 1), DemoData.SSPrice[holder.getPosition()]) + "");
            }
            /** 动画 */
            GoodsAnimUtil.setAnim(MainActivity.this, holder.mImgJia, mCarLay);
            GoodsAnimUtil.setOnEndAnimListener(new onEndAnim());
            /** 统计购物总数和购物总价 */
        }
        /**
         * 减少
         */
        @Override
        public void onItemJianClick(SecondCanteenSecondFloorAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.SSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) - 1), DemoData.SSPrice[holder.getPosition()]) + "");
            numText = holder.mNumber.getText().toString().trim();
            /** 减完之后  数据为0 */
            if (numText.equals("0")) {
                holder.mNumber.setVisibility(View.GONE);
                holder.mImgJian.setVisibility(View.GONE);
            }
            setAll();
        }
    }
    /*
    定义自己的事件监听器
     */
    class MyCommonOnItemClickListener implements RecyclerViewContentAdapter.OnItemClickListener {
        @Override
        public void onItemClick(RecyclerViewContentAdapter.ViewHolder holder) {}
        @Override
        public void onItemLongClick(RecyclerViewContentAdapter.ViewHolder holder) {}
        /** 添加 */
        @Override
        public void onItemJiaClick(RecyclerViewContentAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            /** 点击加号之前还没有数据的时候 */
            if (numText.isEmpty() || numText.equals("0")) {
                Log.e("TAG", "点击获取信息：SELECTPOSITION--" + SELECTPOSITION + "  DemoData.ListMenu_GOODSID[position]--" + DemoData.ListMenu_GOODSID[holder.getPosition()]);
                holder.mImgJian.setVisibility(View.VISIBLE);
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.ListMenu_GOODSID[holder.getPosition()], "1", DemoData.ListMenu_PPRICE[holder.getPosition()]) + "");
                holder.mNumber.setVisibility(View.VISIBLE);
            }/** 点击加号之前有数据的时候 */
            else {
                holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.ListMenu_GOODSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) + 1), DemoData.ListMenu_PPRICE[holder.getPosition()]) + "");
            }
            /** 动画 */
            GoodsAnimUtil.setAnim(MainActivity.this, holder.mImgJia, mCarLay);
            GoodsAnimUtil.setOnEndAnimListener(new onEndAnim());
            /** 统计购物总数和购物总价 */
        }
        /** 减少 */
        @Override
        public void onItemJianClick(RecyclerViewContentAdapter.ViewHolder holder) {
            String numText = holder.mNumber.getText().toString().trim();
            holder.mNumber.setText(mGoodsDataBaseInterface.saveGoodsNumber(mContext, SELECTPOSITION, DemoData.ListMenu_GOODSID[holder.getPosition()], String.valueOf(Integer.parseInt(numText) - 1), DemoData.ListMenu_PPRICE[holder.getPosition()]) + "");
            numText = holder.mNumber.getText().toString().trim();
            /** 减完之后  数据为0 */
            if (numText.equals("0")) {
                holder.mNumber.setVisibility(View.GONE);
                holder.mImgJian.setVisibility(View.GONE);
            }
            setAll();
        }
    }
    /**
     * 动画结束后，更新所有数量和所有价格
     */
    class onEndAnim implements GoodsAnimUtil.OnEndAnimListener {
        @Override
        public void onEndAnim() {
            setAll();
        }
    }
    /**
     * 点击加号和减号的时候设置总数和总价格
     */
    private void setAll() {
        //设置所有购物数量
        if (mGoodsDataBaseInterface.getGoodsNumberAll(mContext) == 0) {
            mListAllNum.setVisibility(View.GONE);
            mListAllPrice.setText("热量:0");
            mListAllNum.setText("");
        } else {
            //sum+=mGoodsDataBaseInterface.getSecondGoodsPriceAll(mContext,SELECTPOSITION);
            mListAllPrice.setText("热量:" + mGoodsDataBaseInterface.getGoodsPriceAll(mContext) + "");
            mListAllNum.setText(mGoodsDataBaseInterface.getGoodsNumberAll(mContext) + "");
            mListAllNum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        overridePendingTransition(0,R.anim.off);
        //finish();

    }
    @Override
    protected void onPause() {
        super.onPause();
       // overridePendingTransition(R.anim.leftin,R.anim.rightout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        need.postInvalidate();
    }

}