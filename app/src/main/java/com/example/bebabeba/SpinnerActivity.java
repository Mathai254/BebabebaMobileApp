package com.example.bebabeba;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Samuel Mathai on 1/20/2017.
 */

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{

    public String vehicle_selected;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            vehicle_selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
