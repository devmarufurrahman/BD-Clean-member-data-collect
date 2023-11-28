package com.example.bdcleanreg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

public class Login extends AppCompatActivity {

    Button nextBtn;
    EditText login_contact;
    ProgressBar progressBar;

    OtpTextView otpTextView;
    public String position_id, dept_level, pos_name = "Head of Logistic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nextBtn = findViewById(R.id.nextBtn);
        login_contact = findViewById(R.id.login_contact);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        if (pos_name.equals("Chief Coordinator")){
            position_id = "6";
            dept_level = "2";
        } else if (pos_name.equals("Head of IT & Media")){
            position_id = "1";
            dept_level = "1";
        } else if (pos_name.equals("Head of Logistic")){
            position_id = "20";
            dept_level = "3";
        } else if (pos_name.equals("Deputy Chief Coordinator")){
            position_id = "6";
            dept_level = "1";
        } else if (pos_name.equals("Divisional Coordinator")){
            position_id = "8";
            dept_level = "4";
        } else if (pos_name.equals("Additional IT & Media")){
            position_id = "2";
            dept_level = "5";
        } else if (pos_name.equals("Additional Logistic")){
            position_id = "21";
            dept_level = "5";
        } else if (pos_name.equals("Additional Coordinator")){
            position_id = "9";
            dept_level = "5";
        } else if (pos_name.equals("District Coordinator")){
            position_id = "12";
            dept_level = "6";
        } else if (pos_name.equals("Deputy Coordinator IT & Media")){
            position_id = "3";
            dept_level = "7";
        } else if (pos_name.equals("Deputy Coordinator Logistic")){
            position_id = "22";
            dept_level = "7";
        } else if (pos_name.equals("Upazila Coordinator")){
            position_id = "14";
            dept_level = "8";
        } else if (pos_name.equals("Deputy Coordinator")){
            position_id = "13";
            dept_level = "7";
        }




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact = login_contact.getText().toString().trim();
                String final_contact = "88"+contact;

                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(Login.this, LocationActivity.class);
                intent.putExtra("pos_name",pos_name);
                intent.putExtra("position_id",position_id);
                intent.putExtra("dept_level",dept_level);
                intent.putExtra("contact", contact);
                startActivity(intent);
//
//                if (contact.equals("") || contact.length() != 11){
//
//                    Toast toast = new Toast(getApplicationContext());
//                    View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout,findViewById(R.id.custom_toast));
//                    TextView toast_message=toast_view.findViewById(R.id.custom_toast_tv);
//                    toast_message.setText("Please give a valid phone number");
//                    toast.setView(toast_view);
//                    toast.setGravity(Gravity.TOP| Gravity.FILL_HORIZONTAL,0,110);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
//                } else {
//
//                    sendSMS(contact);
//                    progressBar.setVisibility(View.GONE);
//                    nextBtn.setEnabled(false);
//                }
            }
        });
    }

    private void sendSMS(String contact) {

        String final_contact = "88"+contact;
        String otp = generateOTP();

       // String authorizationHeader = "Bearer 184|pt5PZv1ROwn03DADlrpaNbRER229eSQIVGyJ0IP6";
        String recipient = final_contact;
        String sender_id = "8809601003721";
        String type = "plain";
        String message = "Your BD Clean member registration verification OTP code is : "+otp;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.esms.com.bd/smsapi",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SMS API RESPONSE", response);
                        displayOTPDialog(otp, final_contact);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("SMS API RESPONSE", error.getMessage());
                        nextBtn.setEnabled(true);

                    }
                }) {

//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization: ", authorizationHeader);
//                return headers;
//            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("contacts", recipient);
                params.put("senderid", sender_id);
                params.put("type", type);
//                params.put("schedule_time", scheduleTime);
                params.put("msg", message);
                params.put("api_key","184|pt5PZv1ROwn03DADlrpaNbRER229eSQIVGyJ0IP6");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void displayOTPDialog(String OTP, String contact) {

        Dialog dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.otp_verification_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();

        otpTextView = dialog.findViewById(R.id.otp_view);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {

                if (OTP.equals(otp)) {

                    Toast.makeText(Login.this, "OTP matched", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, DesignationSelect.class);
                    intent.putExtra("contact", contact);
                    startActivity(intent);
                    dialog.dismiss();

                } else {
                    Toast.makeText(Login.this, "OTP not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static String generateOTP () {
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10)); // Generates random digits from 0 to 9
        }

        return otp.toString();
    }

//    private void startSmartUserConsent() {
//
//        SmsRetrieverClient client = SmsRetriever.getClient(this);
//        client.startSmsUserConsent(null);
//
//    }

}