package com.xiaosuokeji.framework.android.xstabactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.xiaosuokeji.framework.android.xstabactivity.R.layout.tab;

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：陈佳润
 * -------------------------------------------------------------------------------------------------
 * 创建日期：17/3/22
 * -------------------------------------------------------------------------------------------------
 * 更新历史(日期/更新人/更新内容)
 */
public abstract class XSTabActivity extends AppCompatActivity {

    FragmentTabHost fragmentTabHost;

    /**
     * 自定义tab列表
     */
    List<View> tabViewList;

    /**
     * 自定义tab对象列表
     */
    List<XSTab> tabList;


    /**
     * 分为两步，第一步是初始化FragmentTabHost,第二步是初始化Tab
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xs_tab);

        //初始化FragmentTabHost
        initFragmentTabHost();

        //初始化tab
        initTabData();


        initContent();
    }


    /**
     * 初始化FragmentTabHost，绑定布局文件并设置内容区域
     */
    private void initFragmentTabHost() {
        // 获取FragmentTabHost
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.activity_xs_tab_layout_tab_host);

        //初始化 FragmentTabHost
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.activity_xs_tab_layout_main_content);
    }


    /**
     * 初始化Tab，过程依次为：创建菜单，设置背景变化，设置监听器，设置默认Tab
     */
    private void initTabData() {

        tabList = getXSTab();

        tabViewList = new ArrayList<View>();

        //逐个添加菜单
        for (int i = 0; i < tabList.size(); i++) {

            XSTab xsTab = tabList.get(i);

            //创建菜单
            TabHost.TabSpec spec = getTabSpec(xsTab);

            //添加菜单
            fragmentTabHost.addTab(spec, xsTab.getTabFragment(), null);

            //设置菜单选中与没选中的背景变化
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_selector);

            //为菜单添加监听器
            fragmentTabHost.getTabWidget().getChildAt(i).setOnClickListener(new OnTabClickListener(tabViewList.get(i), xsTab));

            //设置默认tab
            if (xsTab.isSel()) {
                fragmentTabHost.setCurrentTabByTag(getTabName(xsTab));
            }

        }

    }

    /**
     * @param xsTab
     * @return
     */
    private TabHost.TabSpec getTabSpec(XSTab xsTab) {

        String tabName = getTabName(xsTab);


        //创建菜单项
        View view = getTabView(xsTab);
        tabViewList.add(view);

        //创建菜单
        TabHost.TabSpec spec = fragmentTabHost.newTabSpec(tabName).setIndicator(view);


        return spec;
    }

    /**
     * 从XSTab中获取Tab名称，并进行空值、Null等校验,如果tab名称名称为空，则命名为 "tab"+XSTab对象的哈希值，便于区分
     *
     * @param xsTab
     * @return 菜单名称
     */
    private String getTabName(XSTab xsTab) {

        String tabName = "";

        //如果没有设置名称
        if (xsTab.getTabName() != 0) {

            tabName = getResources().getText(xsTab.getTabName()).toString();

        }

        //名称如果为空字符串，则默认为tab0、tab1...
        if ("".equals(tabName)) {

            tabName = "tab" + xsTab.hashCode();

        }

        return tabName;
    }


    public View getTabView(XSTab xsTab) {

        View view = null;

        view = View.inflate(this, tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iamge);
        TextView textView = (TextView) view.findViewById(R.id.tab_text);

        if (isShowIcon()) {

            imageView.setVisibility(View.VISIBLE);

            if (xsTab.isSel()) {
                imageView.setImageResource(xsTab.getTabIconSel());
            } else {
                imageView.setImageResource(xsTab.getTabIconNor());
            }

        } else {
            imageView.setVisibility(GONE);
        }


        if (isShowTitle()) {

            textView.setVisibility(View.VISIBLE);
            textView.setText(xsTab.getTabName());

            if (xsTab.isSel()) {

                textView.setTextColor(getResources().getColor(xsTab.getTabNameSel()));

            } else {

                textView.setTextColor(getResources().getColor(xsTab.getTabNameNor()));
            }

        } else {

            textView.setVisibility(GONE);
        }

        return view;
    }


    /**
     * 监听Tab的点击事件
     */
    public class OnTabClickListener implements View.OnClickListener {

        private View tabView;

        private XSTab tab;

        public OnTabClickListener(View tabView, XSTab tab) {
            this.tabView = tabView;
            this.tab = tab;
        }

        @Override
        public void onClick(View view) {

            fragmentTabHost.setCurrentTabByTag(getTabName(tab));

            //清除所有tab的选中状态
            clearAllSel();

            ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_iamge);
            TextView textView = (TextView) tabView.findViewById(R.id.tab_text);

            imageView.setImageResource(tab.getTabIconSel());
            textView.setTextColor(getResources().getColor(tab.getTabNameSel()));

            afterTabClick(tab);
        }
    }

    /**
     * 将所有Tab变为未选中状态
     */
    private void clearAllSel() {

        for (int i = 0; i < tabViewList.size(); i++) {

            View tabView = tabViewList.get(i);
            ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_iamge);
            TextView textView = (TextView) tabView.findViewById(R.id.tab_text);

            if (isShowIcon()) {

                imageView.setImageResource(tabList.get(i).getTabIconNor());

            }

            if (isShowTitle()) {


                textView.setTextColor(getResources().getColor(tabList.get(i).getTabNameNor()));
            }

        }

    }

    /**
     * 获取XSTab列表（菜单列表），由子类来定义所有菜单选项，并传给本类；
     *
     * @return
     */
    public abstract List<XSTab> getXSTab();

    /**
     * 是否显示菜单图标
     *
     * @return
     */
    public boolean isShowIcon() {
        return true;
    }

    /**
     * 是否显示菜单文字
     *
     * @return
     */
    public boolean isShowTitle() {
        return true;
    }

    /**
     * 菜单选中后回调
     *
     * @param xsTab
     */
    public abstract void afterTabClick(XSTab xsTab);


    public abstract void initContent();


    /**
     * 显示图标上的提示点
     */
    public void showTipPoint(XSTab xsTab) {
        int index = tabList.indexOf(xsTab);

        View view = tabViewList.get(index);

        LinearLayout layoutTipPoint = (LinearLayout) view.findViewById(R.id.tip_point);

        layoutTipPoint.setVisibility(View.VISIBLE);
    }

    /**
     * 显示图标上的提示点
     */
    public void showTipPointByName(int... tabNameList) {

        for (XSTab xsTab : tabList) {

            for (int tabName : tabNameList) {

                if (xsTab.getTabName() == tabName) {

                    int index = tabList.indexOf(xsTab);

                    View view = tabViewList.get(index);

                    LinearLayout layoutTipPoint = (LinearLayout) view.findViewById(R.id.tip_point);

                    layoutTipPoint.setVisibility(View.VISIBLE);
                }

            }
        }
    }


    /**
     * 隐藏图标上的提示点
     */
    public void hideTipPoint(XSTab xsTab) {
        int index = tabList.indexOf(xsTab);

        View view = tabViewList.get(index);

        LinearLayout layoutTipPoint = (LinearLayout) view.findViewById(R.id.tip_point);

        layoutTipPoint.setVisibility(View.GONE);
    }


}
