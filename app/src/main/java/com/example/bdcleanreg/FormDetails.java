package com.example.bdcleanreg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormDetails extends AppCompatActivity {


    EditText user_name,user_name_bn,password, user_email,confirm_password,presentAddress,facebook_link,nid_number,father_name,father_contact,father_occupation,mother_name,mother_contact,mother_occupation,number_of_brothers,number_of_sister,field_of_experience,years_of_experience,spouse_name,num_of_sons,highest_education,exam_degree,subject_major,university_board,education_institute,pass_year,registation_roll,result;
    Button select_shirt_size,marital_status,religion_spinner,select_blood,select_reference,select_gender,date_birth;
    CircleImageView member_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);
// id define ========================================================
        user_name = findViewById(R.id.user_name);
        user_name_bn = findViewById(R.id.user_name_bn);
        user_email = findViewById(R.id.user_email);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        date_birth = findViewById(R.id.date_birth);
        select_gender = findViewById(R.id.select_gender);
        presentAddress = findViewById(R.id.presentAddress);
        facebook_link = findViewById(R.id.nid_number);
        father_name = findViewById(R.id.father_name);
        father_contact = findViewById(R.id.father_contact);
        father_occupation = findViewById(R.id.father_occupation);
        mother_name = findViewById(R.id.mother_name);
        number_of_brothers = findViewById(R.id.number_of_brothers);
        number_of_sister = findViewById(R.id.number_of_sister);
        select_reference = findViewById(R.id.select_reference);
        select_blood = findViewById(R.id.select_blood);
        field_of_experience = findViewById(R.id.field_of_experience);
        years_of_experience = findViewById(R.id.years_of_experience);
        religion_spinner = findViewById(R.id.religion_spinner);
        spouse_name = findViewById(R.id.spouse_name);
        num_of_sons = findViewById(R.id.num_of_sons);
        highest_education = findViewById(R.id.highest_education);
        exam_degree = findViewById(R.id.exam_degree);
        subject_major = findViewById(R.id.subject_major);
        university_board = findViewById(R.id.university_board);
        education_institute = findViewById(R.id.education_institute);
        pass_year = findViewById(R.id.pass_year);
        registation_roll = findViewById(R.id.registation_roll);
        result = findViewById(R.id.result);
        select_shirt_size = findViewById(R.id.select_shirt_size);




    }


//    private void submitDialog(String notice, int imgGet, int noticeColor) {
//        Dialog dialog = new Dialog(FormDetails.this);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(true);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        TextView text = dialog.findViewById(R.id.submitTextDialog);
//        ImageView img = dialog.findViewById(R.id.submitImgDialog);
//        Button okBtn = dialog.findViewById(R.id.submitOkBtn);
//
//        text.setText(notice);
//        img.setImageResource(imgGet);
//        text.setTextColor(getResources().getColor(noticeColor));
//        okBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//            }
//        });
//        dialog.show();
//    }

}