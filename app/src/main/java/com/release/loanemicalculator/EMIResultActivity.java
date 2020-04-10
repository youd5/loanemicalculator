package com.release.loanemicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class EMIResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_m_i_result);
        String emi;
        Integer principal = 0, interestRate = 0, time = 0;
        setToolbar("EMI Details");


        String loanAmount = getIntent().getStringExtra("loanAmount");
        String rate = getIntent().getStringExtra("rate");
        String duration = getIntent().getStringExtra("duration");

        //TODO: Push this data to local storage, to use in a new tab in this activity.

        try{
            principal = (Integer.parseInt(loanAmount));
            interestRate = Integer.parseInt(rate);
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

}
