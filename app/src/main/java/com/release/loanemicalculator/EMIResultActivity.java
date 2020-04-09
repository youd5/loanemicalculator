package com.release.loanemicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

public class EMIResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_m_i_result);

        setToolbar("EMI Details");


        String result = getIntent().getStringExtra("emi");
        TextView tvresult = findViewById(R.id.emi_result);
        tvresult.setText(result);
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

}
