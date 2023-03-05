package com.codewithrisesh.ramro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentUpdateEmailBinding;
import com.codewithrisesh.ramro.databinding.FragmentUpdatePasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UpdateEmail extends Fragment {

    public UpdateEmail() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    FragmentUpdateEmailBinding binding;
    FirebaseFirestore database;
    FirebaseAuth auth; // make a object of auth
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateEmailBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        try {
            binding.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newEmail = binding.newEmail.getText().toString();
                    String password = binding.pass.getText().toString();
                    Toast.makeText(getContext(), user.getEmail(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();
                    AuthCredential authCredential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()), password);
                    user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            user.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    getActivity().onBackPressed();
                                    getParentFragmentManager().beginTransaction().remove(UpdateEmail.this).commit();
                                    Toast.makeText(getContext(), "Email Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to update email", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to verify details", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            binding.cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get back to previous page
//                    getParentFragmentManager().popBackStack();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, new Profile());
                    transaction.commit();

                }
            });
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }
}