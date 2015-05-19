package com.example.ll.fsc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.example.ll.fsc.parameterfile.ParameterFileListActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private int mSvcIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.fsc_toolbar);
        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mSvcIdx = 0;

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
    }

    public void onSectionAttached(int number) {
        mSvcIdx = number;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(FusionSpliceServiceInfo.FS_SVC_INFO[mSvcIdx].toolbarInfo.title);
        actionBar.setSubtitle(FusionSpliceServiceInfo.FS_SVC_INFO[mSvcIdx].toolbarInfo.subtitle);
        actionBar.setLogo(FusionSpliceServiceInfo.FS_SVC_INFO[mSvcIdx].toolbarInfo.icon);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setIconEnable(menu, true);

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(FusionSpliceServiceInfo.FS_SVC_INFO[mSvcIdx].toolbarInfo.menu, menu);
            getMenuInflater().inflate(ToolbarInfo.GLB_TOOLBAR_INFO.menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.fusion_splice_param:
                Intent intent = new Intent(this, ParameterFileListActivity.class);
                intent.putExtra("list_adapter", "fs_param_file");
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            LineChartView lcv = genChart(inflater.getContext());
            ((RelativeLayout) rootView).addView(lcv);
            genTimer(lcv);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        private void genTimer(final LineChartView mChart) {
            new CountDownTimer(100000, 1000) {
                int left = -10;
                int right = 0;
                int top = 15;
                int bottom = 0;
                final List<PointValue> pvs = ((LineChartData)mChart.getChartData()).getLines().get(0).getValues();
                public void onTick(long millisUntilFinished) {
                    this.onFinish();
                }

                public void onFinish() {
                    final PointValue newPoint = new PointValue(right, (float) Math.random() * 10);
                    pvs.add(newPoint);
                    ++right;

                    mChart.setLineChartData((LineChartData)mChart.getChartData());
                    if (right == 10) {
                        mChart.setViewportAnimatorInterpolator(new LinearInterpolator());
                        mChart.setCurrentViewportWithAnimation(new Viewport(110, top, 120, bottom), 120000);
                    }
                }
            }.start();
        }

        private LineChartView genChart(Context context) {
            LineChartView chart = new LineChartView(context);
            //chart.setInteractive(true);
            //chart.setZoomType(ZoomType.VERTICAL);
            //chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            //chart.setContainerScrollEnabled(false, ContainerScrollType.VERTICAL);
            //chart.setZoomEnabled(true);
            //chart.setScrollEnabled(true);
            chart.setViewportCalculationEnabled(false);
            chart.setMaximumViewport(new Viewport(-10, 10, 200, 0));

            List<PointValue> values = new ArrayList<PointValue>();
            values.add(new PointValue(0, 10));

            //In most cased you can call data model methods in builder-pattern-like manner.
            Line line = new Line(values).setColor(Color.BLUE).setCubic(true).setPointRadius(0);
            //line.setStrokeWidth(3);
            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            LineChartData data = new LineChartData();
            data.setLines(lines);

            Axis axisX = new Axis(); //X轴
            axisX.setHasTiltedLabels(true);
            axisX.setTextColor(Color.BLUE);
            axisX.setName("采集时间");
            axisX.setMaxLabelChars(3);
            final ArrayList<AxisValue> mAxisValues = new ArrayList<AxisValue>();
            for (int i = 0; i < 120; ++i) {
                mAxisValues.add(new AxisValue(i).setLabel(String.valueOf(i-10)));
            }
            //axisX.setValues(mAxisValues);
            data.setAxisXBottom(axisX);

            Axis axisY = new Axis();  //Y轴
            axisY.setMaxLabelChars(7); //默认是3，只能看最后三个数字
            data.setAxisYLeft(axisY);

            chart.setLineChartData(data);
            chart.setCurrentViewport(new Viewport(-10, 10, 0, 0));

            return chart;
        }
    }

    private void setIconEnable(Menu menu, boolean enable) {
        try {
            //Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Class<?> clazz = Class.forName("android.support.v7.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
