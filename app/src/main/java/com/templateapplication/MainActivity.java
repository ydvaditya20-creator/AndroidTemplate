package com.example.mobileapp; // अपना पुराना पैकेज नाम ही रहने दें

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import com.templateapplication.R;

public class MainActivity extends Activity {

    private WebView myWebView;
    private LinearLayout errorLayout;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // व्यूज को ढूंढना
        myWebView = findViewById(R.id.myWebView);
        errorLayout = findViewById(R.id.errorLayout);
        btnRetry = findViewById(R.id.btnRetry);

        // वेबव्यू सेटिंग्स कॉन्फ़िगर करना
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true); // iframe लोड करने के लिए ज़रूरी
        myWebView.setWebViewClient(new WebViewClient());

        // ऐप खुलते ही पहली बार इंटरनेट चेक करना
        checkAndLoadApp();

        // रीट्राई बटन पर क्लिक करने का लॉजिक
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLoadApp();
            }
        });
    }

    // इंटरनेट चेक करके स्क्रीन दिखाने वाला फंक्शन
    private void checkAndLoadApp() {
        if (isInternetConnected()) {
            // नेट चालू है: एरर छुपाओ, वेबव्यू दिखाओ और HTML लोड करो
            errorLayout.setVisibility(View.GONE);
            myWebView.setVisibility(View.VISIBLE);
            myWebView.loadUrl("file:///android_asset/index.html");
        } else {
            // नेट बंद है: वेबव्यू छुपाओ, एरर स्क्रीन दिखाओ
            myWebView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    // इंटरनेट कनेक्शन जांचने का फंक्शन
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
