package com.example.elysian_clothing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;

public class Add extends AppCompatActivity {
    Datas datas;
    public Uri imguri;
    StorageReference mStorageRef;
    ImageView img;
    Button publish,ch;
    EditText ed,name,price;
    DatabaseReference dbref;
    ArrayList<String> childrenNames;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ch = (Button) findViewById(R.id.imgbtn);
         publish = (Button) findViewById(R.id.publish);
         img =findViewById(R.id.img);
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        dbref= FirebaseDatabase.getInstance().getReference().child("Datas");
        name = (EditText) findViewById(R.id.name);
        price=(EditText) findViewById(R.id.itemprice);
        datas= new Datas();
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText ed = (EditText) findViewById(R.id.name);
                EditText ed2 = (EditText) findViewById(R.id.itemprice);
                String ed_text = ed.getText().toString().trim();
                String ed2_text = ed.getText().toString().trim();

                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null
                || ed2_text.isEmpty() || ed2_text.length() == 0 || ed2_text.equals("") || ed2_text == null
                ) {
                    Toast.makeText(Add.this,"Please Enter the Item Name First",Toast.LENGTH_LONG).show();
                }else {
                    Filechooser();
                }
            }
        });

    }

    public String getExtention(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    public void FileUploader(View v){
        String imgId;
        //giving the new file same name as item name in edit text . extention of upload
        pd = new ProgressDialog(Add.this);
        pd.setMessage("Uploading Image");
        pd.show();
        EditText ed = (EditText) findViewById(R.id.name);
        String n= ed.getText().toString().replaceAll("\\s+", "");
        imgId =n+"."+getExtention(imguri);
        datas.setName(name.getText().toString().trim());
        datas.setImgid(imgId);
        int p= Integer.parseInt(price.getText().toString().trim());
        datas.setPrice(p);
        dbref.push().setValue(datas);
        StorageReference Ref = mStorageRef.child(imgId);

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(Add.this,"Uploaded Successfully",Toast.LENGTH_LONG).show();
                        pd.cancel();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.cancel();
                        Toast.makeText(Add.this,"IMG Failed to Upload",Toast.LENGTH_LONG).show();

                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData()!=null && data!=null){
            imguri=data.getData();
            img.setImageURI(imguri);
        }
    }

    public void Filechooser(){

        Intent I = new Intent();
        I.setType("image/*");
        I.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(I,1);


    }
}
