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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.sharif.timesync.R;
import edu.sharif.timesync.database.SQLDatabaseManager;

public class DashboardFragment extends Fragment {

    private View myView;

    private ViewFlipper barChartFlipper;
    private HorizontalBarChart barChart;
    private ArrayList<BarEntry> barArrayList;
    private Spinner userSpinner;
    private Spinner chartTypeSpinner;

    private String[] days = new String[]{"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

    private ArrayList<String> usernames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.myView = view;

        barChartFlipper = view.findViewById(R.id.barChartFlipper);

        usernames = new ArrayList<>();

        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());

        userSpinner = view.findViewById(R.id.users);

        if (!sqlDatabaseManager.getGroupUserMappingDatabaseManager().isLoggedInUserAdminInCurrentGroup()) {
            userSpinner.setVisibility(View.GONE);
        } else {
            userSpinner.setVisibility(View.VISIBLE);
            usernames = sqlDatabaseManager.getGroupUserMappingDatabaseManager().getCurrentGroupUsernames();
            usernames.remove(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, usernames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (chartTypeSpinner.getSelectedItem().toString().equals("Week Report")) {
                    barChartFlipper.setDisplayedChild(0);
                    barChart = myView.findViewById(R.id.weekChart);
                    setUpWeekChart(usernames.get(position));
                } else {
                    barChartFlipper.setDisplayedChild(1);
                    barChart = myView.findViewById(R.id.jobsChart);
                    setUpJobsChart(usernames.get(position));
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chartTypeSpinner = view.findViewById(R.id.chartType);
        ArrayAdapter<CharSequence> anotherAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(anotherAdapter);

        if (!sqlDatabaseManager.getGroupUserMappingDatabaseManager().isLoggedInUserAdminInCurrentGroup()) {
            chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 1) {
                        barChartFlipper.setDisplayedChild(0);
                        barChart = myView.findViewById(R.id.weekChart);
                        setUpWeekChart(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
                    } else {
                        barChartFlipper.setDisplayedChild(1);
                        barChart = myView.findViewById(R.id.jobsChart);
                        setUpJobsChart(sqlDatabaseManager.getUserDatabaseManager().getLoggedInUser().getUsername());
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 1) {
                        barChartFlipper.setDisplayedChild(0);
                        barChart = myView.findViewById(R.id.weekChart);
                        setUpWeekChart(userSpinner.getSelectedItem().toString());
                    } else {
                        barChartFlipper.setDisplayedChild(1);
                        barChart = myView.findViewById(R.id.jobsChart);
                        setUpJobsChart(userSpinner.getSelectedItem().toString());
                    }

                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        barArrayList = new ArrayList<BarEntry>();

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

    private void setUpWeekChart(String username) {
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        HashMap<String, Integer> hashMap = sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserWeekly(username);
        barArrayList.clear();
        ArrayList<Integer> arrayList = getWeekTimeInOrder(hashMap);
        barArrayList.add(new BarEntry(0, arrayList.get(0)));
        barArrayList.add(new BarEntry(1, arrayList.get(1)));
        barArrayList.add(new BarEntry(2, arrayList.get(2)));
        barArrayList.add(new BarEntry(3, arrayList.get(3)));
        barArrayList.add(new BarEntry(4, arrayList.get(4)));
        barArrayList.add(new BarEntry(5, arrayList.get(5)));
        barArrayList.add(new BarEntry(6, arrayList.get(6)));

        Log.d("gggggggggg", "setUpJobsChart: " + username + " " + barArrayList);


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

    private void setUpJobsChart(String username) {
        SQLDatabaseManager sqlDatabaseManager = SQLDatabaseManager.instanceOfDatabase(getContext());
        HashMap<String, Integer> hashMap = sqlDatabaseManager.getTimeSpentDatabaseManager().getTimeSpentByUserByJobName(username);
        barArrayList.clear();
        int i = 0;
        ArrayList<String> jobs = new ArrayList<>();
        for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
            barArrayList.add(new BarEntry(i, e.getValue()));
            i += 1;
            jobs.add(e.getKey());
        }



        Log.d("wtfffffffffffff", "setUpJobsChart: " + username + " " + jobs + " " + barArrayList);

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
}