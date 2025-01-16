package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    View lastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize UI components
        cityList = findViewById(R.id.city_list);
        Button addCity = findViewById(R.id.addCity);
        Button deleteCity = findViewById(R.id.deleteCity);
        Button confirm = findViewById(R.id.confirm);
        EditText input = findViewById(R.id.input);

        final int[] item = {-2}; // Holds the index of the selected item (-2 indicates no selection)

        // Initialize city data and adapter
        String[] cities = {"Edmonton", "Paris", "London"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Initially hide the confirm button and input field
        confirm.setVisibility(View.GONE);
        input.setVisibility(View.GONE);

        // Handle item selection in the ListView
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item[0] = position; // Set the selected item's index
                if (lastView != null) {
                    lastView.setBackgroundColor(Color.TRANSPARENT); // Reset background color of the last selected view
                }
                lastView = view; // Update the last selected view
                view.setBackgroundColor(Color.GRAY); // Highlight the selected item

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the city name from the input field and add it to the list
                String newCity = ((EditText) findViewById(R.id.input)).getText().toString();
                ((EditText) findViewById(R.id.input)).setText("");
                dataList.add(newCity);
                cityList.setAdapter(cityAdapter);
                confirm.setVisibility(View.GONE);
                input.setVisibility(View.GONE);
            }

    });
        // Handle the add city button click
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the confirm button and input field
                confirm.setVisibility(View.VISIBLE);
                input.setVisibility(View.VISIBLE);
                cityAdapter.notifyDataSetChanged();
                item[0] = -2; // Reset the selected item

            }
        });
        // Handle the delete city button click
        deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the selected city if a valid selection is made
                if (item[0] >= 0 && item[0] < dataList.size()) {
                    dataList.remove(item[0]);
                    cityList.setAdapter(cityAdapter);
                    item[0] = -2;
                }else{
                    Toast.makeText(v.getContext(), "Select a city to delete!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}