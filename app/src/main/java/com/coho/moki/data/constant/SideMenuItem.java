package com.coho.moki.data.constant;

import com.coho.moki.R;

import java.util.ArrayList;

/**
 * Created by trung on 11/10/2017.
 */

public class SideMenuItem {

    private int index;
    private int icon;
    private int title;

    public SideMenuItem(int index, int icon, int title) {
        this.index = index;
        this.icon = icon;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public static ArrayList<SideMenuItem> getListSideMenuItem(){
        ArrayList<SideMenuItem> sideMenuItems = new ArrayList<SideMenuItem>();
        sideMenuItems.add(new SideMenuItem(AppConstant.Home_MENU_INDEX, R.drawable.sidemenu_icon_store_normal, R.string.sidemenu_title_store));
        sideMenuItems.add(new SideMenuItem(1, R.drawable.sidemenu_icon_news_normal, R.string.sidemenu_title_news));
        sideMenuItems.add(new SideMenuItem(2, R.drawable.sidemenu_icon_like_normal, R.string.sidemenu_title_like));
        sideMenuItems.add(new SideMenuItem(3, R.drawable.sidemenu_icon_exhibit_normal, R.string.sidemenu_title_exhibit));
        sideMenuItems.add(new SideMenuItem(4, R.drawable.sidemenu_icon_buy_normal, R.string.sidemenu_title_buy));
        sideMenuItems.add(new SideMenuItem(5, R.drawable.sidemenu_icon_charity, R.string.sidemenu_title_charity));
        sideMenuItems.add(new SideMenuItem(6, R.drawable.sidemenu_icon_setting_normal, R.string.sidemenu_title_setting));
        sideMenuItems.add(new SideMenuItem(7, R.drawable.sidemenu_icon_contact_normal, R.string.sidemenu_title_contact));
        sideMenuItems.add(new SideMenuItem(8, R.drawable.sidemenu_icon_invite_normal, R.string.sidemenu_title_invite));
        sideMenuItems.add(new SideMenuItem(9, R.drawable.sidemenu_icon_logout_normal, R.string.sidemenu_title_logout));

        return sideMenuItems;
    }
}
