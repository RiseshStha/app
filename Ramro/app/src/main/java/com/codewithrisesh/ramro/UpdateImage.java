package com.codewithrisesh.ramro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.ActivityUpdateImageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UpdateImage extends AppCompatActivity {
ActivityUpdateImageBinding binding;
    String img;
    Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateImageBinding.inflate(getLayoutInflater());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ActivityResultLauncher<Intent> someActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    //changing data into uri
                    Uri uri = data.getData();
                    binding.image.setImageURI(uri);
                    img = String.valueOf(uri);
                    filePath = uri;
                    // setting image into profile page

                }
            }
        });

        binding.chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                someActivityResult.launch(intent);
            }
        });

        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //now uploading the image or storing the image in firebase storage.
//                Toast.makeText(UpdateImage.this, img, Toast.LENGTH_SHORT).show();
                uploadImage();
            }
        });


        setContentView(binding.getRoot());
    }
    public void uploadImage(){
        if(img!= null){
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading...");
            dialog.show();
            try {
                StorageReference ref = storageReference.child("images/"+ FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Toast.makeText(UpdateImage.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(UpdateImage.this, "Retry!!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        //do something
                        //double progress = (100*)
                    }
                });
            }
            catch (Exception e){
               // do something.
            }


        }
    }
}