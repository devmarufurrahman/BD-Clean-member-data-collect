package com.example.bdcleanreg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private  String position_id,user_id,dept_level;
    Toolbar toolbar;
    Button select_division,select_district,select_upazilla, create_member, select_union, select_village;
    String pos_name, contact;
    public JSONArray division_result,district_result,upazila_result,union_result,village_result;
    TextView position_tv,toast_message, district_tv, upazila_tv, union_tv, village_tv;
    ProgressBar progressBar;
    Dialog dialog;
    List<String> gender, religion,occupation,division, district,upazila,union,village,parent, blood_group, shirt_size;
    int district_ref=0,division_ref=0,upazila_ref=0, union_ref = 0, village_ref = 0;
    int location_type_ref = 0;


    LinearLayout location_type_layout, ll1, ll2, ll3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        create_member = findViewById(R.id.create_member);

        Intent intent = getIntent();
        position_id = intent.getStringExtra("position_id");
        user_id=intent.getStringExtra("user_id");
        pos_name=intent.getStringExtra("pos_name");
        dept_level=intent.getStringExtra("dept_level");
        contact = intent.getStringExtra("contact");
        System.out.println("position_id = " + position_id);
        System.out.println("user_id = " + user_id);
        System.out.println("dept_level_pos = " + dept_level);

        toolbar=findViewById(R.id.custom_toolbars);
        select_division=findViewById(R.id.division_spinner);
        select_district=findViewById(R.id.district_spinner);
        select_upazilla=findViewById(R.id.upazilla_spinner);
//        select_union = findViewById(R.id.union_spinner);
//        select_village = findViewById(R.id.village_spinner);
        district_tv = findViewById(R.id.district_tv);
        upazila_tv = findViewById(R.id.upazila_tv);
//        union_tv = findViewById(R.id.union_tv);
//        village_tv = findViewById(R.id.village_tv);
        position_tv = findViewById(R.id.position_tv);
        progressBar = findViewById(R.id.progressbar);
        location_type_layout = findViewById(R.id.location_type_layout);
        location_type_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        position_tv.setText(pos_name);
        ll1 = findViewById(R.id.ll_1);
        ll2 = findViewById(R.id.ll_2);
        ll3 = findViewById(R.id.ll3);



        division = new ArrayList<>();
        district = new ArrayList<>();
        upazila = new ArrayList<>();
        union = new ArrayList<>();
        village = new ArrayList<>();
        parent = new ArrayList<>();
        gender = new ArrayList<>();
        religion = new ArrayList<>();
        occupation = new ArrayList<>();
        blood_group = new ArrayList<>();
        shirt_size = new ArrayList<>();



        // position wise location visible
        if (pos_name.equals("Chief Coordinator") || pos_name.equals("Head of IT & Media") || pos_name.equals("Head of Logistic") || pos_name.equals("Deputy Chief Coordinator")) {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            create_member.setVisibility(View.GONE);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent ii = new Intent(LocationActivity.this, FormDetails.class);

                    ii.putExtra("position_id",position_id);
                    ii.putExtra("pos_name",pos_name);
                    ii.putExtra("dept_level",dept_level);
                    ii.putExtra("contact", contact);

                    startActivity(ii);
                    finish();
                }
            },100);

            progressBar.setVisibility(View.GONE);

        } else if (pos_name.equals("Divisional Coordinator") || pos_name.equals("Additional IT & Media") || pos_name.equals("Additional Logistic")) {

            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
        } else if (pos_name.equals("Additional Coordinator") || pos_name.equals("District Coordinator") || pos_name.equals("Deputy Coordinator Logistic") || pos_name.equals("Deputy Coordinator IT & Media")) {

            ll3.setVisibility(View.GONE);
        }


        getDivisionData();
        select_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(LocationActivity.this);
                dialog.setContentView(R.layout.custom_spinner_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_list_item_1,division);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);

                        for (int i =0; i<division_result.length();i++){
                            try {
                                JSONObject jsonObject = division_result.getJSONObject(i);

                                if (jsonObject.getString("name").equals(item)){

                                    division_ref = Integer.parseInt(jsonObject.getString("id"));
                                    district.clear();
                                    select_division.setText(jsonObject.getString("name"));
                                    dialog.dismiss();

                                    if (pos_name.equals("Divisional Coordinator") || pos_name.equals("Additional IT & Media") || pos_name.equals("Additional Logistic")) {
                                        location_type_layout.setVisibility(View.GONE);
                                    } else {

                                        location_type_layout.setVisibility(View.VISIBLE);
                                    }


                                    Button cityBtn, districtBtn;
                                    cityBtn = findViewById(R.id.city_dialog_btn);
                                    districtBtn = findViewById(R.id.district_dialog_btn);

                                    cityBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            location_type_ref = 2;
                                            getDistrictData(division_ref, location_type_ref);
                                            district_tv.setText("City Corporation");
                                            upazila_tv.setText("Ward");
                                            union_tv.setText("Zone");
                                            village_tv.setText("Area name / Road");
                                            location_type_layout.setVisibility(View.GONE);
                                        }
                                    });

                                    districtBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            location_type_ref = 1;
                                            getDistrictData(division_ref, location_type_ref);
                                            district_tv.setText("District");
                                            upazila_tv.setText("Upazila");
