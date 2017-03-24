package com.xiaosuokeji.framework.android.xstabactivity;

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：陈佳润
 * -------------------------------------------------------------------------------------------------
 * 创建日期：17/3/22
 * -------------------------------------------------------------------------------------------------
 * 更新历史(日期/更新人/更新内容)
 */
public class XSTab {

    private int tabName;

    private int tabNameNor;

    private int tabNameSel;

    private int tabIconNor;

    private int tabIconSel;

    private Class tabFragment;

    private boolean isSel = false;

    public XSTab(int tabName, int tabIconNor, int tabIconSel, Class tabFragment) {
        this(tabName, R.color.colorTabNameNor, R.color.colorTabNameSel, tabIconNor, tabIconSel, tabFragment, false);
    }

    public XSTab(int tabName, int tabIconNor, int tabIconSel, Class tabFragment, boolean isSel) {
        this(tabName, R.color.colorTabNameNor, R.color.colorTabNameSel, tabIconNor, tabIconSel, tabFragment, isSel);
    }

    public XSTab(int tabName, int tabNameNor, int tabNameSel, int tabIconNor, int tabIconSel, Class tabFragment, boolean isSel) {
        this.tabName = tabName;
        this.tabNameNor = tabNameNor;
        this.tabNameSel = tabNameSel;
        this.tabIconNor = tabIconNor;
        this.tabIconSel = tabIconSel;
        this.tabFragment = tabFragment;
        this.isSel = isSel;
    }

    public int getTabNameNor() {
        return tabNameNor;
    }

    public void setTabNameNor(int tabNameNor) {
        this.tabNameNor = tabNameNor;
    }

    public int getTabNameSel() {
        return tabNameSel;
    }

    public void setTabNameSel(int tabNameSel) {
        this.tabNameSel = tabNameSel;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public int getTabName() {
        return tabName;
    }

    public void setTabName(int tabName) {
        this.tabName = tabName;
    }

    public int getTabIconNor() {
        return tabIconNor;
    }

    public void setTabIconNor(int tabIconNor) {
        this.tabIconNor = tabIconNor;
    }

    public int getTabIconSel() {
        return tabIconSel;
    }

    public void setTabIconSel(int tabIconSel) {
        this.tabIconSel = tabIconSel;
    }

    public Class getTabFragment() {
        return tabFragment;
    }

    public void setTabFragment(Class tabFragment) {
        this.tabFragment = tabFragment;
    }

}
