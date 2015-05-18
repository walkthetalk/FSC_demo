package com.example.ll.fsc;

/**
 * Created by ll on 4/5/15.
 */
public class ToolbarInfo {
    public final int title;
    public final int subtitle;
    public final int icon;
    public final int menu;

    public ToolbarInfo(int titleResId, int subtitleResId, int iconResId, int menuResId) {
        title = titleResId;
        subtitle = subtitleResId;
        icon = iconResId;
        menu = menuResId;
    }

    public static final ToolbarInfo GLB_TOOLBAR_INFO = new ToolbarInfo(
            R.string.app_name,
            R.string.app_subtitle,
            R.drawable.ic_launcher,
            R.menu.global);
}
