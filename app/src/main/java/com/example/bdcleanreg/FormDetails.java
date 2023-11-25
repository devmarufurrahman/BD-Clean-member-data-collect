package com.example.bdcleanreg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormDetails extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{


    EditText user_name,user_name_bn,password, spouse_contact, user_email,confirm_password,presentAddress,facebook_link,nid_number,father_name,father_contact,father_occupation,mother_name,mother_contact,mother_occupation,number_of_brothers,number_of_sister,field_of_experience,years_of_experience,spouse_name,num_of_sons,highest_education,exam_degree,subject_major,university_board,education_institute,pass_year,registation_roll,result;
    Button select_t_shirt,marital_status, select_religion, select_blood, select_reference, select_gender, date_birth, submitBtn ,marital_spinner;
    CircleImageView select_photo;
    int day, month, year;
    int myday, myMonth, myYear;
    String birth_date = "";
    ArrayList<String> shirt_size, blood_group, religion, occupation, gender, reference, marital;
    Dialog dialog;
    int counter, occupation_ref = 0, religion_ref= 0, gender_ref= 0, reference_ref =0, marital_ref = 0;
    String size="", group="", contact, cover_pic, encodedImage = "";
    Bitmap bitmap;

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
        facebook_link = findViewById(R.id.facebook_link);
        father_name = findViewById(R.id.father_name);
        father_contact = findViewById(R.id.father_contact);
        father_occupation = findViewById(R.id.father_occupation);
        mother_name = findViewById(R.id.mother_name);
        number_of_brothers = findViewById(R.id.number_of_brothers);
        number_of_sister = findViewById(R.id.number_of_sister);
        select_reference = findViewById(R.id.select_reference);
        select_blood = findViewById(R.id.select_blood);
        marital_status = findViewById(R.id.marital_spinner);
        field_of_experience = findViewById(R.id.field_of_experience);
        years_of_experience = findViewById(R.id.years_of_experience);
        select_religion = findViewById(R.id.religion_spinner);
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
        select_t_shirt = findViewById(R.id.select_shirt_size);
        submitBtn = findViewById(R.id.submitBtn);
        nid_number = findViewById(R.id.nid_number);
        mother_contact = findViewById(R.id.mother_contact);
        mother_occupation = findViewById(R.id.mother_occupation);
        select_photo = findViewById(R.id.member_photo);
        spouse_contact = findViewById(R.id.spouse_contact);

        // ===================================================================

        // get input field ====================================================


        // date of birth set ===================================================
        date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormDetails.this, FormDetails.this,year, month,day);
                datePickerDialog.show();
            }
        });


        // gender selection ===========================================================
        gender=new ArrayList<>();
        gender.add("Male");
        gender.add("Female");

        select_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,gender);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (Objects.requireNonNull(item)){
                            case "Male" :
                                gender_ref = 1;
                                select_gender.setText(item);
                                dialog.dismiss();
                                break;
                            case "Female" :
                                gender_ref = 2;
                                select_gender.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });



        // marital selection ===========================================================
        marital=new ArrayList<>();
        marital.add("Married");
        marital.add("Unmarried");

        marital_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,marital);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (Objects.requireNonNull(item)){
                            case "Married" :
                                marital_ref = 1;
                                marital_status.setText(item);
                                dialog.dismiss();
                                spouse_name.setVisibility(View.VISIBLE);
                                num_of_sons.setVisibility(View.VISIBLE);
                                spouse_contact.setVisibility(View.VISIBLE);
                                break;
                            case "Unmarried" :
                                marital_ref = 2;
                                marital_status.setText(item);
                                dialog.dismiss();
                                spouse_name.setVisibility(View.GONE);
                                num_of_sons.setVisibility(View.GONE);
                                spouse_contact.setVisibility(View.GONE);
                                break;
                        }
                    }
                });
            }
        });

        // reference by selection =======================================================

        reference=new ArrayList<>();
        reference.add("BD Clean Member");
        reference.add("Facebook");
        reference.add("YouTube");
        reference.add("Others");

        select_reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,reference);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (Objects.requireNonNull(item)){
                            case "BD Clean Member" :
                                reference_ref = 1;
                                select_reference.setText(item);
                                dialog.dismiss();
                                break;
                            case "Facebook" :
                                reference_ref = 2;
                                select_reference.setText(item);
                                dialog.dismiss();
                                break;
                            case "YouTube" :
                                reference_ref = 3;
                                select_reference.setText(item);
                                dialog.dismiss();
                                break;
                            case "Others" :
                                reference_ref = 4;
                                select_reference.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });

            }
        });


        // blood group selection =====================================================

        blood_group = new ArrayList<>();
        blood_group.add("A+");
        blood_group.add("A-");
        blood_group.add("B+");
        blood_group.add("B-");
        blood_group.add("AB+");
        blood_group.add("AB-");
        blood_group.add("O+");
        blood_group.add("O-");

        select_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,blood_group);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        group = blood_group.get(position);
                        select_blood.setText(group);
                        dialog.dismiss();
                    }
                });
            }
        });

        // select religion ==========================================================

        religion=new ArrayList<>();
        religion.add("Islam");
        religion.add("Hindu");
        religion.add("Christian");
        religion.add("Buddha");
        select_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,religion);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (Objects.requireNonNull(item)){
                            case "Islam" :
                                religion_ref = 1;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Hindu" :
                                religion_ref = 2;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Christian" :
                                religion_ref = 3;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Buddha" :
                                religion_ref = 4;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });


        // select t shirt size=========================================================
        shirt_size = new ArrayList<>();
        shirt_size.add("S");
        shirt_size.add("M");
        shirt_size.add("L");
        shirt_size.add("XL");
        shirt_size.add("XXL");

        select_t_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,shirt_size);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        size  = adapter.getItem(position);
                        select_t_shirt.setText(size);
                        dialog.dismiss();
                    }
                });
            }
        });

        // select photo==============================================================
        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(FormDetails.this)
                            .withPermission(android.Manifest.permission.READ_MEDIA_IMAGES)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {

                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                                }
                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {

                                    submitDialog("Permission Denied!!",R.drawable.warning_image,R.color.red);

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }


                            }).check();
                } else {

                    Dexter.withActivity(FormDetails.this)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {

                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);

                                }
                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {

                                    submitDialog("Permission Denied!!",R.drawable.warning_image,R.color.red);
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                    token.continuePermissionRequest();
                                }
                            }).check();
                }
            }
        });



        // submit onclick function ===============================================================
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();

            }
        });


    }

    private void submitData() {
        String userName = user_name.getText().toString().trim();
        String userNameBn = user_name_bn.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String passwordGet = password.getText().toString().trim();
        String confirmPassword = confirm_password.getText().toString().trim();
        String present_address = presentAddress.getText().toString().trim();
        String facebookId = facebook_link.getText().toString().trim();
        String nidNumber = nid_number.getText().toString().trim();
        String fatherName = father_name.getText().toString().trim();
        String fatherContact = father_contact.getText().toString().trim();
        String fatherOccupation = father_occupation.getText().toString().trim();
        String motherName = mother_name.getText().toString().trim();
        String motherContact = mother_contact.getText().toString().trim();
        String motherOccupation = mother_occupation.getText().toString().trim();
        String totalBrothers = number_of_brothers.getText().toString().trim();
        String totalSisters = number_of_sister.getText().toString().trim();
        String fieldOfExpertise = field_of_experience.getText().toString().trim();
        String yearsOfExperience = years_of_experience.getText().toString().trim();
        String spouseName = spouse_name.getText().toString().trim();
        String spouseContact = spouse_contact.getText().toString().trim();
        String numOfSons = num_of_sons.getText().toString().trim();
        String highestEducation = highest_education.getText().toString().trim();
        String examDegree = exam_degree.getText().toString().trim();
        String subjectMajor = subject_major.getText().toString().trim();
        String universityBoard = university_board.getText().toString().trim();
        String educationInstitute = education_institute.getText().toString().trim();
        String passingYear = pass_year.getText().toString().trim();
        String registrationNo = registation_roll.getText().toString().trim();
        String resultGet = result.getText().toString().trim();

        boolean isValid = isValidEmail(email);

        String TAG = "Condition";
        if (userName.equals("")){
            user_name.setError("Input name EN");
            submitToast();
        } else if (userNameBn.equals("")) {
            user_name_bn.setError("Input name BN");
            submitToast();
        } else if (email.equals("") || !isValid) {
            user_email.setError("Input a valid email");
            submitToast();
        } else if (passwordGet.equals("")) {
            password.setError("Input password");
            submitToast();
        } else if (confirmPassword.equals("")) {
            confirm_password.setError("Input confirm password");
            submitToast();
        } else if (!passwordGet.equals(confirmPassword)) {
            password.setError("password not match");
            confirm_password.setError("password not match");
            submitToast();
        } else if (birth_date.equals("")) {
            date_birth.setError("select your birthday");
            submitToast();
        } else if (gender_ref == 0) {
            select_gender.setError("select gender");
            submitToast();
        } else if (present_address.equals("")) {
            presentAddress.setError("Input present address");
            submitToast();
        } else if (facebookId.equals("")) {
            facebook_link.setError("Input facebook id");
            submitToast();
        } else if (nidNumber.equals("")) {
            nid_number.setError("Input nid/birth");
            submitToast();
        } else if (fatherName.equals("")) {
            father_name.setError("Input fatherName");
            submitToast();
        } else if (fatherContact.equals("") || fatherContact.length() != 11) {
            father_contact.setError("Input a valid contact");
            submitToast();
        } else if (fatherOccupation.equals("")) {
            father_occupation.setError("Input father occupation");
            submitToast();
        } else if (motherName.equals("")) {
            mother_name.setError("Input mother name");
            submitToast();
        } else if (motherContact.equals("") || motherContact.length() != 11) {
            mother_contact.setError("Input a valid contact");
            submitToast();
        } else if (motherOccupation.equals("")) {
            mother_occupation.setError("Input mother occupation");
            submitToast();
        } else if (totalBrothers.equals("")) {
            number_of_brothers.setError("Input total brothers");
            submitToast();
        } else if (totalSisters.equals("")) {
            number_of_sister.setError("Input total sister");
            submitToast();
        } else if (reference_ref == 0) {
            select_reference.setError("select reference");
            submitToast();
        } else if (group.equals("")) {
            select_blood.setError("select blood group");
            submitToast();
        } else if (fieldOfExpertise.equals("")) {
            field_of_experience.setError("Input fieldOfExpertise");
            submitToast();
        } else if (yearsOfExperience.equals("")) {
            years_of_experience.setError("Input yearsOfExperience");
            submitToast();
        } else if (religion_ref == 0) {
            select_religion.setError("Select religion");
            submitToast();
        } else if (marital_ref == 0) {
            marital_spinner.setError("Select marital");
            submitToast();
        } else if (marital_ref == 1){
            // conditional rendering
            if (spouseName.equals("")){
                spouse_name.setError("Input spouse name");
                submitToast();
            } else if (numOfSons.equals("")) {
                num_of_sons.setError("Input Num of sons");
                submitToast();
            } else if (spouseContact.equals("")){
                spouse_contact.setError("Input Spouse Contact");
            }

        } else if (highestEducation.equals("")) {
            highest_education.setError("Input highestEducation");
            submitToast();
        } else if (examDegree.equals("")) {
            exam_degree.setError("Input examDegree");
            submitToast();
        } else if (subjectMajor.equals("")) {
            subject_major.setError("Input subjectMajor");
            submitToast();
        } else if (universityBoard.equals("")) {
            university_board.setError("Input universityBoard");
            submitToast();
        } else if (educationInstitute.equals("")) {
            education_institute.setError("Input educationInstitute");
            submitToast();
        } else if (passingYear.equals("")) {
            pass_year.setError("Input passingYear");
            submitToast();
        } else if (registrationNo.equals("")) {
            registation_roll.setError("Input registrationNo");
            submitToast();
        } else if (resultGet.equals("")) {
            result.setError("Input result");
            submitToast();
        } else if (size.equals("")) {
            select_t_shirt.setError("Input t shirt size");
            submitToast();
        } else if (encodedImage.equals("")){
            submitDialog("Please select a profile photo",R.drawable.warning_image,R.color.red);
            submitToast();
        } else {
            Toast.makeText(FormDetails.this, "Submited", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // date picker method
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month;
        birth_date = (myYear + "-" + (myMonth+1) + "-" + myday);
        date_birth.setText(birth_date);
    }




    //submit toast function
    private void submitToast(){
        Toast toast = new Toast(getApplicationContext());
        View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout, findViewById(R.id.custom_toast));
        TextView toast_message=toast_view.findViewById(R.id.custom_toast_tv);
        toast_message.setText("Please Input All Field");
        toast.setView(toast_view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,110);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


// dialog function

    private void submitDialog(String notice, int imgGet, int noticeColor) {
        Dialog dialog = new Dialog(FormDetails.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView text = dialog.findViewById(R.id.submitTextDialog);
        ImageView img = dialog.findViewById(R.id.submitImgDialog);
        Button okBtn = dialog.findViewById(R.id.submitOkBtn);

        text.setText(notice);
        img.setImageResource(imgGet);
        text.setTextColor(getResources().getColor(noticeColor));
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }


// select image method

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==1 && resultCode==RESULT_OK){

            assert data != null;
            Uri filepath=data.getData();
            try {

                InputStream inputStream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                select_photo.setImageBitmap(bitmap);
                encodeBitmapImage();

            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
        byte[] bytesOfImage=byteArrayOutputStream.toByteArray();
        int lengthbmp = bytesOfImage.length;
        lengthbmp=lengthbmp/1024;
        System.out.println("image length : " + lengthbmp);

        if (lengthbmp>2048){

            submitDialog("Image Too Large...select a smaller one",R.drawable.warning_image,R.color.red);

        } else if (lengthbmp==0){

            encodedImage="";

        }else{

            encodedImage= Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        }
    }



}