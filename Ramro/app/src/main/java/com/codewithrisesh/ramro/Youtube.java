package com.codewithrisesh.ramro;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.codewithrisesh.ramro.databinding.FragmentHomeBinding;
import com.codewithrisesh.ramro.databinding.FragmentYoutubeBinding;


public class Youtube extends Fragment {

    public Youtube() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(getContext(), "now", Toast.LENGTH_SHORT).show();



        /*
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.getBlockNetworkLoads();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
         */
    }
    FragmentYoutubeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        if(Build.VERSION.SDK_INT >= 21){ // for changing status bar color but it apply in all activity so use it wisely
//            Window window = getActivity().getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getActivity().getResources().getColor(R.color.white));
//
//        }
        binding = FragmentYoutubeBinding.inflate(inflater, container, false);
        WebSettings setting = binding.webView.getSettings();
        setting.setJavaScriptCanOpenWindowsAutomatically(false);
        setting.setBuiltInZoomControls(true);
        setting.getBlockNetworkLoads();
        setting.setJavaScriptEnabled(true);
        setting.setDisplayZoomControls(false);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl("https://m.youtube.com/");
        binding.webView.setWebViewClient(new WebViewClient(){
             @Override
             public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                 super.doUpdateVisitedHistory(view, url, isReload);
             }
         });
        binding.webView.setWebViewClient(new WebViewClient() {

        });
//        binding.webView.loadUrl("https://google.com");
//        binding.webView.

        return binding.getRoot();

    }
}