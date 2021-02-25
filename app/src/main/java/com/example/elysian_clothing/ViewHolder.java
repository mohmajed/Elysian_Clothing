package com.example.elysian_clothing;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class ViewHolder extends RecyclerView.ViewHolder {
    FirebaseStorage storage;
    StorageReference storageRef;
    ImageView mimg;
    View mview;
    TextView mname,mprice;
    String imglink;
    Context ctx2;
    public ViewHolder(@NonNull View view) {
        super(view);
        mview=view;
        mname= (TextView) mview.findViewById(R.id.name);
        mprice= (TextView) mview.findViewById(R.id.price);
        mimg= (ImageView) mview.findViewById(R.id.img);
    }
    public void setDetails(Context ctx,String name, int price, String image){
        ctx2=ctx;
        mname.setText(name);
        mprice.setText(String.valueOf(price));
        //change image to imagedownload link here
        storage= FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        storageRef.child("Images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                imglink=uri.toString();
                Picasso.get().load(imglink).into(mimg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

}


