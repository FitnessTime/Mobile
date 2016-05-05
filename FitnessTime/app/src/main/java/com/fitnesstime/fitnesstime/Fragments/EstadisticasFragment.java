package com.fitnesstime.fitnesstime.Fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class EstadisticasFragment extends Fragment {

    private View rootView;

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        /*BarChart chart = (BarChart) rootView.findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataset = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarData data = new BarData(labels, dataset);

        chart.setData(data);
        */
        webView = (WebView) rootView.findViewById(R.id.web_view);
        String GraphURL = "http://chart.googleapis.com/chart?chxr=0,2010,2013|1,100,1300" +
                "&chxs=0,676767,8.5,0,l,676767&chxt=x,y&chbh=a,3,80&chs=1000x300" +
                "&cht=bvg&chco=3366CC,FF0000&chds=0,1170,0,1120" +
                "&chd=t:1000,1170,660,1030|400,460,1120,540&chdl=Sales|Expenses" +
                "&chg=0,-1&chtt=Truiton%27s+Performance";
        webView.loadUrl(GraphURL);
        return rootView;
    }
}
