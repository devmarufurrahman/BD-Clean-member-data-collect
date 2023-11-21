package com.example.bdcleanreg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FormDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);


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