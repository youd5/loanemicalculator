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

        startActivity(new Intent(MainActivity.this, EMIResultActivity.class)
                .putExtra("loanAmount", loanAmount)
                .putExtra("duration", years)
                .putExtra("rate", rate)
        );
    }
}


