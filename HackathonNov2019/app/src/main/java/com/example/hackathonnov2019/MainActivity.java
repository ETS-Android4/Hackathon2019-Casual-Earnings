package com.example.hackathonnov2019;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView, shiftEarnings;
    EditText editWage, editTime;
    Button start, pause, calculateEarnings;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shiftEarnings = findViewById(R.id.shiftEarnings);
        textView = findViewById(R.id.textView);
        start = findViewById(R.id.button);
        pause = findViewById(R.id.button2);
        editWage = findViewById(R.id.editWage);
        editTime = findViewById(R.id.editTime);
        calculateEarnings = findViewById(R.id.buttonCalculate);

        handler = new Handler() ;

        calculateEarnings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Do something in response to button click
                double wage = 0;
                double hours = 0;
                double earnings = 0;

                String wageString = editWage.getText().toString();
                String hoursString = editTime.getText().toString();

                if (wageString.matches("")|| hoursString.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter appropriate values",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(wageString.matches("")|| hoursString.matches(""))){
                    wage = Double.parseDouble(wageString);
                    hours = Double.parseDouble(hoursString);
                    earnings = wage * hours;
                    shiftEarnings.setText(String.valueOf(earnings));

                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;
                    MilliSeconds = 0 ;
                    handler.postDelayed(runnable, 0);
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);
            }
        });
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}