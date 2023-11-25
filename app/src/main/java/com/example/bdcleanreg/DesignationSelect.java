package com.example.bdcleanreg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class DesignationSelect extends AppCompatActivity {
    Button loginBtn, designation_select;
    ArrayList<String> designation;
    Dialog dialog;
    int designation_ref = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation_select);
        loginBtn = findViewById(R.id.nextForm);
        designation_select = findViewById(R.id.designation_select);


        // gender selection ===========================================================
        designation=new ArrayList<>();
        designation.add("Chief Coordinator");
        designation.add("Head of Coordinator");
        designation.add("Head of Logistic");

        designation_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(DesignationSelect.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(DesignationSelect.this, android.R.layout.simple_list_item_1,designation);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (Objects.requireNonNull(item)){
                            case "Chief Coordinator" :
                                designation_ref = 1;
                                designation_select.setText(item);
                                dialog.dismiss();
                                break;
                            case "Head of Coordinator" :
                                designation_ref = 2;
                                designation_select.setText(item);
                                dialog.dismiss();
                                break;
                            case "Head of Logistic" :
                                designation_ref = 3;
                                designation_select.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DesignationSelect.this, FormDetails.class);
                startActivity(i);
                finish();
            }
        });
    }
}