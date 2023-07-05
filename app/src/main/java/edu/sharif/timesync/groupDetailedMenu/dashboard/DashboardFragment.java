package edu.sharif.timesync.groupDetailedMenu.dashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;

public class DashboardFragment extends Fragment {

    private ViewFlipper topFlipper;
    private ViewFlipper barChartFlipper;

    private HorizontalBarChart barChart;

    private ArrayList<BarEntry> barArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topFlipper = view.findViewById(R.id.topFlipper);
        barChartFlipper = view.findViewById(R.id.barChartFlipper);

        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        if (sqlDatabaseManager.getGroupUserMappingDatabaseManager().isLoggedInUserAdminInCurrentGroup()) {

        } else {

        }
    }

    private void setUpWeekChart() {
        //        setting up
//        set data
        BarDataSet barDataSet = new BarDataSet(barArrayList, "fake");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

//        setting data
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }

    private void setUpJobsChart() {

    }


}