package com.launguagelearning;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.launguagelearning.R;
import com.launguagelearning.model.ResultModel;

import java.util.ArrayList;
import java.util.List;

public class GetResultAdapter extends BaseAdapter {
    List<ResultModel> ar;
    Context cnt;

    public GetResultAdapter(List<ResultModel> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.list_get_result, null);

        TextView tvCtype = (TextView) obj2.findViewById(R.id.tvCtype);
        tvCtype.setText("Category :" + ar.get(pos).getCtype());

        TextView tvTotal = (TextView) obj2.findViewById(R.id.tvTotal);
        tvTotal.setText("Total :" + ar.get(pos).getTotal());

        TextView tvLevel = (TextView) obj2.findViewById(R.id.tvLevel);
        tvLevel.setText("Level :" + ar.get(pos).getLevel());

        TextView tvCorrect = (TextView) obj2.findViewById(R.id.tvCorrect);
        tvCorrect.setText("Correct :" + ar.get(pos).getCorrect());

        TextView tvWrong = (TextView) obj2.findViewById(R.id.tvWrong);
        tvWrong.setText("Wrong :" + ar.get(pos).getWrong());
        PieChart piechart = (PieChart)obj2.findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new Entry(Float.parseFloat(ar.get(pos).getCorrect()), 0));
        piechart.setHoleColor(R.color.colorAccent);
        NoOfEmp.add(new Entry(Float.parseFloat( ar.get(pos).getWrong()), 1));
        //   progress_bar.setProgress(QuestionsActivity.wrong);
        piechart.setHoleColor(R.color.colorPrimaryDark);

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        ArrayList year = new ArrayList();

        year.add("Correct");
        year.add("Wrong");

        PieData data = new PieData(year, dataSet);
        dataSet.setValueTextSize(13);
        data.setValueTextColor(Color.parseColor("#ffffff"));

        piechart.setData(data);
        // dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet .setColors(new int[] { Color.parseColor("#4CAF50"), Color.parseColor("#F44336")});
        return obj2;
    }
}