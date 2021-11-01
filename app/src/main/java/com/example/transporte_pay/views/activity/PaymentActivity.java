package com.example.transporte_pay.views.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.api.PaymentClient;
import com.example.transporte_pay.data.model.Routes;
import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView message;
    EditText busGcash,fullName,reference;
    ImageButton uploadImageBtn;
    ImageView showImage;
    Button save,back;
    int scheduleID, quantity;
    String token,transId,busGcashNumber,fullname,bus_id;
    SessionManager sessionManager;
    AlertDialogManager alert;
    Uri imageUri;
    private Bitmap bitmap;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 21 && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            imageUri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                showImage.setImageBitmap(bitmap);
                showImage.setVisibility(View.VISIBLE);

            }catch (IOException e){
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(),TravelLogsActivity.class);
        startActivity(backIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        busGcash       = findViewById(R.id.gcashNumber);
        fullName       = findViewById(R.id.fullname);
        reference      = findViewById(R.id.reference);
        uploadImageBtn = findViewById(R.id.uploadBtn);
        showImage      = findViewById(R.id.proofImage);
        save           = findViewById(R.id.save);
        back           = findViewById(R.id.back);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            busGcashNumber = (String) extras.getString("busGcash");
            fullname = (String) extras.getString("fullname");
            transId = (String) extras.getString("transId");
            bus_id = (String) extras.getString("bus_id");
        }
        busGcash.setEnabled(false);
        fullName.setEnabled(false);

        busGcash.setText(busGcashNumber);
        fullName.setText(fullname);


        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,21);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(),TravelLogsActivity.class);
                startActivity(backIntent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }
    private void uploadImage(){

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
//        byte[] imageInByte   = byteArrayOutputStream.toByteArray();
//
//        String encodedImage =Base64.getEncoder().encodeToString(imageInByte);
//
//        File imageFile = bitmapToFile(getApplicationContext(),bitmap,"basim");
//
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(getContentResolver().getType(imageUri)),
//                        imageFile
//                );
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("picture", imageFile.getName(), requestFile);
//
        String referenceId = reference.getText().toString();
//        RequestBody references =
//                RequestBody.create(
//                        okhttp3.MultipartBody.FORM, referenceId);
//        RequestBody item_id =
//                RequestBody.create(
//                        okhttp3.MultipartBody.FORM, transId);
//
//        Toast.makeText(getApplicationContext(), imageFile.getName(), Toast.LENGTH_SHORT).show();
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setItemId(transId);
        paymentRequest.setReferenceId(referenceId);
//        paymentRequest.setSchedule_date(uDate);
//        Call<PaymentRequest> paymentCall = ApiClient.getPaymentClient().uploadImage(body, item_id ,references,"Bearer"+ token);
        Call<PaymentRequest> paymentCall = ApiClient.getPaymentClient().uploadImage(paymentRequest,"Bearer"+ token);
        paymentCall.enqueue(new Callback<PaymentRequest>() {
            @Override
            public void onResponse(Call<PaymentRequest> call, Response<PaymentRequest> response) {

                if(response.body().isStatus()){
                    Toast.makeText(PaymentActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentActivity.this,TravelLogsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(PaymentActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<PaymentRequest> call, Throwable t) {

                Toast.makeText(PaymentActivity.this,"Network Failure",Toast.LENGTH_SHORT).show();
            }
        });



    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }
}