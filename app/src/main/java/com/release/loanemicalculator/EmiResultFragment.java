package com.release.loanemicalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EmiResultFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        String resultValue = getArguments().getString("result", "");
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        String resultValue = getArguments().getString("result", "");
        final View rootView = inflater.inflate(R.layout.fragment_test, parent, false);
        TextView name = (TextView) rootView.findViewById(R.id.textView1);
        name.setText(resultValue);


        return rootView;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }

    public static EmiResultFragment newInstance(String result) {
        EmiResultFragment fragmentDemo = new EmiResultFragment();
        Bundle args = new Bundle();
        args.putString("result", result);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
