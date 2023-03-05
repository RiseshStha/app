package com.codewithrisesh.ramro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Wallet extends Fragment {
    FragmentWalletBinding binding;
    FirebaseFirestore database;

    public Wallet() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    User user;
    String request = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWalletBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        binding.walletCoins.setText(String.valueOf(user.getCoins()));

                    }
                });

        database.collection("withdraws")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        try {
                            WithdrawRequest wallet = documentSnapshot.toObject(WithdrawRequest.class);
                            request = wallet.getUserId();
                        } catch (Exception e){
                            //
                        }

                    }
                });

        binding.reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                if(!request.equals(uid)){if(user.getCoins() > 50000){

                    String eSewa = binding.eSewaID.getText().toString();
                    WithdrawRequest request = new WithdrawRequest(user.getName(), eSewa,uid);
                    database.collection("withdraws")
                            .document(FirebaseAuth.getInstance().getUid())
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Withdraw request send successfully!!!", Toast.LENGTH_SHORT).show();
                                    binding.eSewaID.setText("");
                                }
                            });

                }
                else{
                    Toast.makeText(getContext(), "You should have 50000 coins to send request", Toast.LENGTH_SHORT).show();
                }
//                    Toast.makeText(getContext(), request, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Already Requested", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return binding.getRoot();
    }
}