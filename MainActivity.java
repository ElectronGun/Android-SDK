package com.example.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double total = 0;
    private int count = 0;
    private TextView avgTempText;
    private ImageView statusImage;

    // Initial unit is Fahrenheit
    private boolean isCelsius = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avgTempText = findViewById(R.id.avgTemp);
        avgTempText.setText("N/A °F");

        statusImage = findViewById(R.id.statusImage);
        // Initial image status is no data
        statusImage.setImageResource(R.drawable.nodata);

        Switch tempSwitch = findViewById(R.id.tempSwitch);
        tempSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isCelsius = isChecked;
            updateDisplayedTemperature();
        });
    }

    public void reportTemperature(View view) {
        EditText tempEnter = findViewById(R.id.tempEnter);
        String text = tempEnter.getText().toString();
        double temperature = Double.parseDouble(text);

        if (isCelsius) {
            // Celsius to Fahrenheit Conversion
            temperature = (temperature * 9/5) + 32;
        }

        total += temperature;
        count++;

        updateDisplayedTemperature();

        tempEnter.setText("");
    }

    private void updateDisplayedTemperature() {
        double avgTemp = total / count;
        if (isCelsius) {
            // Convert average temperature to Celsius
            avgTemp = (avgTemp - 32) * 5/9;
            avgTempText.setText(String.valueOf(avgTemp) + " °C");
        } else {
            avgTempText.setText(String.valueOf(avgTemp) + " °F");
        }
        updateStatusImage(avgTemp);
    }

    private void updateStatusImage(double avgTemp) {
        if (count == 0) {
            statusImage.setImageResource(R.drawable.nodata);
        } else if (isCelsius) {
            // Celsius Ranges
            if (avgTemp >= 36 && avgTemp <= 38) {
                statusImage.setImageResource(R.drawable.normal);
            } else if (avgTemp > 38 && avgTemp <= 39.5) {
                statusImage.setImageResource(R.drawable.fever);
            } else {
                statusImage.setImageResource(R.drawable.highfever);
            }
        } else {
            // Fahrenheit Ranges
            if (avgTemp >= 97.2 && avgTemp <= 100.4) {
                statusImage.setImageResource(R.drawable.normal);
            } else if (avgTemp > 100.4 && avgTemp <= 103.1) {
                statusImage.setImageResource(R.drawable.fever);
            } else {
                statusImage.setImageResource(R.drawable.highfever);
            }
        }
    }
}
