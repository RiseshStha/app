package com.codewithrisesh.ramro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
//    View decorView;
//    private GestureDetector tapDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        decorView = getWindow().getDecorView();
//        tapDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                boolean visible = ((decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) == 0);
////                if(visible){
////                    hideAll();
////                }
////                else{
//////                    showUi();
////                    hideAll();
////                }
//
//
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                //start from here59
//                if(distanceX < 0){
//                    hideAll();
//                }
////                else if(e2.){
////                    showUi();
////                }
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                return false;
//            }
//        });
        // use menu to replace it


        /*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(R.id.frameLayout, new home());
        transaction.commit();
         */

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new home());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        FragmentTransaction transactionHome = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        transactionHome.replace(R.id.frameLayout, new home());
                        transactionHome.commit();
                        break;
                    case R.id.wallet:
                        FragmentTransaction transactionWallet = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        transactionWallet.replace(R.id.frameLayout, new Wallet());
                        transactionWallet.commit();
                        break;
                    case R.id.profile:
                        FragmentTransaction transactionProfile = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        transactionProfile.replace(R.id.frameLayout, new Profile());
                        transactionProfile.commit();
                        break;
                    case R.id.youtube:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        transaction.replace(R.id.frameLayout, new Youtube());
                        transaction.commit();
                        Toast.makeText(MainActivity.this, "Youtube", Toast.LENGTH_SHORT).show();
                        break;
                }


                return false;

            }
        });


    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        tapDetector.onTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
//    }

//    private void hideAll(){
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
////                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);

//    }
//    private void showUi(){
//        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);
//    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
//        getMenuInflater().inflate(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
/*

        <TextView
            android:id="@+id/homeBtn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:layout_marginStart="30dp"
            android:gravity="center"
            android:text="Home"
            android:textSize="12sp" />

        <ImageView
            android:id="@+id/homeIcon"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="35dp"
            android:layout_marginTop="3dp"
            app:srcCompat="@drawable/ic_baseline_home_24" />

        <TextView
            android:id="@+id/youtubeBtn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|right"
            android:layout_marginRight="30dp"
            android:gravity="right"
            android:text="@string/youtube"
            android:textSize="12sp" />

        <ImageView
            android:id="@+id/youtube"
            android:layout_width="29dp"
            android:layout_height="25dp"
            android:layout_gravity="right"
            android:layout_marginTop="3dp"
            android:layout_marginRight="35dp"
            android:clickable="true"
            app:srcCompat="@drawable/icons8_youtube_96"
            tools:ignore="TouchTargetSizeCheck" />

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:layout_marginStart="130dp"
            android:text="Wallet"
            android:textSize="12sp" />

        <ImageView
            android:id="@+id/wallet"
            android:layout_width="29dp"
            android:layout_height="25dp"
            android:layout_marginStart="133dp"
            android:layout_marginTop="3dp"
            app:srcCompat="@drawable/ic_baseline_wallet_24" />

        <TextView
            android:id="@+id/textView2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:layout_marginStart="230dp"
            android:text="Profile"
            android:textSize="12sp" />

        <ImageView
            android:id="@+id/profile"
            android:layout_width="29dp"
            android:layout_height="25dp"
            android:layout_marginStart="233dp"
            android:layout_marginTop="3dp"
            app:srcCompat="@drawable/profile_user_svgrepo_com" />
 */