//                                            union_tv.setText("Union / Pourosova");
//                                            village_tv.setText("Village / Area name");
                                            location_type_layout.setVisibility(View.GONE);

                                        }
                                    });

                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        // Dismiss dialog
                    }
                });
            }
        });

        select_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(LocationActivity.this);
                dialog.setContentView(R.layout.custom_spinner_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_list_item_1,district);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);

                        for (int i =0; i<district_result.length();i++){
                            try {
                                JSONObject jsonObject = district_result.getJSONObject(i);

                                if (jsonObject.getString("name").equals(item)){

                                    district_ref = Integer.parseInt(jsonObject.getString("id"));
                                    upazila.clear();
                                    select_district.setText(jsonObject.getString("name"));
                                    dialog.dismiss();
                                    getUpaziladata(district_ref, location_type_ref);

                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        // Dismiss dialog
                    }
                });
            }
        });

        select_upazilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(LocationActivity.this);
                dialog.setContentView(R.layout.custom_spinner_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_list_item_1,upazila);
                listView.setAdapter(adapter);



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);

                        for (int i =0; i<upazila_result.length();i++){
                            try {
                                JSONObject jsonObject = upazila_result.getJSONObject(i);

                                if (jsonObject.getString("name").equals(item)){
                                    upazila_ref = Integer.parseInt(jsonObject.getString("id"));
                                    union.clear();
                                    getUnionData(upazila_ref, location_type_ref);
                                    select_upazilla.setText(jsonObject.getString("name"));
                                    dialog.dismiss();

                                }

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        // Dismiss dialog
                    }
                });
            }
        });

//        select_union.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dialog = new Dialog(LocationActivity.this);
//                dialog.setContentView(R.layout.custom_spinner_layout);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//
//                ListView listView=dialog.findViewById(R.id.list_view);
//                ArrayAdapter<String> adapter=new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_list_item_1,union);
//                listView.setAdapter(adapter);
//
//
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // when item selected from list
//                        // set selected item on textView
//                        String item  = adapter.getItem(position);
//
//                        for (int i =0; i<union_result.length();i++){
//                            try {
//                                JSONObject jsonObject = union_result.getJSONObject(i);
//
//                                if (jsonObject.getString("name").equals(item)){
//                                    union_ref = Integer.parseInt(jsonObject.getString("id"));
//                                    village.clear();
//                                    select_union.setText(jsonObject.getString("name"));
//                                    dialog.dismiss();
//                                    getVillageData(union_ref);
//
//                                }
//
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                        // Dismiss dialog
//                    }
//                });
//
//            }
//        });

        create_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (division_ref == 0 && (pos_name.equals("Divisional Coordinator") || pos_name.equals("Additional IT & Media") || pos_name.equals("Additional Logistic"))){
                    select_division.setError("Select Division");
                } else if (district_ref == 0 && (pos_name.equals("Additional Coordinator") || pos_name.equals("District Coordinator") || pos_name.equals("Deputy Coordinator Logistic") || pos_name.equals("Deputy Coordinator IT & Media"))) {
                    select_district.setError("Select District");
                }
                else if (upazila_ref == 0 && (pos_name.equals("Upazila Coordinator"))) {
                    select_upazilla.setError("Select Upazila / Ward");
                }
                else {

                    Intent intent = new Intent(LocationActivity.this, FormDetails.class);

                    intent.putExtra("position_id",position_id);
                    intent.putExtra("pos_name",pos_name);
                    intent.putExtra("dept_level",dept_level);
                    intent.putExtra("contact", contact);

                    intent.putExtra("division_ref", division_ref);
                    intent.putExtra("district_ref", district_ref);
                    intent.putExtra("upazila_ref", upazila_ref);
                    intent.putExtra("union_ref", union_ref);
                    intent.putExtra("village_ref", village_ref);

                    startActivity(intent);

                }

            }
        });

