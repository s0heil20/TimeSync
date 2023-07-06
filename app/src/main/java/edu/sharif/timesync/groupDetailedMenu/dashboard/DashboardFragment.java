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

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private ViewFlipper topFlipper;
    private ViewFlipper barChartFlipper;

    private HorizontalBarChart barChart;
    private ArrayList<BarEntry> barArrayList;

    private Spinner spinner;

    private View view;

    private String[] days = new String[]{"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        topFlipper = view.findViewById(R.id.topFlipper);
        barChartFlipper = view.findViewById(R.id.barChartFlipper);

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());

        barArrayList = new ArrayList<BarEntry>();
        onItemSelected(null, view, 0, 0);

        if (sqlDatabaseManager.getGroupUserMappingDatabaseManager().isLoggedInUserAdminInCurrentGroup()) {
            topFlipper.setDisplayedChild(1);
        } else {
            topFlipper.setDisplayedChild(0);
        }
    }

    private ArrayList<Integer> getWeekTimeInOrder(HashMap<String, Integer> hashMap) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(hashMap.get("Saturday") == null ? Integer.valueOf(0) : hashMap.get("Saturday"));
        arrayList.add(hashMap.get("Sunday") == null ? Integer.valueOf(0) : hashMap.get("Sunday"));
        arrayList.add(hashMap.get("Monday") == null ? Integer.valueOf(0) : hashMap.get("Monday"));
        arrayList.add(hashMap.get("Tuesday") == null ? Integer.valueOf(0) : hashMap.get("Tuesday"));
        arrayList.add(hashMap.get("Wednesday") == null ? Integer.valueOf(0) : hashMap.get("Wednesday"));
        arrayList.add(hashMap.get("Thursday") == null ? Integer.valueOf(0) : hashMap.get("Thursday"));
        arrayList.add(hashMap.get("Friday") == null ? Integer.valueOf(0) : hashMap.get("Friday"));
        return arrayList;
    }

    private void setUpWeekChart() {
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        HashMap<String, Integer> hashMap = sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserWeekly(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
        barArrayList.clear();
        ArrayList<Integer> arrayList = getWeekTimeInOrder(hashMap);
        barArrayList.add(new BarEntry(0, arrayList.get(0)));
        barArrayList.add(new BarEntry(1, arrayList.get(1)));
        barArrayList.add(new BarEntry(2, arrayList.get(2)));
        barArrayList.add(new BarEntry(3, arrayList.get(3)));
        barArrayList.add(new BarEntry(4, arrayList.get(4)));
        barArrayList.add(new BarEntry(5, arrayList.get(5)));
        barArrayList.add(new BarEntry(6, arrayList.get(6)));

        Log.d("gggggggggg", "setUpJobsChart: " + barArrayList);


        BarDataSet barDataSet = new BarDataSet(barArrayList, "Week Report");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(days));
        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getLegend().setEnabled(false);   // Hide the legend
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
    }

    private void setUpJobsChart() {
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        HashMap<String, Integer> hashMap = sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserByJobName(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
        barArrayList.clear();
        int i = 0;
        ArrayList<String> jobs = new ArrayList<>();
        for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
            barArrayList.add(new BarEntry(i, e.getValue()));
            i += 1;
            jobs.add(e.getKey());
        }



        Log.d("wtfffffffffffff", "setUpJobsChart: " + jobs + " " + barArrayList);

        BarDataSet barDataSet = new BarDataSet(barArrayList, "Jobs Report");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        barChart.getXAxis().setLabelCount(jobs.size());
        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(jobs));
        barChart.getDescription().setEnabled(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getLegend().setEnabled(false);   // Hide the legend
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        Log.d("tttttttttttttttttt", "setUpJobsChart: " + barChart.getXAxis().getLabelCount());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 1) {
            barChartFlipper.setDisplayedChild(0);
            barChart = this.view.findViewById(R.id.weekChart);
            setUpWeekChart();
        } else {
            barChartFlipper.setDisplayedChild(1);
            barChart = this.view.findViewById(R.id.jobsChart);
            setUpJobsChart();
            Log.d("xxxxxxxxxxxxx", "onItemSelected: " + barChart);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}