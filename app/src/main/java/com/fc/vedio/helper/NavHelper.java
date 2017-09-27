package com.fc.vedio.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * @author 范超 on 2017/6/23
 * 完成对Fragment的调度与重用问题，
 * 达到最优的Fragment切换
 */

public class NavHelper<T> {
    //所有的Tab集合
    private final SparseArray<Tab<T>> tabs = new SparseArray<Tab<T>>();
    private final Context context;
    private final FragmentManager manager;
    private final int containerId;
    private OnTabChangedListener<T> listener;
    //当前选中的Tab
    private Tab<T> currentTab;

    public NavHelper(Context context, FragmentManager manager, int containerId, OnTabChangedListener<T> listener) {
        this.context = context;
        this.manager = manager;
        this.containerId = containerId;
        this.listener = listener;
    }

    //添加Tab
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        tabs.put(menuId, tab);
        return this;
    }

    //获取当前的显示的Tab
    public Tab<T> getCurrentTab() {
        return currentTab;
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId 菜单的ID
     * @return 是否能够处理
     */
    public boolean performClickMenu(int menuId) {
        Tab<T> tab = tabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        } else {
            return false;
        }
    }

    //进行真实的Tab选择操作
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (currentTab != null) {
            oldTab = currentTab;
            //如果当前的Tab就是点击的Tab，就刷新当前
            if (oldTab == tab) {
                notifyTabReselect(tab);
                return;
            }
        }
        currentTab = tab;
        doTabChanged(currentTab, oldTab);
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = manager.beginTransaction();
        if (oldTab != null && oldTab.fragment != null) {
            ft.detach(oldTab.fragment);
        }
        if (newTab != null) {
            if (newTab.fragment == null) {
                Fragment fragment = Fragment.instantiate(context, newTab.clz.getName(), null);
                newTab.fragment = fragment;
                ft.add(containerId, fragment, newTab.clz.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        ft.commit();
        notifyTabSelect(newTab, oldTab);
    }

    private void notifyTabSelect(Tab<T> newTab, Tab<T> oldTab) {
        if (listener != null) {
            listener.onTabChanged(newTab, oldTab);
        }
    }

    private void notifyTabReselect(Tab<T> tab) {
        //TODO:二次点击Tab所做的操作

    }

    /**
     * 所有的Tab基础属性
     *
     * @param <T>
     */
    public static class Tab<T> {
        //Fragment对应的信息
        public Class<?> clz;
        //额外的字段，用户自己设定 需要的东西
        public T extra;
        //内部缓存的Fragment
        Fragment fragment;

        public Tab(Class<?> clz, T extra) {
            this.clz = clz;
            this.extra = extra;
        }
    }

    /**
     * 定义事件处理完成后的回调接口
     *
     * @param <T>
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
