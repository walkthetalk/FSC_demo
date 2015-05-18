package com.example.ll.fsc;

/**
 * Created by ll on 4/5/15.
 */
public class FusionSpliceServiceInfo {
    public final ToolbarInfo toolbarInfo;

    public FusionSpliceServiceInfo(int titleResId, int subtitleResId, int iconResId, int menuResId) {
        toolbarInfo = new ToolbarInfo(titleResId, subtitleResId, iconResId, menuResId);
    }

    static FusionSpliceServiceInfo[] FS_SVC_INFO = new FusionSpliceServiceInfo[]{
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_fusion_splice_title,
                    R.string.fs_svc_fusion_splice_subtitle,
                    R.drawable.ic_fusion_splice,
                    R.menu.fs_svc_fusion_splice),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_heat_title,
                    R.string.fs_svc_heat_subtitle,
                    R.drawable.ic_heat,
                    R.menu.fs_svc_heat),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_arc_adjust_title,
                    R.string.fs_svc_arc_adjust_subtitle,
                    R.drawable.ic_arc_adjust,
                    R.menu.fs_svc_arc_adjust),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_real_time_adjust_title,
                    R.string.fs_svc_real_time_adjust_subtitle,
                    R.drawable.ic_realtime_adjust,
                    R.menu.fs_svc_realtime_adjust),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_regular_test_title,
                    R.string.fs_svc_regular_test_subtitle,
                    R.drawable.ic_regular_test,
                    R.menu.fs_svc_regular_test),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_regular_debug_title,
                    R.string.fs_svc_regular_debug_subtitle,
                    R.drawable.ic_regular_debug,
                    R.menu.fs_svc_regular_debug),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_dust_check_title,
                    R.string.fs_svc_dust_check_subtitle,
                    R.drawable.ic_dust_check,
                    R.menu.fs_svc_dust_check),
            new FusionSpliceServiceInfo(
                    R.string.fs_svc_full_dust_check_title,
                    R.string.fs_svc_full_dust_check_subtitle,
                    R.drawable.ic_full_dust_check,
                    R.menu.fs_svc_full_dust_check),
    };
}
