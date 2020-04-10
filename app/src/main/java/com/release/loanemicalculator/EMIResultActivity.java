package com.release.loanemicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.release.loanemicalculator.util.CommonHelper;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EMIResultActivity extends AppCompatActivity {


    String emi;
    Integer principal = 0;
    Double interestRate = 0.0;
    Integer time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_m_i_result);
        setToolbar("EMI Details");
        initView();

        initChart();


    }

    public void initChart(){
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        List<DataEntry> seriesData = new ArrayList<>();


        Integer  N = time*12;
        Double r = interestRate;
        Double E = 0.0;
        try {
            E = Double.valueOf(emi);
        }catch (Exception e){
            //Not valid inputs. so return.
            return;
        }

        //TODO: minor, instead of month count, add actual month, year calculated and show in the graph
        for(int n = 1; n < N; n++){
            Double pp = (double) Math.pow(1/(1+r/1200),(N-n+1)) *E ;
            Double ii = E-pp;
            seriesData.add(new CustomDataEntry("Month " + String.valueOf(n), E, CommonHelper.formatDouble(ii), CommonHelper.formatDouble(pp)));
        }

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Payment Chart Showing Interest and Principal component in each Month");

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("EMI");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Interest");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Principal");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

    }

    public void initView(){
        String loanAmount = getIntent().getStringExtra("loanAmount");
        String rate = getIntent().getStringExtra("rate");
        String duration = getIntent().getStringExtra("duration");

        //TODO: Push this data to local storage, to use in a new tab in this activity.

        try{
            principal = (Integer.parseInt(loanAmount));
            interestRate = Double.parseDouble(rate);
            time = Integer.parseInt(duration);
            emi = emiCalculate( Double.valueOf(principal), Double.valueOf(interestRate), Double.valueOf(time));
        } catch (Exception e){
            emi = "Invalid Inputs";
        }

        TextView tvPrincipal = findViewById(R.id.tv_loan_amount_value);
        TextView tvRate = findViewById(R.id.tv_loan_rate_value);
        TextView tvDuration = findViewById(R.id.tv_loan_duration_value);
        TextView tvEmi = findViewById(R.id.tv_loan_emi_value);
        tvPrincipal.setText(principal.toString());
        tvRate.setText(interestRate.toString());
        tvDuration.setText(time.toString());
        tvEmi.setText(emi);
    }

    public void setToolbar(@NonNull String title) {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        TextView tootlbarTitle = (TextView) findViewById(R.id.toolbar_title) ;
        tootlbarTitle.setText(title);
        myToolbar.setTitle(title);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(getDrawable(R.drawable.favicon32x32));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    // Function to calculate EMI
    static String emiCalculate(Double p, Double rate, Double t) {
        Double emi;
        rate = rate / (12 * 100); // one month interest
        t = t * 12; // one month period
        emi = (p * rate * (float)Math.pow(1 + rate, t))
                / (float)(Math.pow(1 + rate, t) - 1);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        return (df.format(emi));
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
