package edu.sharif.timesync.groupDetailedMenu.dashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ViewFlipper topFlipper;
    private ViewFlipper barChartFlipper;

    private HorizontalBarChart barChart;
    private ArrayList<BarEntry> barArrayList;

    private Spinner spinner;

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

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        Log.d("TAGTAG", "onViewCreated: " + sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserWeekly("ruzbeh"));

//        onItemSelected(null, view, 0, 0);

        barArrayList = new ArrayList<BarEntry>();

        if (sqlDatabaseManager.getGroupUserMappingDatabaseManager().isLoggedInUserAdminInCurrentGroup()) {
            topFlipper.setDisplayedChild(1);
        } else {
            topFlipper.setDisplayedChild(0);
        }
    }

    private void setUpWeekChart() {
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        HashMap<String, Integer> hashMap = sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserWeekly(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
        barArrayList = new ArrayList<BarEntry>();

        BarDataSet barDataSet = new BarDataSet(barArrayList, "Week Report");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }

    private void setUpJobsChart() {
        BarDataSet barDataSet = new BarDataSet(barArrayList, "Jobs Report");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        String text = spinner.getSelectedItem().toString();
//        if (text.equals("Week Report")) {
//            barChart = view.findViewById(R.id.weekChart);
//            setUpWeekChart();
//            barChartFlipper.setDisplayedChild(0);
//        } else {
//            barChart = view.findViewById(R.id.jobsChart);
//            setUpJobsChart();
//            barChartFlipper.setDisplayedChild(1);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}