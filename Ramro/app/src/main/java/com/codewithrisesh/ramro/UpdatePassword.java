package com.codewithrisesh.ramro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentUpdatePasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdatePassword extends Fragment {


    public UpdatePassword() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    FragmentUpdatePasswordBinding binding;
    FirebaseFirestore database;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdatePasswordBinding.inflate(getLayoutInflater());

        auth = FirebaseAuth.getInstance();
        binding.updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = binding.oldPassword.getText().toString();
                String newPass = binding.newPassword.getText().toString();
                String confirmPass = binding.confirmPass.getText().toString();
                if(newPass.equals(confirmPass)){
                    final FirebaseUser user = auth.getCurrentUser();
                    AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
                    user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            user.updatePassword(newPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    getActivity().onBackPressed();
                                    getParentFragmentManager().beginTransaction().remove(UpdatePassword.this).commit();
                                    Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to update password!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to verify account!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Password and Confirm Password Didn't Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().remove(UpdatePassword.this).commit();
                getParentFragmentManager().popBackStack();
                // get back to previous page
            }
        });


//        database = FirebaseFirestore.getInstance();
//        database.collection("users")
//                .document(FirebaseAuth.getInstance().getUid())
//                .set("password",);
        return binding.getRoot();
    }
}