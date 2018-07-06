package com.example.yl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FourthActivity extends Activity {
    private int Position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Button button = (Button) findViewById(R.id.button);
        initSpinner((Spinner) findViewById(R.id.timeOrder));
        buttonOnClick(button);

    }
    public void buttonOnClick(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", Position+"");
//                Toast.makeText(FourthActivity.this,Position,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initSpinner(final Spinner spinner){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Position = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
