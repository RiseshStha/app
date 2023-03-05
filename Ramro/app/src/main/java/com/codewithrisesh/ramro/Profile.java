package com.codewithrisesh.ramro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;


public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        StorageReference ref = reference.child("images/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        try{
            File file = File.createTempFile("tempFile",".jpg");
            ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    binding.profileImage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Profile","Failed to load");
                }
            });
        }catch (Exception e){
            //
        }

    }
    FragmentProfileBinding binding;
    FirebaseFirestore database;
    StorageReference storageReference;
    User user;
    Uri imageUri;
    public static final int IMAGE_REQUEST = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference reference = storage.getReference();
//        StorageReference ref = reference.child("images/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
//        try{
//            File file = File.createTempFile("tempFile",".jpg");
//            ref.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                    binding.profileImage.setImageBitmap(bitmap);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("Profile","Failed to load");
//                }
//            });
//        }catch (Exception e){
//            //
//        }
//        Uri img = ref.getDownloadUrl().getResult();
        database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        binding.profileName.setText(String.valueOf(user.getName()));
                    }
                });


        ActivityResultLauncher<Intent> someActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    //changing data into uri
                    Uri uri = data.getData();
                    // setting image into profile page
                    binding.profileImage.setImageURI(uri);

                }
            }
        });

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class); // converting  user class to object
                        binding.profileEmail.setText(user.getEmail());
                        binding.profileCoins.setText(String.valueOf(user.getCoins()));
                        binding.profileTokens.setText(String.valueOf(user.getTokens()));

                    }
                });
//        binding.profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // opening gallery to select profile picture.
//                Toast.makeText(getActivity(), "Opening", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent();
////                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                //start from here
////                startActivityForResult(intent, IMAGE_REQUEST);
////                ActivityResultLauncher<Intent> someActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
////                    @Override
////                    public void onActivityResult(ActivityResult result) {
////                        if(result.getResultCode() == Activity.RESULT_OK){
////                            Intent data = result.getData();
////                            //changing data into uri
////                            Uri uri = data.getData();
////                            // setting image into profile page
////                            binding.profileImage.setImageURI(uri);
////
////                        }
////                    }
////                });
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                someActivityResult.launch(intent);
//                Toast.makeText(getContext(), String.valueOf(intent.getData()), Toast.LENGTH_SHORT).show();// MAKE ANOTHER ACTIVITY TO SELECT IMAGE AND SAVE IMAGE OPTION
////                uploadImage();
//
//            }
//        });

        binding.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateImage.class);
                startActivity(intent);

            }
        });

        // handling the result
//        onActivityResult();
//        database.collection()

        binding.updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactionHome = getParentFragmentManager().beginTransaction();
                transactionHome.replace(R.id.frameLayout, new UpdateEmail());
                transactionHome.commit();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setView(R.layout.custom_email_box);
//                builder.show();


            }
        });

        binding.updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transactionHome = getParentFragmentManager().beginTransaction();
                transactionHome.replace(R.id.frameLayout, new UpdatePassword());
                transactionHome.commit();

            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
               builder.setMessage("Are you sure want to logout?");
               builder.setTitle("");
               builder.setCancelable(false);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       FirebaseAuth.getInstance().signOut();
                       Intent intent = new Intent(getContext(), LoginPage.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                       getParentFragmentManager().beginTransaction().remove(Profile.this);
                       Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                       getActivity().finish();
                       startActivity(intent);

                   }
               }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               AlertDialog alert = builder.create();
               alert.show();


            }
        });


        return binding.getRoot();
    }
    public void openSomeActivityForResult(){ // it is a method to get a result of intent
        ActivityResultLauncher<Intent> someActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    //changing data into uri
                    Uri uri = data.getData();
                    imageUri = data.getData();
                    Toast.makeText(getContext(), String.valueOf(uri), Toast.LENGTH_SHORT).show();

                    // setting image into profile page
//                   binding.profileImage.setImageURI(uri);

                }
            }
        });
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        someActivityResult.launch(intent);
    }
    public void uploadImage(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.ENGLISH);
        Date now  = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(imageUri).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //uploading the image
                        binding.profileImage.setImageURI(null);
                        Toast.makeText(getContext(), "Image Updated", Toast.LENGTH_SHORT).show();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Retry!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}