//        select_village.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dialog = new Dialog(LocationActivity.this);
//                dialog.setContentView(R.layout.custom_spinner_layout);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();
//
//                ListView listView=dialog.findViewById(R.id.list_view);
//                ArrayAdapter<String> adapter=new ArrayAdapter<>(LocationActivity.this, android.R.layout.simple_list_item_1,village);
//                listView.setAdapter(adapter);
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // when item selected from list
//                        // set selected item on textView
//                        String item  = adapter.getItem(position);
//
//                        for (int i =0; i<village_result.length();i++){
//                            try {
//                                JSONObject jsonObject = village_result.getJSONObject(i);
//
//                                if (jsonObject.getString("name").equals(item)){
//                                    village_ref = Integer.parseInt(jsonObject.getString("id"));
//                                    select_village.setText(jsonObject.getString("name"));
//                                    dialog.dismiss();
//
//                                }
//
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//                        // Dismiss dialog
//                    }
//                });
//
//            }
//        });

    }

    private void getUpaziladata(int district_ref, int type_ref) {

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://bdclean.winkytech.com/backend/api/getUpazilaProfile.php?district_ref="+district_ref;
        System.out.println(type_ref+"upazilaData get");

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showUpazilaJSONS(response);
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(LocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                //Toast.makeText(NewMemberActivity.this, volleyError, Toast.LENGTH_LONG).show();
                Toast toast = new Toast(getApplicationContext());
                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout,findViewById(R.id.custom_toast));
                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
                toast_message.setText(volleyError +",  Failed To Get information");
                toast.setView(toast_view);
                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(stringRequest);


    }

    private void showUpazilaJSONS(String response) {
        String name="";

        System.out.println(response);

        try {

            JSONArray obj = new JSONArray(response);

            upazila_result = obj;

            for (int i=0;i<obj.length();i++){
                JSONObject jsonObject = obj.getJSONObject(i);
                name=jsonObject.getString("name");
                upazila.add(name);
            }


        }
        catch (JSONException e){
            Log.e("anyText",response);
            e.printStackTrace();
        }

    }

    private void getDistrictData(int division_ref, int type_ref) {

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://bdclean.winkytech.com/backend/api/getDistrictProfile.php?division_ref="+division_ref+"&type_ref="+type_ref;

        System.out.println(type_ref+"get district ");
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showDistrictJSONS(response);
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(LocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                //Toast.makeText(NewMemberActivity.this, volleyError, Toast.LENGTH_LONG).show();
                Toast toast = new Toast(getApplicationContext());
                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout,findViewById(R.id.custom_toast));
                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
                toast_message.setText(volleyError +",  Failed To Get information");
                toast.setView(toast_view);
                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(stringRequest);

    }

    private void showDistrictJSONS(String response) {

        String name="";

        System.out.println(response);

        try {

            JSONArray obj = new JSONArray(response);

            district_result = obj;

            for (int i=0;i<obj.length();i++){
                JSONObject jsonObject = obj.getJSONObject(i);
                name=jsonObject.getString("name");
                district.add(name);
            }


        }
        catch (JSONException e){
            Log.e("anyText",response);
            e.printStackTrace();
        }
    }

    private void getDivisionData() {

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://bdclean.winkytech.com/backend/api/getDivisionProfile.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showDivisionJSONS(response);
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(LocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                //Toast.makeText(NewMemberActivity.this, volleyError, Toast.LENGTH_LONG).show();
                Toast toast = new Toast(getApplicationContext());
                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout,findViewById(R.id.custom_toast));
                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
                toast_message.setText(volleyError +",  Failed To Get information");
                toast.setView(toast_view);
                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(stringRequest);


    }

    private void showDivisionJSONS(String response) {

        String name;

        System.out.println(response);

        try {

            JSONArray obj = new JSONArray(response);

            division_result = obj;

            for (int i=0;i<obj.length();i++){
                JSONObject jsonObject = obj.getJSONObject(i);
                name=jsonObject.getString("name");
                division.add(name);
            }

        }
        catch (JSONException e){
            Log.e("anyText",response);
            e.printStackTrace();
        }

    }

    private void getVillageData(int union_ref) {

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://bdclean.winkytech.com/backend/api/getVillageData.php?union_ref="+union_ref;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                String name="";
                System.out.println(response);
                try {
                    JSONArray obj = new JSONArray(response);
                    village_result = obj;
                    for (int i=0;i<obj.length();i++){
                        JSONObject jsonObject = obj.getJSONObject(i);
                        name=jsonObject.getString("name");
                        village.add(name);
                    }
                }
                catch (JSONException e){
                    Log.e("anyText",response);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(LocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                Toast.makeText(LocationActivity.this, volleyError, Toast.LENGTH_LONG).show();
//                Toast toast = new Toast(getApplicationContext());
//                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_layout_2,findViewById(R.id.custom_toast));
//                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
//                toast_message.setText(volleyError +",  Failed To Get User information");
//                toast.setView(toast_view);
//                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.show();
//                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getUnionData(int upazila_ref, int type_ref) {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://bdclean.winkytech.com/backend/api/getUnionData.php?upazila_ref="+upazila_ref+"&type_ref="+type_ref;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                String name="";
                System.out.println(response);
                try {
                    JSONArray obj = new JSONArray(response);
                    union_result = obj;
                    for (int i=0;i<obj.length();i++){
                        JSONObject jsonObject = obj.getJSONObject(i);
                        name=jsonObject.getString("name");
                        union.add(name);
                    }
                }
                catch (JSONException e){
                    Log.e("anyText",response);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(LocationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                Toast.makeText(LocationActivity.this, volleyError, Toast.LENGTH_LONG).show();
//                Toast toast = new Toast(getApplicationContext());
//                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_layout_2,findViewById(R.id.custom_toast));
//                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
//                toast_message.setText(volleyError +",  Failed To Get User information");
//                toast.setView(toast_view);
//                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.show();
//                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LocationActivity.this);
        requestQueue.add(stringRequest);
    }

}