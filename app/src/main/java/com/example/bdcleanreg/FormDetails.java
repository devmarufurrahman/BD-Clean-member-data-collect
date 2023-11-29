package com.example.bdcleanreg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormDetails extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{


    EditText user_name,user_name_bn,password, spouse_contact, user_email,confirm_password,presentAddress,facebook_link,nid_number,father_name,father_contact,father_occupation,mother_name,mother_contact,mother_occupation,number_of_brothers,number_of_sister,field_of_experience,years_of_experience,spouse_name,num_of_sons,highest_education,exam_degree,subject_major,university_board,education_institute,pass_year,registation_roll,result;
    Button select_t_shirt,marital_status, select_religion, select_blood, select_reference, select_gender, date_birth, submitBtn, select_occupation;
    CircleImageView select_photo;
    int day, month, year, division_ref, district_ref, upazila_ref, union_ref, village_ref, parent_ref;
    int myday, myMonth, myYear;
    String birth_date = "", position_id, pos_name, dept_level, contact;
    ArrayList<String> shirt_size, blood_group, religion, occupation, gender, reference, marital;
    Dialog dialog;
    int counter, occupation_ref = 0, religion_ref= 0, gender_ref= 0, reference_ref =0, marital_ref = 0;
    String size="", group="", cover_pic, encodedImage = "";
    Bitmap bitmap;
    ProgressBar progressBar;
    TextView toast_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);

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
        select_occupation = findViewById(R.id.select_occupation);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        position_id = intent.getStringExtra("position_id");
        pos_name = intent.getStringExtra("pos_name");
        dept_level = intent.getStringExtra("dept_level");
        contact = intent.getStringExtra("contact");

        division_ref = intent.getIntExtra("division_ref",0);
        district_ref = intent.getIntExtra("district_ref",0);
        upazila_ref = intent.getIntExtra("upazila_ref",0);
        union_ref = intent.getIntExtra("union_ref",0);
        village_ref = intent.getIntExtra("village_ref",0);

        Log.d("Form Details input", "village_ref: " + village_ref+", union_ref: "+union_ref+", upazila_ref: "+ upazila_ref+", district_ref: "+district_ref+"division_ref: " + division_ref+", contact: "+contact+", dept_level: "+ dept_level+", position_id: "+position_id+",  pos_name: "+ pos_name);

        getParentData(dept_level, division_ref, district_ref, upazila_ref, village_ref);

        date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_birth.setError(null);
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormDetails.this, FormDetails.this,year, month,day);
                datePickerDialog.show();
            }
        });



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
                                select_gender.setError(null);
                                dialog.dismiss();
                                break;
                            case "Female" :
                                gender_ref = 2;
                                select_gender.setText(item);
                                select_gender.setError(null);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });


        // occupation
        occupation = new ArrayList<>();
        occupation.add("Student");
        occupation.add("Farmer");
        occupation.add("Businessman");
        occupation.add("Service Holder (Govt.)");
        occupation.add("Service Holder (Private Company)");
        occupation.add("Enterpreneur");
        occupation.add("Home Maker");
        occupation.add("Social Worker");
        occupation.add("Technical Worker");
        occupation.add("Other");
        select_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_occupation.setError(null);


                dialog = new Dialog(FormDetails.this);
                dialog.setContentView(R.layout.custom_spinner_layout_2);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                ListView listView=dialog.findViewById(R.id.list_view);
                ArrayAdapter<String> adapter=new ArrayAdapter<>(FormDetails.this, android.R.layout.simple_list_item_1,occupation);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        String item  = adapter.getItem(position);
                        switch (item){
                            case "Farmer" :
                                occupation_ref = 2;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Businessman" :
                                occupation_ref = 3;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Service Holder (Govt.)" :
                                occupation_ref = 4;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Service Holder (Private Company)" :
                                occupation_ref = 5;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Enterpreneur" :
                                occupation_ref = 6;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Home Maker" :
                                occupation_ref = 7;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Social Worker" :
                                occupation_ref = 8;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Technical Worker" :
                                occupation_ref = 9;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Other" :
                                occupation_ref = 10;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                            case "Student" :
                                occupation_ref = 1;
                                select_occupation.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });





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
                marital_status.setError(null);


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

        reference=new ArrayList<>();
        reference.add("BD Clean Member");
        reference.add("Facebook");
        reference.add("YouTube");
        reference.add("Others");

        select_reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_reference.setError(null);
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
                select_blood.setError(null);
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

        religion=new ArrayList<>();
        religion.add("Islam");
        religion.add("Hinduism");
        religion.add("Christianity");
        religion.add("Buddhism");
        religion.add("Other");

        select_religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_religion.setError(null);
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
                            case "Hinduism" :
                                religion_ref = 2;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Christianity" :
                                religion_ref = 3;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Buddhism" :
                                religion_ref = 4;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                            case "Other" :
                                religion_ref = 5;
                                select_religion.setText(item);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
            }
        });

        shirt_size = new ArrayList<>();
        shirt_size.add("S");
        shirt_size.add("M");
        shirt_size.add("L");
        shirt_size.add("XL");
        shirt_size.add("XXL");

        select_t_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_t_shirt.setError(null);
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

        if (userName.equals("")){
            user_name.setError("Give a valid name in English");
            submitToast();
        } else if (userNameBn.equals("")) {
            user_name_bn.setError("Give a valid name in Bangla");
            submitToast();
        } else if (email.equals("") || !isValid) {
            user_email.setError("Give a valid email");
            submitToast();
        } else if (passwordGet.equals("") || passwordGet.length()<4) {
            password.setError("password min 4 char");
            submitToast();
        } else if (confirmPassword.equals("")) {
            confirm_password.setError("Confirm password");
            submitToast();
        } else if (!passwordGet.equals(confirmPassword)) {
            password.setError("password not match");
            confirm_password.setError("password not match");
            submitToast();
        } else if (birth_date.equals("")) {
            date_birth.setError("Select your Date of Birth");
            submitToast();
        } else if (gender_ref == 0) {
            select_gender.setError("Select gender");
            submitToast();
        } else if (occupation_ref == 0) {
            select_occupation.setError("Input Occupation");
            submitToast();
        } else if (present_address.equals("")) {
            presentAddress.setError("Give present address");
            submitToast();
        } else if (facebookId.equals("")) {
            facebook_link.setError("Give facebook id");
            submitToast();
        } else if (nidNumber.equals("") || nidNumber.length()<8) {
            nid_number.setError("Input a valid ID Number");
            submitToast();
        } else if (fatherName.equals("")) {
            father_name.setError("Give Father's name");
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
            marital_status.setError("Select Marital Status");
            submitToast();
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

            //Toast.makeText(FormDetails.this, "Submited", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[46];
                    field[0] = "userName";
                    field[1] = "userNameBn";
                    field[2] = "email";
                    field[3] = "passwordGet";
                    field[4] = "present_address";
                    field[5] = "facebookId";
                    field[6] = "nidNumber";
                    field[7] = "fatherName";
                    field[8] = "fatherContact";
                    field[9] = "fatherOccupation";
                    field[10] = "motherName";
                    field[11] = "motherContact";
                    field[12] = "motherOccupation";
                    field[13] = "totalBrothers";
                    field[14] = "totalSisters";
                    field[15] = "fieldOfExpertise";
                    field[16] = "yearsOfExperience";
                    field[17] = "spouseName";
                    field[18] = "spouseContact";
                    field[19] = "numOfSons";
                    field[20] = "highestEducation";
                    field[21] = "examDegree";
                    field[22] = "subjectMajor";
                    field[23] = "universityBoard";
                    field[24] = "educationInstitute";
                    field[25] = "passingYear";
                    field[26] = "registrationNo";
                    field[27] = "resultGet";
                    field[28] = "referred_by";
                    field[29] = "shirt_size";
                    field[30] = "blood_group";
                    field[31] = "occupation";
                    field[32] = "date_birth";
                    field[33] = "gender";
                    field[34] = "religion";
                    field[35] = "marital_status";
                    field[36] = "division_ref";
                    field[37] = "district_ref";
                    field[38] = "upazila_ref";
                    field[39] = "union_ref";
                    field[40] = "village_ref";
                    field[41] = "parent_ref";
                    field[42] = "contact";
                    field[43] = "photo";

                    field[44] = "position_id";
                    field[45] = "dept_level";


                    //Creating array for data
                    String[] data = new String[46];
                    data[0] = userName;
                    data[1] = userNameBn;
                    data[2] = email;
                    data[3] = passwordGet;
                    data[4] = present_address;
                    data[5] = facebookId;
                    data[6] = nidNumber;
                    data[7] = fatherName;
                    data[8] = fatherContact;
                    data[9] = fatherOccupation;
                    data[10] = motherName;
                    data[11] = motherContact;
                    data[12] = motherOccupation;
                    data[13] = totalBrothers;
                    data[14] = totalSisters;
                    data[15] = fieldOfExpertise;
                    data[16] = yearsOfExperience;
                    data[17] = spouseName;
                    data[18] =spouseContact;
                    data[19] = numOfSons;
                    data[20] = highestEducation;
                    data[21] = examDegree;
                    data[22] = subjectMajor;
                    data[23] = universityBoard;
                    data[24] = educationInstitute;
                    data[25] = passingYear;
                    data[26] = registrationNo;
                    data[27] = resultGet;
                    data[28] = String.valueOf(reference_ref);
                    data[29] = size;
                    data[30] = group;
                    data[31] = String.valueOf(occupation_ref);

                    data[32] = birth_date;
                    data[33] = String.valueOf(gender_ref);
                    data[34] = String.valueOf(religion_ref);
                    data[35] = String.valueOf(marital_ref);
                    data[36] = String.valueOf(division_ref);
                    data[37] = String.valueOf(district_ref);
                    data[38] = String.valueOf(upazila_ref);
                    data[39] = String.valueOf(union_ref);
                    data[40] = String.valueOf(village_ref);
                    data[41] = String.valueOf(parent_ref);
                    data[42] = contact;
                    data[43] = encodedImage;
                    data[44] = position_id;
                    data[45] = dept_level;

                    PutData putData = new PutData("https://bdclean.winkytech.com/backend/api/create_new_member_new_app.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult().trim();
                            Log.d("NEW MEMBER API : ",result);
                            progressBar.setVisibility(View.GONE);
                            if (result.equals("success")) {

                                // Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                                Toast toast = new Toast(FormDetails.this);
                                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_success_layout,findViewById(R.id.custom_toast));
                                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
                                toast_message.setText("Profile Created Successfully");
                                toast.setView(toast_view);
                                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();

                                finish();

                            } else if(result.equals("Contact Already in use. Try with Another One")) {
                                progressBar.setVisibility(View.GONE);
                                Log.i("PutData", result);
                                Toast toast = new Toast(getApplicationContext());
                                View toast_view = getLayoutInflater().inflate(R.layout.custom_toast_error_layout, findViewById(R.id.custom_toast));
                                toast_message=toast_view.findViewById(R.id.custom_toast_tv);
                                toast_message.setText("Contact Already in use. Try with Another One");
                                toast.setView(toast_view);
                                toast.setGravity(Gravity.TOP|Gravity.FILL_HORIZONTAL,0,110);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.show();
                                progressBar.setVisibility(View.GONE);

                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            });
        }
    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = dayOfMonth;
        myMonth = month;
        birth_date = (myYear + "-" + (myMonth+1) + "-" + myday);
        date_birth.setText(birth_date);
    }
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

    private void getParentData(String dept_level, int division_ref, int district_ref, int upazila_ref, int village_ref) {

        progressBar.setVisibility(View.VISIBLE);
        String url = "https://bdclean.winkytech.com/backend/api/getParentData.php?dept_level="+dept_level+"&upazila_ref="+upazila_ref+"&division_ref="+division_ref+"&district_ref="+district_ref+"&village_ref="+village_ref;
        System.out.println("Dept level = "+dept_level+" , upazila_ref="+upazila_ref+" , division_ref = "+division_ref+"&district_ref="+district_ref);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray obj = new JSONArray(response);
                    for (int i=0;i<obj.length();i++){
                        JSONObject jsonObject = obj.getJSONObject(i);
                        parent_ref = Integer.parseInt(jsonObject.getString("user_id"));

                    }
                }
                catch (JSONException e){
                    Log.e("anyText",response);
                    e.printStackTrace();

                }
                System.out.println("RESPONSE = "+response);
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.i("VolleyError",error.getMessage());
                Toast.makeText(FormDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                String volleyError = "";

                if (error instanceof NetworkError){
                    volleyError="Network Error";
                } else if (error instanceof ServerError){

                    volleyError="Server Connection error";
                }

                Toast.makeText(FormDetails.this, volleyError, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(FormDetails.this);
        requestQueue.add(stringRequest);

    }

}