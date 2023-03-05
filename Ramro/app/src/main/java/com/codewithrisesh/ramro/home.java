package com.codewithrisesh.ramro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentHomeBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class home extends Fragment {
    FragmentHomeBinding binding;
    RewardedAd mRewarded;
    private int coin;
    private int token;
    public static final String REWARDED_APP_ID_NO = "ca-app-pub-3940256099942544/5224354917";
    FirebaseFirestore database;
    final int reward = 10;
    public home() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        User model = new User("Rise",1,1);
        database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class); // converting user object to class object
                        binding.coins.setText(String.valueOf(user.getCoins()));
                        binding.tokens.setText(String.valueOf(user.getTokens()));
                        binding.user.setText(String.valueOf(user.getName()));
                    }
                });
        //first store date and noWatchAds in database, and get current date in devices and check in database if matched get noWatchAds if not update
        // the date and reset noWatchAds to zero.
//        database.collection("watchAds")
//                .document(FirebaseAuth.getInstance().getUid())
//                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        WatchAds watchAds = documentSnapshot.toObject(WatchAds.class);
//                    }
//                });

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        binding.coins.setText(String.valueOf(model.getCoins()));
        MobileAds.initialize(requireContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadRewardAds();
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Loading Ads", Toast.LENGTH_SHORT).show();
                showAds();
            }
        });
        // CAREFULLY HANDEL THIS CODE
        // IN FUTURE THIS CODE CAN BE OPTIMIZED
        // AND REWRITTEN
//        View v = inflater.inflate(R.layout.fragment_home, container, true);
//        v.setFocusableInTouchMode(true);
//        v.requestFocus();
//        v.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(event.getAction() == KeyEvent.ACTION_DOWN){
//                    if(keyCode == KeyEvent.KEYCODE_BACK){
//                        Toast.makeText(getContext(), "exit", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                return false;
//            }
//        });
        int backStackCount = getParentFragmentManager().getBackStackEntryCount();
        if(backStackCount == 0){
            OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("");
                    builder.setMessage("Are you sure want to exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), backPressedCallback);
        }
        else{
            //DO something
        }

        return binding.getRoot();
    }

    private void loadRewardAds(){
        AdRequest request = new AdRequest.Builder().build();
        RewardedAd.load(getContext(), REWARDED_APP_ID_NO, request, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mRewarded = null;
                Toast.makeText(getContext(), "Ads not available\n Comeback later", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mRewarded = rewardedAd;
                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });

            }
        });
    }
    private void showAds(){
        if(mRewarded != null){
            // TRACKING REWARDS
            // Making objects of model
            User model = new User();
//            model.setCoins(coin+model.getAddCoin());
//            binding.coins.setText(String.valueOf(model.getCoins()));
            mRewarded.show(requireActivity(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    int amount = rewardItem.getAmount();
                    String type = rewardItem.getType();
                    database = FirebaseFirestore.getInstance();
                    database.collection("users")
                                    .document(FirebaseAuth.getInstance().getUid())
                                            .update("coins", FieldValue.increment(reward)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), String.valueOf(amount)+" you have earned", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
        }
        else
            Toast.makeText(getContext(), "Video not available", Toast.LENGTH_SHORT).show();
    }

}