package com.release.loanemicalculator;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.emiCalculator.MESSAGE";

    Button btncalculate;
    EditText editrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar("Monthly EMI Calculator");

        btncalculate = findViewById(R.id.calculate);
        editrate = findViewById(R.id.editroi);
        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editrate.onEditorAction(EditorInfo.IME_ACTION_DONE);
                calculate(v);
            }
        });
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

    public void calculate(View view) {
        EditText loanAmountText = (EditText) findViewById(R.id.editloanAmount);
        EditText roi = (EditText) findViewById(R.id.editroi);
        EditText duration = (EditText) findViewById(R.id.editduration);
        String loanAmount = loanAmountText.getText().toString();
        String rate = roi.getText().toString();
        String years = duration.getText().toString();

        // Push this data to local storage


        Integer principal = (Integer.parseInt(loanAmount));
        Integer interestRate = Integer.parseInt(rate);
        Integer time = Integer.parseInt(years);

        String emi = emiCalculate( Double.valueOf(principal), Double.valueOf(interestRate), Double.valueOf(time));
        startActivity(new Intent(MainActivity.this, EMIResultActivity.class).putExtra("emi", emi));

        /*
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment1, EmiResultFragment.newInstance("Your monthly EMI = " + emi));
        // or ft.add(R.id.your_placeholder, new FooFragment());
        ft.addToBackStack(null);
        // Complete the changes added above
        ft.commit();

         */


        //intent.putExtra(EXTRA_MESSAGE, emi);
        //startActivity(intent);
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


